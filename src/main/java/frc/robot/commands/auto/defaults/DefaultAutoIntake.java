// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import static frc.robot.RobotContainer.INTAKE;
import static frc.robot.RobotContainer.SHOOTER;

public class DefaultAutoIntake extends Command {
  /** Creates a new DefaultAutoIntake. */
  public DefaultAutoIntake() {
    addRequirements(INTAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch(RobotContainer.getAutoState()) {
      case COLLECTING:
        if (SHOOTER.isCenterBroken()) {
          INTAKE.stopBoth();
        } else {
          INTAKE.setRPM(Constants.Intake.INTAKE_RPM);
        }
        break;
      case DEFAULT:
      default:
        INTAKE.stopBoth();
    }
  }
}
