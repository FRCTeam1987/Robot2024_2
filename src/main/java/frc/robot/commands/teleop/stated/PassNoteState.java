// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import static frc.robot.RobotContainer.ELEVATOR;
import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PassNoteState extends SequentialCommandGroup {
  /** Creates a new PassNoteState. */
  public PassNoteState() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new WaitUntilCommand(() -> ELEVATOR.isAtSetpoint() && SHOOTER.isShooterAtSetpoint() && WRIST.isAtSetpoint()),
        new InstCmd(() -> RobotContainer.setRobotState(RobotState.PASS)),
        new WaitUntilCommand(() -> !SHOOTER.isCenterBroken()),
        new InstCmd(() -> RobotContainer.setRobotState(RobotState.DEFAULT)));
  }
}
