// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import frc.robot.util.InstCmd;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import static frc.robot.RobotContainer.DRIVETRAIN;
import static frc.robot.RobotContainer.WRIST;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCollectNote extends ParallelDeadlineGroup {
  /** Creates a new AutoCollectNote. */
  public AutoCollectNote(final DoubleSupplier initialVelocity) {
    super(new IntakeNoteSequenceAuto());
    addCommands(
      new InstCmd(() -> WRIST.enableWristLockdown())
        .andThen(new DriveToNote(initialVelocity.getAsDouble()))
        .finallyDo(() -> WRIST.disableWristLockdown())
    );
  }

  public AutoCollectNote() {
    this(() -> {
      final ChassisSpeeds speeds = DRIVETRAIN.getCurrentRobotChassisSpeeds();
      return Math.sqrt(speeds.vxMetersPerSecond * speeds.vxMetersPerSecond + speeds.vyMetersPerSecond * speeds.vyMetersPerSecond);
    });
  }
}
