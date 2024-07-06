// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stateless.recovery;

import static frc.robot.RobotContainer.ELEVATOR;
import static frc.robot.RobotContainer.INTAKE;
import static frc.robot.RobotContainer.SHOOTER;
import static frc.robot.RobotContainer.WRIST;
import static frc.robot.RobotContainer.setRobotState;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Wrist;
import frc.robot.util.InstCmd;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ReverseIntake extends SequentialCommandGroup {
    /** Creates a new ReverseIntake. */
    public ReverseIntake() {

        addCommands(
                new InstCmd(() -> setRobotState(RobotState.RECOVERY)),
                new InstCmd(
                        () -> {
                            SHOOTER.setFeederVoltage(-Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
                            INTAKE.setVolts(-Constants.Intake.INTAKE_COLLECT_VOLTS);
                            WRIST.setDegrees(21);
                            ELEVATOR.goHome();
                        }),
                new WaitCommand(3.0),
                new InstCmd(
                        () -> {
                            SHOOTER.stopFeeder();
                            INTAKE.stopBoth();
                        }),
                new InstCmd(() -> setRobotState(RobotState.DEFAULT)));
    }
}
