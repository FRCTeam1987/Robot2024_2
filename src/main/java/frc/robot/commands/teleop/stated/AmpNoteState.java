// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.commands.teleop.stateless.RumbleLock;
import frc.robot.RobotContainer;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AmpNoteState extends SequentialCommandGroup {
  /** Creates a new AmpNoteState. */
  public AmpNoteState() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> {
          RobotContainer.setRobotState(RobotState.AMP_PREP);
        }),
        new WaitUntilCommand(
            () -> !DRIVER_CONTROLLER.y().getAsBoolean() && ELEVATOR.isAtSetpoint() && WRIST.isAtSetpoint()),
        new WaitUntilCommand(() -> DRIVER_CONTROLLER.y().getAsBoolean()),
        new RumbleLock(DRIVER_CONTROLLER.getHID(), DRIVER_CONTROLLER.y(),
            () -> AMP_SENSORS.getBothSensors(true),
            () -> setRobotState(RobotState.AMP_SCORE)),
        new WaitUntilCommand(() -> !SHOOTER.isCenterBroken() && !SHOOTER.isRearBroken()),
        new InstCmd(() -> {
          setRobotState(RobotState.AMP_EXIT);
        }),
        new WaitUntilCommand(() -> DRIVER_CONTROLLER.y().getAsBoolean()),
        new InstCmd(() -> {
          setRobotState(RobotState.DEFAULT);
        }));
  }
}
