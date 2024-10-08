// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.InstCmd;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class PoopMonitor extends SubsystemBase {

  private boolean hasPooped = false;
  private boolean shouldMonitor = false;

  public boolean hasPooped() {
    return hasPooped;
  }

  public void resetPooped() {
    hasPooped = false;
  }

  public void startMonitor() {
    shouldMonitor = true;
  }

  public void stopMonitor() {
    shouldMonitor = false;
  }

  public Command StartPoopMonitorCommand() {
    return new InstCmd(this::startMonitor);
  }

  public Command StopPoopMonitorCommand() {
    return new InstCmd(this::stopMonitor);
  }

  public Command ResetPoopMonitorCommand() {
    return new InstCmd(this::resetPooped);
  }

  /** Creates a new PoopMonitor. */
  public PoopMonitor() {}

  @Override
  public void periodic() {
    if (shouldMonitor && !hasPooped && RobotContainer.SHOOTER.hasNote()) {
      hasPooped = true;
    }
  }
}
