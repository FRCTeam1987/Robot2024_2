package frc.robot.commands.teleop.defaults;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.util.zoning.FieldZones;

public class DefaultElevator extends Command {
  public DefaultElevator() {
    addRequirements(RobotContainer.ELEVATOR);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch (RobotContainer.STATE) {
      case RECOVERY:
        break;
      case AMP_PREP:
      case AMP_SCORE:
        ELEVATOR.setLengthInches(Constants.Elevator.AMP_ELEVATOR_HEIGHT, 0);
        break;
      case AMP_EXIT:
        ELEVATOR.setLengthInches(Constants.Elevator.AMP_ELEVATOR_EXIT_HEIGHT);
        break;
      case PASS:
        ELEVATOR.setLengthInches(Constants.Elevator.PASS_ELEVATOR_HEIGHT);
        break;
      case FAST_SUB_PREP:
      case FAST_SUB:
      case SUBWOOFER_PREP:
      case SUBWOOFER:
        ELEVATOR.setLengthInches(Constants.Elevator.SUBWOOFER_SHOT_HEIGHT, 0);
        break;
      case PODIUM_PREP:
      case PODIUM:
        ELEVATOR.setLengthInches(Constants.Elevator.PODIUM_SHOT_HEIGHT);
        break;
      case CLIMB_INIT:
        ELEVATOR.setLengthInches(Constants.Elevator.CLIMB_START_HEIGHT, 0);
        break;
      case CLIMB_PULLDOWN:
        ELEVATOR.setLengthInches(Constants.Elevator.CLIMB_PULLDOWN_HEIGHT, 1);
        break;
      case CLIMB_LEVEL:
        ELEVATOR.setLengthInches(Constants.Elevator.CLIMB_LEVEL_HEIGHT, 0);
        break;
      case TRAP_ELEV_MIDWAY:
      case TRAP_WRIST_MIDWAY:
        ELEVATOR.setLengthInches(Constants.Elevator.TRAP_ELEVATOR_HEIGHT_MIDWAY, 0);
        break;
      case TRAP_ELEV_FULL:
      case TRAP_WRIST_FULL:
      case TRAP_SCORE:
      case TRAP_DOTHEJIGGLE:
        ELEVATOR.setLengthInches(Constants.Elevator.TRAP_ELEVATOR_HEIGHT, 0);

        break;
      default:
        switch (getScoreMode()) {
          case DEFENSE:
            ELEVATOR.goHome();
            break;
          case AMP:
          case SPEAKER:
          default:
            switch (RobotContainer.getLocalizationState().getFieldZone()) {
              case ALLIANCE_WING:
              case ALLIANCE_STAGE:
              case OPPONENT_STAGE:
              case ALLIANCE_YIELD:
                ELEVATOR.goHome();
                break;
              case OPPONENT_YIELD:
              case OPPONENT_WING:
              case NEUTRAL_WING:
                if (SHOOTER.isCenterBroken()) {
                  ELEVATOR.setLengthInches(Constants.Elevator.PASS_ELEVATOR_HEIGHT);
                } else {
                  ELEVATOR.goHome();
                }
                break;
              default:
                ELEVATOR.goHome();
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
