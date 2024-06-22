// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.logic.AutoState;

public class InstantShoot extends Command {

  private Debouncer m_shotDebouncer;

  /** Creates a new InstantShoot. */
  public InstantShoot() {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shotDebouncer = new Debouncer(Constants.Shooter.SHOT_DEBOUNCE_TIME, DebounceType.kFalling);
    RobotContainer.setAutoState(AutoState.SHOOTING);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.setAutoState(AutoState.COLLECTING);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !m_shotDebouncer.calculate(RobotContainer.SHOOTER.isCenterBroken() && RobotContainer.SHOOTER.isRearBroken());
  }
}
