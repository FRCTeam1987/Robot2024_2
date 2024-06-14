package frc.robot.commands.defaultStates;

import frc.robot.RobotContainer;

public class DefaultIntake extends Default{
  public DefaultIntake() {
    addRequirements(RobotContainer.INTAKE);
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
