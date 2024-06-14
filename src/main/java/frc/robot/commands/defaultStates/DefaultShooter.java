package frc.robot.commands.defaultStates;

import frc.robot.RobotContainer;

public class DefaultShooter extends Default{
  public DefaultShooter() {
    addRequirements(RobotContainer.SHOOTER);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void defaulting() {

  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
