// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.setAutoState;
import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.commands.teleop.logic.RobotState;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class InstantSub extends SequentialCommandGroup {
  /** Creates a new ShootTrap. */
  public InstantSub() {
    addCommands(
        new InstCmd(() -> setAutoState(AutoState.INSTANT_SUB_PREP)),
        new WaitUntilCommand(
            () -> WRIST.isAtSetpoint(0.003) && ELEVATOR.isAtSetpoint(1.0) && SHOOTER.isShooterAtSetpoint()),
        new InstCmd(() -> setAutoState(AutoState.INSTANT_SUB)),
        new WaitUntilCommand(() -> !SHOOTER.isCenterBroken()),
        new InstCmd(() -> setAutoState(AutoState.COLLECTING)));
  }
}
