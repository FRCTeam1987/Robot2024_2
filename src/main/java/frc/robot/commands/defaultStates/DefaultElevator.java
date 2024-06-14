package frc.robot.commands.defaultStates;

import frc.robot.RobotContainer;

public class DefaultElevator extends Default {
  public DefaultElevator() {
    addRequirements(RobotContainer.ELEVATOR);
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
