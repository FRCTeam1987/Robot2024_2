// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import static frc.robot.RobotContainer.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AimAwayFromSpeaker extends ProfiledPIDCommand {
  /** Creates a new AimAtSpeaker. */
  public AimAwayFromSpeaker() {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            9.0,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(MaxAngularRate * 1.5, MaxAngularRate * 0.75)),
        // This should return the measurement
        () -> DRIVETRAIN.getPose().getRotation().getRadians(),
        // This should return the goal (can also be a constant)
        () -> new TrapezoidProfile.State(getLocalizationState().speakerAngle().plus(Rotation2d.fromDegrees(180.0)).getRadians(), 0.0),
        // This uses the output
        (output, setpoint) -> {
          // Use the output (and setpoint, if desired) here
          DRIVETRAIN.setControl(drive.withVelocityX(0.0).withVelocityY(0.0).withRotationalRate(output));
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-Math.PI, Math.PI);
    getController().setTolerance(
        Rotation2d.fromDegrees(1.5).getRadians(),
        Rotation2d.fromDegrees(1.5).getRadians());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
