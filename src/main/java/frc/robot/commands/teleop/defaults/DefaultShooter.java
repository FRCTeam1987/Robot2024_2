package frc.robot.commands.teleop.defaults;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.util.Util;
import frc.robot.util.interpolable.InterpolatingDouble;
import frc.robot.util.zoning.FieldZones;

public class DefaultShooter extends Command {
    public DefaultShooter() {
        addRequirements(RobotContainer.SHOOTER);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        switch (RobotContainer.STATE) {
            case AMP_PREP:
                SHOOTER.stopShooter();
                SHOOTER.stopFeeder();
                break;
            case AMP_SCORE:
                SHOOTER.setFeederVoltage(Constants.Shooter.AMP_FEEDER_VOLTAGE);
                break;
            case AMP_EXIT:
                SHOOTER.stopFeeder();
                break;
            case COLLECTING:
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
                break;
            case COLLECTING_SLOW:
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS_SLOW);
                break;
            case POOPING_PREP:
                SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_POOP_RPM);
                break;
            case POOPING:
                SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_POOP_RPM);
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
                break;
            case PASS:
                SHOOTER.setRPMShoot(Constants.DISTANCE_TO_PASS_RPM
                        .getInterpolated(new InterpolatingDouble(Util.getDistanceToPass())).value);
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
                break;
            case SHOOTING:
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_SHOOT_VOLTS);
                break;
            case SUBWOOFER_PREP:
                SHOOTER.setRPMShoot(Constants.Shooter.SUBWOOFER_SHOT_RPM);
                break;
            case SUBWOOFER:
                SHOOTER.setRPMShoot(Constants.Shooter.SUBWOOFER_SHOT_RPM);
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS_AGRESSIVE);
                break;
            case PODIUM_PREP:
                SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
                break;
            case PODIUM:
                SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS_AGRESSIVE);
                break;
            default:
                SHOOTER.stopFeeder();
                if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING) {
                    if (SCORE_MODE == ScoreMode.SPEAKER) {
                        SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM - 1800);
                    } else {
                        SHOOTER.stopShooter();
                    }
                } else if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.NEUTRAL_WING) {
                    SHOOTER.setRPMShoot(Constants.DISTANCE_TO_PASS_RPM
                            .getInterpolated(new InterpolatingDouble(Util.getDistanceToPass())).value);
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
