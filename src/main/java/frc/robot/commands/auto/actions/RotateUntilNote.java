// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import java.util.function.BooleanSupplier;

public class RotateUntilNote extends Command {

  private static final SwerveRequest.ApplyChassisSpeeds swerveRequest =
      new SwerveRequest.ApplyChassisSpeeds();

  private double shouldTurnClockwise;
  private final BooleanSupplier isClockwise;

  /** Creates a new RotateInPlace. */
  public RotateUntilNote(final BooleanSupplier isClockwise) {
    this.isClockwise = isClockwise;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.DRIVETRAIN);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shouldTurnClockwise = isClockwise.getAsBoolean() ? -1.0 : 1.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double velocityScale = RobotContainer.VISION.hasTargets() ? 0.5 : 1.5;
    RobotContainer.DRIVETRAIN.setControl(
        swerveRequest.withSpeeds(
            new ChassisSpeeds(0.0, 0.0, (Math.PI * velocityScale) * shouldTurnClockwise)));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.DRIVETRAIN.setControl(swerveRequest.withSpeeds(new ChassisSpeeds()));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.VISION.hasTargets()
        && Math.abs(RobotContainer.VISION.getYawVal()) < 7.5;
  }
}
