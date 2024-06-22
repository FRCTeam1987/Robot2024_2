// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.util.InstCmd;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.actions.AutoCollectNote;
import frc.robot.commands.auto.actions.PathFind;
import frc.robot.commands.auto.actions.RotateUntilNote;
import frc.robot.commands.auto.defaults.DefaultAutoShooter;
import frc.robot.commands.auto.defaults.DefaultAutoWrist;
import frc.robot.commands.teleop.defaults.DefaultIntake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Madtown extends ParallelCommandGroup {

  public static boolean SHOULD_WATCH_FOR_NOTE = false;
  private static boolean INITIAL_WAS_INTERRUPTED = false;

  /** Creates a new Madtown. */
  public Madtown(final Alliance alliance) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    Boolean isBlue = alliance == Alliance.Blue;
    final String color = alliance.toString().toLowerCase();
    addCommands(
      new SequentialCommandGroup(
        new ParallelRaceGroup(
          AutoBuilder.buildAuto("madtown_" + color + "_initial"),
          new WaitUntilCommand(() -> {
            final boolean shouldInterrupt = SHOULD_WATCH_FOR_NOTE
              && RobotContainer.VISION.hasTargets()
              && Math.abs(RobotContainer.VISION.getYawVal()) < 20.0;
            INITIAL_WAS_INTERRUPTED = shouldInterrupt;
            return shouldInterrupt;
        })),
        new ConditionalCommand(
          new AutoCollectNote(() -> 2.75).withTimeout(1.0)
            .andThen(PathFind.toSourceShot())
            .andThen(new AutoAimAndShoot()),
          new InstCmd(),
          () -> INITIAL_WAS_INTERRUPTED
        ),
        new RotateUntilNote(() -> isBlue),
        new AutoCollectNote(() -> 2.25),
        PathFind.toMadtownshot(),
        new AutoAimAndShoot(),
        // new InstantShoot(RobotContainer.SHOOTER),
        new RotateUntilNote(() -> isBlue),
        new AutoCollectNote(() -> 2.75),
        PathFind.toMadtownshot(),
        new AutoAimAndShoot()
      ),
      new DefaultIntake(),
      new DefaultAutoWrist(),
      new DefaultAutoShooter()
    );
  }
}
