package frc.robot.commands.teleop.defaults;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.logic.ScoreMode;
import frc.robot.util.Util;
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
    switch(RobotContainer.STATE) {
        case COLLECTING:
            break;
        case COLLECTING_SLOW:
            break;
        case DEFAULT:
            if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING) {
                if (MODE == ScoreMode.SPEAKER) {
                    if (SHOOTER.isCenterBroken()) {
                        if (!Util.isValidShot()) {
                          WRIST.setDegrees(35.0);
                        } else {
                          WRIST.setDegrees(Util.getInterpolatedWristAngleSpeaker());
                    } 
                }else {
                      WRIST.setDegrees(12.0);
                    }
            }
        }
            break;
        case POOPING:
            break;
        case POOP_PREP:
            break;
        case SHOOTING:
                WRIST.setDegrees(Util.getInterpolatedWristAngleSpeaker());
            break;
        case SHOOT_PREP:
            break;
        default:
            break;

      }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
