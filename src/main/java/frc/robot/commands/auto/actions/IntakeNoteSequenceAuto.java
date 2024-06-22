// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import frc.robot.util.InstCmd;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.logic.AutoState;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeNoteSequenceAuto extends SequentialCommandGroup {

  // private final Debouncer hasNote = new Debouncer(0.02, DebounceType.kRising);

  /** Creates a new IntakeNoteSequence. */
  public IntakeNoteSequenceAuto() {
    addCommands(
      new InstCmd(() -> {
        RobotContainer.setAutoState(AutoState.COLLECTING);
      }),
      new WaitUntilCommand(() -> RobotContainer.SHOOTER.isCenterBroken()),
      new InstCmd(() -> {
        RobotContainer.setAutoState(AutoState.DEFAULT);
      }));
  }
}
