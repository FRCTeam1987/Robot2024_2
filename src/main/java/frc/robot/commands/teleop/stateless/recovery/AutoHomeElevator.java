// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stateless.recovery;

import static frc.robot.RobotContainer.ELEVATOR;
import static frc.robot.RobotContainer.setRobotState;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoHomeElevator extends SequentialCommandGroup {
  /** Creates a new AutoHomeElevator. */
  public AutoHomeElevator() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> setRobotState(RobotState.RECOVERY)),
        new InstCmd(() -> ELEVATOR.setVoltage(-2.0)),
        new WaitCommand(0.5),
        new WaitUntilCommand(() -> ELEVATOR.getVelocity() == 0.0),
        new InstCmd(ELEVATOR::zeroPosition),
        new InstCmd(ELEVATOR::stop),
        new InstCmd(() -> setRobotState(RobotState.DEFAULT)));
  }
}
