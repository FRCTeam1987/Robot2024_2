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
public class Source_5_4_No_Color extends SequentialCommandGroup {
  /** Creates a new Source_5_4. */
  public Source_5_4_No_Color() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutoBuilder.buildAuto("source_5-4_initial"),
      new ConditionalCommand(
        AutoBuilder.buildAuto("source_5-4_5y4y"),
        new ConditionalCommand(
          AutoBuilder.buildAuto("source_5-4_5y4n"),
          new ConditionalCommand(
            AutoBuilder.buildAuto("source_5-4_5n4y"),
            AutoBuilder.buildAuto("source_5-4_5n4n"),
            () -> !POOP_MONITOR.hasPooped() && SHOOTER.hasNote()
          ),
          () -> POOP_MONITOR.hasPooped() && !SHOOTER.hasNote()
        ),
        () -> POOP_MONITOR.hasPooped() && SHOOTER.hasNote()
      ),
      AutoBuilder.buildAuto("source_5-4_preload2")
    );
  }
}
