// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.actions.PathFind;
import frc.robot.commands.auto.defaults.DefaultAutoShooter;
import frc.robot.commands.auto.defaults.DefaultAutoWrist;

import static frc.robot.RobotContainer.POOP_MONITOR;
import static frc.robot.RobotContainer.SHOOTER;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Source_5_4 extends ParallelCommandGroup {
  /** Creates a new Source_5_4. */
  public Source_5_4(final Alliance alliance) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    final String color = alliance.toString().toLowerCase();
    addCommands(
      new SequentialCommandGroup(
        AutoBuilder.buildAuto("source_5-4_" + color + "_initial"),
        new ConditionalCommand(
          AutoBuilder.buildAuto("source_5-4_" + color + "_5y4y"),
          new ConditionalCommand(
            AutoBuilder.buildAuto("source_5-4_" + color + "_5y4n"),
            new ConditionalCommand(
              AutoBuilder.buildAuto("source_5-4_" + color + "_5n4y"),
              AutoBuilder.buildAuto("source_5-4_" + color + "_5n4n"),
              () -> !POOP_MONITOR.hasPooped() && SHOOTER.hasNote()
            ),
            () -> POOP_MONITOR.hasPooped() && !SHOOTER.hasNote()
          ),
          () -> POOP_MONITOR.hasPooped() && SHOOTER.hasNote()
        ),
        AutoBuilder.buildAuto("source_5-4_" + color + "_preload"),
        PathFind.toSourceShot(),
        new AutoAimAndShoot()
      ),
      new DefaultAutoWrist(),
      new DefaultAutoShooter()
    );
  }
}
