package frc.robot.commands.teleop.defaults;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.logic.ScoreMode;
import frc.robot.util.zoning.FieldZones;

public class DefaultShooter extends Command{
  public DefaultShooter() {
    addRequirements(RobotContainer.SHOOTER);
  }
  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch(RobotContainer.STATE) {
        case COLLECTING:
            SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
            break;
        case COLLECTING_SLOW:
            SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS_SLOW);
            break;
        case POOPING:
        case POOP_PREP:
            break;
        case SHOOTING:
            SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_SHOOT_VOLTS);
            break;
        case SHOOT_PREP:
            break;
        case DEFAULT:
                    SHOOTER.stopFeeder();
            if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING) {
                if (MODE == ScoreMode.SPEAKER) {
                    SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
                }
            }
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
