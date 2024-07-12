// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stateless;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import static frc.robot.RobotContainer.DRIVETRAIN;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ReLocalizeSub extends InstantCommand {
  public ReLocalizeSub() {
    // Use addRequirements() here to declare subsystem dependencies.
    // ignoringDisable(true);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (DRIVETRAIN.getAlliance() == Alliance.Blue) {
      DRIVETRAIN.seedFieldRelative(
          new Pose2d(1.37, 5.52, Rotation2d.fromDegrees(0.0)));
    } else {
      DRIVETRAIN.seedFieldRelative(
          new Pose2d(15.2, 5.5, Rotation2d.fromDegrees(-180)));
    }
  }
}
