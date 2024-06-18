// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import frc.robot.util.InstCmd;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCollectNote extends ParallelDeadlineGroup {
  /** Creates a new AutoCollectNote. */
  public AutoCollectNote(final double initialVelocity) {
    super(new IntakeNoteSequenceAuto());
    addCommands(
      new InstCmd(() -> RobotContainer.WRIST.enableWristLockdown())
        .andThen(new DriveToNote(initialVelocity))
        .finallyDo(() -> RobotContainer.WRIST.disableWristLockdown())
    );
  }
}
