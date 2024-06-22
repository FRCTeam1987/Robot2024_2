// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.actions.PathFind;
import frc.robot.commands.auto.defaults.DefaultAutoIntake;
import frc.robot.commands.auto.defaults.DefaultAutoShooter;
import frc.robot.commands.auto.defaults.DefaultAutoWrist;

import static frc.robot.RobotContainer.POOP_MONITOR;
import static frc.robot.RobotContainer.SHOOTER;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Amp_1_2 extends ParallelCommandGroup {
  /** Creates a new Amp_1_2. */
  public Amp_1_2() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SequentialCommandGroup(
        AutoBuilder.buildAuto("amp_1-2_initial"),
        new ConditionalCommand(
          AutoBuilder.buildAuto("amp_1-2_1y2y"),
          new ConditionalCommand(
            AutoBuilder.buildAuto("amp_1-2_1y2n"),
            new ConditionalCommand(
              AutoBuilder.buildAuto("amp_1-2_1n2y"),
              AutoBuilder.buildAuto("amp_1-2_1n2n"),
              () -> POOP_MONITOR.hasPooped() && SHOOTER.hasNote()
              ),
            () -> POOP_MONITOR.hasPooped() && !SHOOTER.hasNote()
          ),
          () -> POOP_MONITOR.hasPooped() && SHOOTER.hasNote()
        ),
        AutoBuilder.buildAuto("amp_1-2_preload"),
        PathFind.toAmpShot(),
        new AutoAimAndShoot()
      ),
      new DefaultAutoIntake(),
      new DefaultAutoWrist(),
      new DefaultAutoShooter()
    );
  }
}
