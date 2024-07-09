// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static frc.robot.RobotContainer.POOP_MONITOR;
import static frc.robot.RobotContainer.SHOOTER;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Amp_2_1_NoPoop extends SequentialCommandGroup {
  /** Creates a new Amp_2_1. */
  public Amp_2_1_NoPoop() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutoBuilder.buildAuto("amp_2-1_NoPoop_begin"),
      new ConditionalCommand(
        AutoBuilder.buildAuto("amp_2-1_NoPoop_1y"),
        AutoBuilder.buildAuto("amp_2-1_NoPoop_1n"),
        () -> SHOOTER.hasNote()
      ),
      AutoBuilder.buildAuto("amp_2-1_NoPoop_2y"),
      AutoBuilder.buildAuto("amp_2-1_end_NoPoop")
    );
  }
}
