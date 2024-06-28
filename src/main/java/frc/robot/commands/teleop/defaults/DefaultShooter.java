package frc.robot.commands.teleop.defaults;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.util.Util;
import frc.robot.util.interpolable.InterpolatingDouble;
import frc.robot.util.zoning.FieldZones;
import frc.robot.util.zoning.LocalizationState;

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
                if (!SHOOTER.isCenterBroken())
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
                        .getInterpolated(new InterpolatingDouble(
                                RobotContainer.getLocalizationState().getAmpPassDistance())).value);
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
            case CLIMB_INIT:
                SHOOTER.stopShooter();
                if (SHOOTER.isCenterBroken() && SHOOTER.getRPMLeader() < 1)
                    SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
                break;
            case CLIMB_PULLDOWN:
            case CLIMB_LEVEL:
                SHOOTER.stopShooter();
                if (!SHOOTER.isRearBroken() && SHOOTER.isShooterAtSetpoint())
                    SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDBACKWARD);
                break;
            case TRAP_ELEV_MIDWAY:
                SHOOTER.stopFeeder();
                SHOOTER.stopShooter();
                break;
            case TRAP_WRIST_MIDWAY:
            case TRAP_ELEV_FULL:
            case TRAP_WRIST_FULL:
                SHOOTER.setRPMShootNoSpin(Constants.Shooter.TRAP_RPM_SPEED);
                SHOOTER.setFeederVoltage(-0.2);
                break;
            case TRAP_SCORE:
                SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_SHOOT_VOLTS);
                break;
            case TRAP_DOTHEJIGGLE:
                SHOOTER.stopFeeder();
                break;
            default:
                SHOOTER.stopFeeder();
                final LocalizationState localizationState = RobotContainer.getLocalizationState();
                switch (getScoreMode()) {
                    case DEFENSE:
                        SHOOTER.stopFeeder();
                        break;
                    case AMP:
                    case SPEAKER:
                    default:
                        switch (RobotContainer.getLocalizationState().getFieldZone()) {
                            case ALLIANCE_WING:
                                if (SCORE_MODE == ScoreMode.SPEAKER) {
                                    if (SHOOTER.hasNote()) {
                                        if (Util.isWithinTolerance(
                                                RobotContainer.DRIVETRAIN.getPose().getRotation().getDegrees(),
                                                localizationState.getSpeakerAngle().getDegrees(),
                                                15.0)) {
                                            SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
                                        } else {
                                            SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM - 1800);
                                        }
                                    } else {
                                        SHOOTER.stopShooter();
                                    }

                                } else {
                                    SHOOTER.stopShooter();
                                }
                                break;
                            case OPPONENT_WING:
                                SHOOTER.setRPMShoot(Constants.DISTANCE_TO_PASS_RPM
                                        .getInterpolated(
                                                new InterpolatingDouble(
                                                        localizationState.getCenterPassDistance())).value);
                                break;
                            case NEUTRAL_WING:
                                SHOOTER.setRPMShoot(Constants.DISTANCE_TO_PASS_RPM
                                        .getInterpolated(
                                                new InterpolatingDouble(localizationState.getAmpPassDistance())).value);
                                break;
                            default:
                                WRIST.goHome();
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
