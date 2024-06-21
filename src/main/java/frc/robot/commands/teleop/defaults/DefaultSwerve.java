package frc.robot.commands.teleop.defaults;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.DriveMode;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.util.Util;
import frc.robot.util.zoning.FieldZones;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

import javax.lang.model.element.ModuleElement.ProvidesDirective;

import static frc.robot.RobotContainer.*;

/**
 * This command, when executed, instructs the drivetrain subsystem to drive
 * based on the specified
 * values from the controller(s). This command is designed to be the default
 * command for the
 * drivetrain subsystem.
 *
 * <p>
 * Requires: the Drivetrain subsystem
 *
 * <p>
 * Finished When: never
 *
 * <p>
 * At End: stops the drivetrain
 */
public class DefaultSwerve extends Command {

  private HolonomicDriveController HOLONOMIC_CONTROLLER;
  private double setPoint = 0.0;
  private FieldZones.Zone prevZone = FieldZones.Zone.ALLIANCE_WING;

  public DefaultSwerve() {
    this.HOLONOMIC_CONTROLLER = new HolonomicDriveController(new PIDController(1, 0, 0), new PIDController(1.0, 0, 0),
        new ProfiledPIDController(12.0, 0, 0,
            new TrapezoidProfile.Constraints(TunerConstants.kSpeedAt12VoltsMps, 3.8)));
    addRequirements(DRIVETRAIN);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    double xPercentage = Util
        .squareValue(Constants.Limiters.TRANSLATION_X_SLEW_RATE.calculate(-DRIVER_CONTROLLER.getLeftY()))
        * TunerConstants.kSpeedAt12VoltsMps;
    double yPercentage = Util
        .squareValue(Constants.Limiters.TRANSLATION_Y_SLEW_RATE.calculate(-DRIVER_CONTROLLER.getLeftX()))
        * TunerConstants.kSpeedAt12VoltsMps;
    double rotationPercentage = Util.squareValue(-DRIVER_CONTROLLER.getRightX()) * Math.PI * 3.5;
    FieldZones.Zone zone = RobotContainer.getLocalizationState().getFieldZone();
    System.out.println(zone);
    if (prevZone != FieldZones.Zone.ALLIANCE_WING && zone == FieldZones.Zone.ALLIANCE_WING) {
      setDriveMode(DriveMode.AUTOMATIC);
    }

    if (prevZone == FieldZones.Zone.OPPONENT_WING && zone == FieldZones.Zone.NEUTRAL_WING) {
      setDriveMode(DriveMode.AUTOMATIC);
    }

    prevZone = zone;

    if (Math.abs(rotationPercentage) > 0.25) {
      setDriveMode(DriveMode.MANUAL);
    }
    if (DRIVER_CONTROLLER.getRightTriggerAxis() > 0.3 || getDriveMode() == DriveMode.AUTOMATIC) {
      setDriveMode(DriveMode.AUTOMATIC);
      if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.NEUTRAL_WING) {
        rotationPercentage = getRPS(Util.getRotationToAllianceLob(DRIVETRAIN.getPose()));
      } else {
        switch (RobotContainer.getScoreMode()) {
          case SPEAKER:
            rotationPercentage = getRPS(Util.getRotationToAllianceSpeaker(DRIVETRAIN.getPose()));
            break;
          case AMP:
            rotationPercentage = getRPS(new Rotation2d(Math.toRadians(90.0)));
            break;
          default:
            break;

        }
      }
    } else if (DRIVER_CONTROLLER.getHID().getPOV() != -1) {
      setDriveMode(DriveMode.CARDINAL_LOCKING);
      if (DRIVER_CONTROLLER.getHID().getPOV() != -1) {
        setPoint = calculateSetpoint(DRIVER_CONTROLLER.getHID().getPOV());
      }

    }
    if (getDriveMode() == DriveMode.CARDINAL_LOCKING) {
      rotationPercentage = getRPS(new Rotation2d(Math.toRadians(setPoint)));
    }
    DRIVETRAIN
        .setControl(drive.withVelocityX(xPercentage).withVelocityY(yPercentage).withRotationalRate(rotationPercentage));
  }

  @Override
  public void end(boolean interrupted) {

  }

  public double getRPS(Rotation2d setpoint) {
    return HOLONOMIC_CONTROLLER.calculate(DRIVETRAIN.getPose(),
        DRIVETRAIN.getPose(), 0.0, setpoint).omegaRadiansPerSecond;
  }

  public int calculateSetpoint(int point) {
    switch (point) {
      case 0:
        return 180;

      case 90:
        return 90;

      case 180:
        return 0;
      case 270:
        return -90;

      default:
        return 0;
    }
  }
}
