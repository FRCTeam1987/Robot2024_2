// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class InstantShoot extends Command {

  private static final double SHOT_DEBOUNCE_TIME = 0.08; // 0.06

  private final Shooter m_shooter;
  private Debouncer m_shotDebouncer;

  /** Creates a new InstantShoot. */
  public InstantShoot(final Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shotDebouncer = new Debouncer(SHOT_DEBOUNCE_TIME, DebounceType.kFalling);
    RobotContainer.setAutoState(AutoState.SHOOTING);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_shooter.setFeederVoltage(Constants.Shooter.FEEDER_AUTO_VOLTS + 2.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_shooter.stopFeeder();
    RobotContainer.setAutoState(AutoState.COLLECTING);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !m_shotDebouncer.calculate(m_shooter.isCenterBroken());
  }
}
