// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.statechangers;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.logic.RobotState;
import frc.robot.util.InstCmd;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeNoteState extends SequentialCommandGroup {
  /** Creates a new IntakeNoteState. */
  public IntakeNoteState() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstCmd(() -> {
        RobotContainer.setRobotState(RobotState.COLLECTING);
      }),
      new WaitUntilCommand(() -> RobotContainer.SHOOTER.isRearBroken()),
      new InstCmd(() -> {
        RobotContainer.setRobotState(RobotState.DEFAULT);
      })
    );
  }
}
