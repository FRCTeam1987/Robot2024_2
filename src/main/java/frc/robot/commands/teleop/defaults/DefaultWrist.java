package frc.robot.commands.teleop.defaults;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class DefaultWrist extends Command {
  public DefaultWrist() {
    addRequirements(RobotContainer.WRIST);
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
