package frc.robot.commands.teleop.defaults;

import static frc.robot.RobotContainer.ELEVATOR;
import static frc.robot.RobotContainer.*;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.util.Util;
import frc.robot.util.interpolable.InterpolatingDouble;
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
      default:
        if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING) {
          ELEVATOR.goHome();
        } else if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.NEUTRAL_WING) {
          if (SHOOTER.isCenterBroken()) {
            ELEVATOR.setLengthInches(Constants.Elevator.PASS_ELEVATOR_HEIGHT);
          } else {
            ELEVATOR.goHome();
          }
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
