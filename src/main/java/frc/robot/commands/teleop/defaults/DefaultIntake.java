package frc.robot.commands.teleop.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import static frc.robot.RobotContainer.*;

public class DefaultIntake extends Command {

  public DefaultIntake() {
    addRequirements(RobotContainer.INTAKE);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch (RobotContainer.STATE) {
      case RECOVERY:
        break;
      case COLLECTING:
        INTAKE.setVolts(Constants.Intake.INTAKE_COLLECT_VOLTS);
        break;
      case COLLECTING_SLOW:
        INTAKE.setVolts(Constants.Intake.INTAKE_COLLECT_SLOW_VOLTS);
        break;
      case CLIMB_INIT:
      case CLIMB_PULLDOWN:
      case CLIMB_LEVEL:
      case TRAP_ELEV_MIDWAY:
      case TRAP_WRIST_MIDWAY:
      case TRAP_ELEV_FULL:
      case TRAP_WRIST_FULL:
      case TRAP_SCORE:
      case TRAP_DOTHEJIGGLE:
        INTAKE.stopBoth();
        break;
      default:
        switch (getScoreMode()) {
          case DEFENSE:
            INTAKE.stopBoth();
            break;
          case AMP:
          case SPEAKER:
          default:
            switch (RobotContainer.getLocalizationState().getFieldZone()) {
              case ALLIANCE_WING:
              case OPPONENT_WING:
              case NEUTRAL_WING:
              default:
                INTAKE.stopBoth();
                break;
            }
            break;
        }
        break;
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
