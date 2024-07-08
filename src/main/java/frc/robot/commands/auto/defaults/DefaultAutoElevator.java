// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import static frc.robot.RobotContainer.getAutoState;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DefaultAutoElevator extends Command {
  /** Creates a new DefaultAutoElevator. */
  public DefaultAutoElevator() {
    addRequirements(RobotContainer.ELEVATOR);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (getAutoState()) {
      case INSTANT_SUB_PREP:
      case INSTANT_SUB:
        RobotContainer.ELEVATOR.setLengthInches(Constants.Elevator.SUBWOOFER_SHOT_HEIGHT, 0);
        break;
      default:
        RobotContainer.ELEVATOR.goHome();
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
