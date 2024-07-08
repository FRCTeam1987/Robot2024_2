// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.InstCmd;
import frc.robot.util.WaitUntilDebounceCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PodiumState extends SequentialCommandGroup {
  /** Creates a new PoopNoteState. */
  public PodiumState() {

    addRequirements(SEMAPHORE);

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> setRobotState(RobotState.PODIUM_PREP)),
        new WaitUntilDebounceCommand(
            () -> WRIST.isAtSetpoint() && SHOOTER.isShooterAtSetpoint() && ELEVATOR.isAtSetpoint(),
            0.04, DebounceType.kRising),
        new InstCmd(() -> setRobotState(RobotState.PODIUM)),
        new WaitUntilDebounceCommand(() -> !SHOOTER.isCenterBroken(), 0.02, DebounceType.kBoth),
        new InstCmd(() -> setRobotState(RobotState.DEFAULT)));
  }

}
