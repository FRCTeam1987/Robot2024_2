package frc.robot.commands.teleop.stateless;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.util.Util;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

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
public class SwerveCommand extends Command {

  private final PIDController THETA_CONTROLLER;
  private final IntSupplier POV_DEGREE;
  private boolean isCardinalLocking = false;
  private double setPoint = 0.0;

  private final CommandSwerveDrivetrain DRIVETRAIN;
  private final DoubleSupplier TRANSLATION_X_SUPPLIER;
  private final DoubleSupplier TRANSLATION_Y_SUPPLIER;
  private final DoubleSupplier ROTATION_SUPPLIER;
  private final SwerveRequest.FieldCentric DRIVE_REQUEST;

  public SwerveCommand(
      CommandSwerveDrivetrain drivetrain,
      SwerveRequest.FieldCentric drive,
      DoubleSupplier translationXSupplier,
      DoubleSupplier translationYSupplier,
      DoubleSupplier rotationSupplier,
      IntSupplier povDegree) {

    POV_DEGREE = povDegree;
    this.DRIVETRAIN = drivetrain;
    this.DRIVE_REQUEST = drive;
    this.TRANSLATION_X_SUPPLIER = translationXSupplier;
    this.TRANSLATION_Y_SUPPLIER = translationYSupplier;
    this.ROTATION_SUPPLIER = rotationSupplier;

    addRequirements(drivetrain);
    THETA_CONTROLLER = new PIDController(0.2, 0.0, 0.0);
    THETA_CONTROLLER.enableContinuousInput(-180, 180);
    THETA_CONTROLLER.setTolerance(0.25, 0.1);
  }

  @Override
  public void initialize() {
    isCardinalLocking = false;
    setPoint = 0.0;
  }

  @Override
  public void execute() {
    double xPercentage =
        Util.squareValue(TRANSLATION_X_SUPPLIER.getAsDouble()) * TunerConstants.kSpeedAt12VoltsMps;
    double yPercentage =
        Util.squareValue(TRANSLATION_Y_SUPPLIER.getAsDouble()) * TunerConstants.kSpeedAt12VoltsMps;
    double rotationPercentage = Util.squareValue(-ROTATION_SUPPLIER.getAsDouble()) * Math.PI * 3.5;

    if (isCardinalLocking && !Util.isWithinTolerance(ROTATION_SUPPLIER.getAsDouble(), 0.0, 0.25)) {
      isCardinalLocking = false;
      setPoint = 0.0;
    } else if (POV_DEGREE.getAsInt() >= 0) {
      isCardinalLocking = true;
      switch (POV_DEGREE.getAsInt()) {
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
      rotationPercentage =
          THETA_CONTROLLER.calculate(DRIVETRAIN.getPose().getRotation().getDegrees(), setPoint);
    }
    
    DRIVETRAIN.setControl(
        DRIVE_REQUEST
            .withVelocityX(xPercentage) // Drive forward with
            // negative Y (forward)
            .withVelocityY(yPercentage) // Drive left with negative X (left)
            .withRotationalRate(rotationPercentage));
  }

  @Override
  public void end(boolean interrupted) {}
}
