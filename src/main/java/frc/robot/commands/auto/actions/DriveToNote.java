// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveToNote extends PIDCommand {

  private static Debouncer createNoteDebouncer() {
    return new Debouncer(0.4, DebounceType.kFalling); // 0.6
  }

  private static final SwerveRequest.ApplyChassisSpeeds swerveRequest =
      new SwerveRequest.ApplyChassisSpeeds();
  private static Debouncer canSeeNoteDebouncer = createNoteDebouncer();

  /** Creates a new DriveToNote2. */
  public DriveToNote(final double initialVelocity) {
    // FIXME probably mixing degrees and radians
    super(
        // The controller that the command will use
        new PIDController(0.1, 0.0, 0.0),
        // This should return the measurement
        () -> RobotContainer.VISION.getYawVal(),
        // This should return the setpoint (can also be a constant)
        () -> 0.0,
        // This uses the output
        output -> {
          if (RobotContainer.SHOOTER.isRearBroken()) {
            RobotContainer.DRIVETRAIN.setControl(swerveRequest.withSpeeds(new ChassisSpeeds()));
            return;
          }
          RobotContainer.DRIVETRAIN.setControl(
              swerveRequest.withSpeeds(
                  new ChassisSpeeds(
                      canSeeNoteDebouncer.calculate(RobotContainer.VISION.hasTargets()) ? initialVelocity : 0.0,
                      0,
                      output * 0.75)));
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(RobotContainer.DRIVETRAIN);
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(0.25, 0.1);
  }

  @Override
  public void initialize() {
    canSeeNoteDebouncer = createNoteDebouncer();
    canSeeNoteDebouncer.calculate(true);
    super.initialize();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // Assume parallel deadline command stops this command.
  }
}
