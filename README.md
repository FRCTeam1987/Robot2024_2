# Robot 2024 (2)

The official repository for Team 1987's second-generation robot code. 

The first iteration of our robot code was designed as a normal command-based robot project. Unfortunately, our robot had certain requirements that made the WPILib command-based system less practical for us. Our programming team decided that a "state machine" methodology of programming would be a better approach for our 2024 season robot. 

## Why?
The command-based template provided by WPILib, while robust, failed in a few main areas for us. We wanted our robot to perform certain actions autonomously, based on its field location. These autonomous "commands" oftentimes conflicted with manual commands or inputs provided by our drivers, sometimes causing niche or unpredicted cases that led to unwanted behavior of our subsystems.

## How?
We created 3 global variables, of custom enum types. One for the RobotState, one for the DriveMode, and one for the ScoreMode.  We also made all of our subsystems static, for ease of access. Each subsystem runs off of a default command, which directs the subsystem based on the current robot state.

### RobotState & Subsystems
The RobotState enum contained all of the possible robot states, such as "COLLECTING, PODIUM, SHOOTING, PASS", etc. 
Most of our commands were "stated" commands; sequential command groups that would set a robot state, and then wait for certain conditions to be met, before returning to default. Here is an example:
```java
  public IntakeNoteState() {

    addRequirements(SEMAPHORE);

    addCommands(
        // Tell subsystems to start collecting
        new InstCmd(() -> setRobotState(RobotState.COLLECTING)),
        // Wait for note to break rear line break sensor
        new WaitUntilCommand(SHOOTER::isRearBroken),
        // Continue collecting, but slower
        new InstCmd(() -> setRobotState(RobotState.COLLECTING_SLOW)),
        // Wait for note to break center line break sensor
        new WaitUntilCommand(SHOOTER::isCenterBroken),
        // Check where the robot is on the field; if in an opponent wing, begin automatically aiming. Else, idle.
        new InstCmd(() -> {
          if (getLocalizationState().fieldZone() != FieldZones.Zone.OPPONENT_WING) {
            setDriveMode(DriveMode.AUTOMATIC);
          }
          setRobotState(RobotState.DEFAULT);
        }));
  }
  ```

Each of our subsystems has been assigned a default command. This default command contains a switch statement that checks the RobotState variable every code cycle and sets motor speeds/subsystem behavior accordingly. For example, if the robot state has been set to "COLLECTING", the Intake subsystem's default command would recognize that and begin running the intake motors. 
Here is a snippet example from DefaultShooter.java.
```java
@Override
    public void execute() {
        switch (RobotContainer.STATE) {
            case COLLECTING:
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
                break;
            case COLLECTING_SLOW:
                if (!SHOOTER.isCenterBroken())
                    SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS_SLOW);
                break;
            default:
                #yada yada yada
        }
    }
```
### ScoreMode 
The ScoreMode enum contains all the possible scoring configurations, such as "SPEAKER, AMP", and a "DEFENSE" mode for pure defending. This variable is set on the driver's controller, allowing our driver to select between an automatic speaker shot, an automatic amp shot, or a defense mode which disables all subsystems and gives extra power to the drivetrain. 

Most commonly, the ScoreMode variable is checked in the default case in each subsystem's default command. For example, in the DefaultShooter command, if the ScoreMode is SPEAKER, we spin the shooter flywheels up to a shooting speed. However, if it's AMP, we spin them down to prepare for an amp score.  The ScoreMode state is also vital to the automatic DriveMode system.

### DriveMode
The DriveMode enum allows us to switch between an "AUTOMATIC" drive mode and a "MANUAL" drive mode. In manual mode, all aspects of the Swerve drivetrain are manually controlled by our driver. Our driver has a button on his controller that allows him to jump to automatic mode, which commands the Swerve subsystem to automatically point the robot at the desired target. (Could be the speaker or amp, depending on the ScoreMode variable.) Automatic mode also locks the wrist to the desired target angle. 

Automatic mode is also automatically triggered when the robot drives from one zone to another zone, and certain conditions are met. Here is an example from DefaultSwerve.java.
```java
    final LocalizationState LOCAL_STATE = RobotContainer.getLocalizationState();

    switch (RobotContainer.getLocalizationState().fieldZone()) {
      case ALLIANCE_WING:
      case ALLIANCE_STAGE:
      case ALLIANCE_HOME:
      // If we're in any part of our alliance side, check if the shooter has a note
        if (SHOOTER.hasNote()) {
            // If the previous zone isn't our wing, our stage, or our home line, go to automatic mode. 
            // (The only time this is possible is if we're going from the neutral wing to any part in our zone.)
          if (prevZone != Zone.ALLIANCE_WING && prevZone != Zone.ALLIANCE_STAGE && prevZone != Zone.ALLIANCE_HOME)
            setDriveMode(DriveMode.AUTOMATIC);
        }
        switch (RobotContainer.getScoreMode()) {
            // Set the degrees we should rotate to, based off of what our ScoreMode is set to.
          case SPEAKER:
            AUTO_ROT = getRPS(LOCAL_STATE.speakerAngle());
            break;
          case AMP:
            AUTO_ROT = getRPS(new Rotation2d(Math.toRadians(90.0)));
            break;
          default:
            break;
        }
        break;
```

### Problems with this method 
State machines, while cool, come with a few glaring issues. Unfortunately, they love to eat your performance, so coding with that in mind is necessary. You also have to be considerate of conflicting states, whilst also being sure to account for every logically possible state. State machines also tend to be very touchy. If you change one thing, a lot of other things can break. 

### Advantages of this method
The coding workflow of a state machine is much simpler to understand in my opinion, and the troubleshooting time for when something goes wrong is much shorter in our experience. Everything is logically defined in a flowchart-type fashion, as you read through each default command. A more centralized way to manage the state would be nice, but that'll be next year's endeavor. 