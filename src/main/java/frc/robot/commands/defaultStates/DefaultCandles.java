package frc.robot.commands.defaultStates;

import frc.robot.RobotContainer;

public class DefaultCandles extends Default {
  public DefaultCandles() {
    addRequirements(RobotContainer.CANDLES);
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
