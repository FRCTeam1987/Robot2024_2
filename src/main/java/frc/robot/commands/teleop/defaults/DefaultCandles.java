package frc.robot.commands.teleop.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class DefaultCandles extends Command {
  public DefaultCandles() {
    addRequirements(RobotContainer.CANDLES);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch(RobotContainer.STATE) {
        case COLLECTING:
            break;
        case COLLECT_PREP:
            break;
        case DEFAULT:
            break;
        case POOPING:
            break;
        case POOP_PREP:
            break;
        case SHOOTING:
            break;
        case SHOOT_PREP:
            break;
        default:
            break;

      }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
