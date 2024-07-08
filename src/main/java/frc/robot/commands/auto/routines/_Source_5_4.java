// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.actions.AutoCollectNote;
import frc.robot.commands.auto.actions.PathFind;

import static frc.robot.RobotContainer.SHOOTER;
import static frc.robot.RobotContainer.VISION;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class _Source_5_4 extends SequentialCommandGroup {
  /** Creates a new _Source_5_4. */
  public _Source_5_4() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutoBuilder.buildAuto("_source_5-4_begin"),
      new ConditionalCommand(
        AutoBuilder.buildAuto("_source_5-4_5y").andThen(
          new ConditionalCommand(
            AutoBuilder.buildAuto("_source_5-4_4vis"),  // auto collect note 4, got to shooting pose
            AutoBuilder.buildAuto("_source_5-4_5y4novis"),  // Note 4 not visible, go to shooting pose
                  VISION::hasTargets
          )
        ),
        AutoBuilder.buildAuto("_source_5-4_5n"),  // Did not get note 5, immediately try to get note 4
              SHOOTER::hasNote
      ),
      new RepeatCommand(new SequentialCommandGroup(
        new AutoCollectNote(),
        PathFind.toSourceShot(),
        new AutoAimAndShoot()
      ))
    );
  }
}
