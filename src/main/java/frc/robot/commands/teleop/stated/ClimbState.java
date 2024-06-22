// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ClimbState extends SequentialCommandGroup {
  /** Creates a new ClimbState. */
  public ClimbState() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.CLIMB_INIT);
        }),
        new WaitUntilCommand(() -> ELEVATOR.isAtSetpoint() && DRIVER_CONTROLLER.a().getAsBoolean()),
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.CLIMB_PULLDOWN);
        }),
        new WaitUntilCommand(ELEVATOR::isAtSetpoint),
        new WaitCommand(0.6),
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.CLIMB_LEVEL);
        }),
        new WaitUntilCommand(ELEVATOR::isAtSetpoint),
        new ConditionalCommand(
            new TrapState(),
            new InstCmd(() -> System.out.println("Climb Finished. No note.")),
            SHOOTER::isCenterBroken));
  }
}
