// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ClimbState extends SequentialCommandGroup {
  private final Debouncer A_DEBOUNCER;

  /** Creates a new ClimbState. */
  public ClimbState() {

    addRequirements(SEMAPHORE);

    A_DEBOUNCER = new Debouncer(0.05);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> setRobotState(RobotState.CLIMB_INIT)),
        new WaitUntilCommand(() -> !CODRIVER_CONTROLLER.y().getAsBoolean()),
        new WaitUntilCommand(
            () -> ELEVATOR.isAtSetpoint() && A_DEBOUNCER
                .calculate(CODRIVER_CONTROLLER.y().getAsBoolean())),
        new InstCmd(() -> setRobotState(RobotState.CLIMB_PULLDOWN)),
        new WaitUntilCommand(ELEVATOR::isAtSetpoint),
        new WaitCommand(0.8),
        new InstCmd(() -> setRobotState(RobotState.CLIMB_LEVEL)),
        new WaitUntilCommand(ELEVATOR::isAtSetpoint),
        new ConditionalCommand(
            new TrapState(),
            new InstCmd(() -> System.out.println("Climb Finished. No note.")),
            SHOOTER::isCenterBroken));
  }
}
