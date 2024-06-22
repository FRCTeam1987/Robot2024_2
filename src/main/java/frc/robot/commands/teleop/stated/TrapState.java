// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TrapState extends SequentialCommandGroup {
  /** Creates a new TrapState. */
  private Debouncer LINE_DEBOUNCER;

  public TrapState() {
    LINE_DEBOUNCER = new Debouncer(Constants.Debouncers.TRAP_DEBOUNCE_TIME, DebounceType.kFalling);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(

        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.TRAP_ELEV_MIDWAY);
        }),
        new WaitUntilCommand(ELEVATOR::isAtSetpoint),
        new WaitCommand(0.5),
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.TRAP_WRIST_MIDWAY);
        }),
        new WaitUntilCommand(() -> WRIST.isAtSetpoint() && ELEVATOR.isAtSetpoint()),
        new WaitCommand(0.5),
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.TRAP_ELEV_FULL);
        }),
        new WaitUntilCommand(() -> WRIST.isAtSetpoint() && ELEVATOR.isAtSetpoint()),
        new WaitCommand(0.7),
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.TRAP_WRIST_FULL);
        }),
        new WaitCommand(0.3),
        new WaitUntilCommand(
            () -> SHOOTER.isShooterAtSetpoint()
                && WRIST.isAtSetpoint()
                && ELEVATOR.isAtSetpoint()),

        new InstCmd(() -> setRobotState(RobotState.TRAP_SCORE)),
        new WaitUntilCommand(() -> LINE_DEBOUNCER.calculate(!SHOOTER.isCenterBroken())),
        new WaitCommand(1.0),
        new InstCmd(() -> setRobotState(RobotState.TRAP_DOTHEJIGGLE))

    );
  }
}
