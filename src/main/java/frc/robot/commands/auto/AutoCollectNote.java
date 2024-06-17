// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCollectNote extends ParallelDeadlineGroup {
  /** Creates a new AutoCollectNote. */
  public AutoCollectNote(final double initialVelocity) {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(
        new IntakeNoteSequenceAuto(
            RobotContainer.SHOOTER, RobotContainer.INTAKE, RobotContainer.ELEVATOR));
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstantCommand(() -> RobotContainer.WRIST.enableWristLockdown())
            .andThen(
                new DriveToNote2(
                    RobotContainer.DRIVETRAIN, RobotContainer.VISION, initialVelocity))
            .finallyDo(() -> RobotContainer.WRIST.disableWristLockdown()));
  }
}
