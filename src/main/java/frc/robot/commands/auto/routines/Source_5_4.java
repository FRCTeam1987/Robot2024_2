// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.defaults.DefaultAutoShooter;
import frc.robot.commands.auto.defaults.DefaultAutoWrist;
import frc.robot.util.Util;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Source_5_4 extends ParallelCommandGroup {
  /** Creates a new Source_5_4. */
  public Source_5_4() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SequentialCommandGroup(
        AutoBuilder.buildAuto("Source 5-4 AB"), // Source 5-4 Initial
        new ConditionalCommand(
          AutoBuilder.buildAuto("Source 5-4 B 5Y4Y"),
          new ConditionalCommand(
            AutoBuilder.buildAuto("Source 5-4 5Y4N"),
            new ConditionalCommand(
              AutoBuilder.buildAuto("Source 5-4 5N4Y"),
              AutoBuilder.buildAuto("Source 5-4 5N4N"),
              () -> !RobotContainer.POOP_MONITOR.hasPooped() && RobotContainer.SHOOTER.hasNote()
            ),
            () -> RobotContainer.POOP_MONITOR.hasPooped() && !RobotContainer.SHOOTER.hasNote()
          ),
          () -> RobotContainer.POOP_MONITOR.hasPooped() && RobotContainer.SHOOTER.hasNote()
        ),
        new ParallelDeadlineGroup(
          new WaitUntilCommand(() -> RobotContainer.SHOOTER.hasNote()),
          AutoBuilder.buildAuto("Source 5-4 Preload")
        ).andThen(Util.PathFindToAutoSourceShot())
        .andThen(new AutoAimAndShoot())
      ),
      new DefaultAutoWrist(),
      new DefaultAutoShooter()
    );
  }
}
