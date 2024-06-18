package frc.robot.commands.teleop.defaults;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import static frc.robot.RobotContainer.INTAKE;

public class DefaultIntake extends Command{

  public DefaultIntake() {
    addRequirements(RobotContainer.INTAKE);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch(RobotContainer.STATE) {
        case COLLECTING:
        INTAKE.setVolts(Constants.Intake.INTAKE_COLLECT_VOLTS);
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
