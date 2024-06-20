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
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.util.Util;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

import javax.lang.model.element.ModuleElement.ProvidesDirective;

import static frc.robot.RobotContainer.*;

/**
 * This command, when executed, instructs the drivetrain subsystem to drive based on the specified
 * values from the controller(s). This command is designed to be the default command for the
 * drivetrain subsystem.
 *
 * <p>Requires: the Drivetrain subsystem
 *
 * <p>Finished When: never
 *
 * <p>At End: stops the drivetrain
 */
public class DefaultSwerve extends Command {

  private HolonomicDriveController HOLONOMIC_CONTROLLER;
  private double setPoint = 0.0;
  private boolean isCardinalLocking = false;

  public DefaultSwerve() {
    this.HOLONOMIC_CONTROLLER = new HolonomicDriveController(  new PIDController(1, 0, 0), new PIDController(1, 0, 0),
  new ProfiledPIDController(1, 0, 0,
    new TrapezoidProfile.Constraints(TunerConstants.kSpeedAt12VoltsMps, 3.5)));
    addRequirements(DRIVETRAIN);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
        double xPercentage =
        Util.squareValue(Constants.Limiters.TRANSLATION_X_SLEW_RATE.calculate(DRIVER_CONTROLLER.getLeftY())) * TunerConstants.kSpeedAt12VoltsMps;
    double yPercentage =
        Util.squareValue(Constants.Limiters.TRANSLATION_Y_SLEW_RATE.calculate(DRIVER_CONTROLLER.getLeftX())) * TunerConstants.kSpeedAt12VoltsMps;
    double rotationPercentage = Util.squareValue(-DRIVER_CONTROLLER.getRightX()) * Math.PI * 3.5;
    switch(RobotContainer.STATE) {
      case COLLECTING:
      case COLLECTING_SLOW:
      case DEFAULT:
      case POOPING:
      case POOP_PREP:
      case SHOOTING:
      case SHOOT_PREP:
      default:

      if (isCardinalLocking && rotationPercentage > 0.0) {
        isCardinalLocking = false;
        setPoint = 0.0;
        // DriverStation.reportWarning("Stop using DPad.", false);
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
        DRIVETRAIN.setControl(
        new SwerveRequest.ApplyChassisSpeeds().withSpeeds(HOLONOMIC_CONTROLLER.calculate(DRIVETRAIN.getPose(), DRIVETRAIN.getPose(), 0.0, new Rotation2d(Math.toRadians(setPoint)))));
        
      }
 else {
    
    DRIVETRAIN.setControl(
        drive
            .withVelocityX(xPercentage) // Drive forward with
            // negative Y (forward)
            .withVelocityY(yPercentage) // Drive left with negative X (left)
            .withRotationalRate(rotationPercentage));
            break;
 }
    }
    

  }

  @Override
  public void end(boolean interrupted) {}
}
