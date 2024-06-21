// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PodiumState extends SequentialCommandGroup {
  /** Creates a new PoopNoteState. */
  public PodiumState() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> RobotContainer.setRobotState(RobotState.PODIUM_PREP)),
        new WaitUntilCommand(() -> WRIST.isAtSetpoint() && SHOOTER.isShooterAtSetpoint() && ELEVATOR.isAtSetpoint()),
        new InstCmd(() -> RobotContainer.setRobotState(RobotState.PODIUM)),
        new WaitUntilCommand(() -> !SHOOTER.isCenterBroken()),
        new InstCmd(() -> RobotContainer.setRobotState(RobotState.DEFAULT)));
  }

}
