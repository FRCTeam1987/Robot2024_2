// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.logic.DriveMode;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DefaultState extends SequentialCommandGroup {
  /** Creates a new HomeState. */
  public DefaultState() {

    addRequirements(SEMAPHORE);

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> {
          setRobotState(RobotState.DEFAULT);
          setDriveMode(DriveMode.MANUAL);
          setScoreMode(ScoreMode.SPEAKER);
        }));
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
