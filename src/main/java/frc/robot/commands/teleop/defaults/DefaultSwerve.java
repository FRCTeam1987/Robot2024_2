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
  private boolean isCardinalLocking = false;
  public boolean isAutomatic = false;

  public DefaultSwerve() {
    this.HOLONOMIC_CONTROLLER = new HolonomicDriveController(new PIDController(1, 0, 0), new PIDController(1.0, 0, 0),
        new ProfiledPIDController(12.0, 0, 0,
            new TrapezoidProfile.Constraints(TunerConstants.kSpeedAt12VoltsMps, 3.0)));
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

    if ((isAutomatic || isCardinalLocking) && rotationPercentage > 0.0) {
      isAutomatic = false;
      isCardinalLocking = false;
      setPoint = 0.0;
    } else if (DRIVER_CONTROLLER.getHID().getPOV() >= 0) {
      isCardinalLocking = true;
      switch (DRIVER_CONTROLLER.getHID().getPOV()) {
        case 0:
          setPoint = 180;
          break;
        case 90:
          setPoint = 90;
          break;
        case 180:
          setPoint = 0;
          break;
        case 270:
          setPoint = -90;
          break;
        default:
          break;
      }
    }
    if (isCardinalLocking) {
      rotationPercentage = getRPS(new Rotation2d(Math.toRadians(setPoint)));
    } else {
      if (DRIVER_CONTROLLER.getRightTriggerAxis() > 0.3 || isAutomatic) {
        isAutomatic = true;
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
      }
    }
    DRIVETRAIN.setControl(
        drive
            .withVelocityX(xPercentage)
            .withVelocityY(yPercentage)
            .withRotationalRate(rotationPercentage));

  }

  @Override
  public void end(boolean interrupted) {

  }

  public double getRPS(Rotation2d setpoint) {
    return HOLONOMIC_CONTROLLER.calculate(DRIVETRAIN.getPose(),
        DRIVETRAIN.getPose(), 0.0, setpoint).omegaRadiansPerSecond;
  }
}
