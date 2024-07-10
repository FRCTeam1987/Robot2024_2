// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static frc.robot.RobotContainer.SHOOTER;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Source_4_5 extends SequentialCommandGroup {
  /** Creates a new Source_4_5. */
  public Source_4_5() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutoBuilder.buildAuto("source_4-5_begin"),
      new ConditionalCommand(
        AutoBuilder.buildAuto("source_4-5_4y"),
        AutoBuilder.buildAuto("source_4-5_4n"),
        SHOOTER::hasNote
      ),
      AutoBuilder.buildAuto("source_4-5_end")
    );
  }
}
