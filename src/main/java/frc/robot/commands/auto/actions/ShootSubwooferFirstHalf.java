// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import frc.robot.util.InstCmd;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootSubwooferFirstHalf extends SequentialCommandGroup {
  /** Creates a new ShootTrap. */
  public ShootSubwooferFirstHalf() {
    addCommands(
      new InstCmd(() -> {
          RobotContainer.WRIST.setDegrees(52);
          RobotContainer.ELEVATOR.setLengthInches(6.5);
          RobotContainer.SHOOTER.setRPMShoot(2000); // 2750
        },
        RobotContainer.ELEVATOR,
        RobotContainer.SHOOTER
      ),
      new WaitUntilCommand(() ->
        RobotContainer.ELEVATOR.isAtSetpoint() &&
        RobotContainer.WRIST.isAtSetpoint() &&
        RobotContainer.SHOOTER.isShooterAtSetpoint()
      ),
      new InstCmd(() -> RobotContainer.SHOOTER.setFeederVoltage(7.5), RobotContainer.SHOOTER),
      new WaitUntilCommand(() -> !RobotContainer.SHOOTER.isCenterBroken())
    );
  }
}
