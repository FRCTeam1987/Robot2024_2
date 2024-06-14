package frc.robot.commands.defaultStates;

import frc.robot.RobotContainer;

public class DefaultWrist extends Default{
  public DefaultWrist() {
    addRequirements(RobotContainer.WRIST);
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
