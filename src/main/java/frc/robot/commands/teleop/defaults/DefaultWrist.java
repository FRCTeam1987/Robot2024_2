package frc.robot.commands.teleop.defaults;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.util.Util;
import frc.robot.util.interpolable.InterpolatingDouble;
import frc.robot.util.zoning.FieldZones;

import static frc.robot.RobotContainer.*;

public class DefaultWrist extends Command {
  public DefaultWrist() {
    addRequirements(RobotContainer.WRIST);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch (RobotContainer.STATE) {
      case SHOOTING:
        WRIST.setDegrees(Util.getInterpolatedWristAngleSpeaker());
        break;
      case AMP_PREP:
      case AMP_SCORE:
        WRIST.setDegrees(Constants.Wrist.AMP_WRIST_DEGREES);
        break;
      case PASS:
        WRIST.setDegrees(Constants.Wrist.PASS_WRIST_DEGREES);
        break;
      case POOPING_PREP:
      case POOPING:
        WRIST.setDegrees(Constants.Wrist.POOP_DEG);
        break;
      case AMP_EXIT:
        WRIST.goHome();
        break;
      default:
        if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING) {
          if (MODE == ScoreMode.SPEAKER) {
            if (SHOOTER.isCenterBroken()) {
              if (!Util.isValidShot()) {
                WRIST.setDegrees(35.0);
              } else {
                WRIST.setDegrees(Util.getInterpolatedWristAngleSpeaker());
              }
            } else {
              WRIST.setDegrees(12.0);
            }
          }
        } else if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.NEUTRAL_WING) {
          if (SHOOTER.isCenterBroken()) {
            WRIST.setDegrees(Constants.Wrist.PASS_WRIST_DEGREES);
          } else {
            WRIST.goHome();
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
