package frc.robot.commands.teleop.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import static frc.robot.RobotContainer.INTAKE;

public class DefaultIntake extends Command {

  public DefaultIntake() {
    addRequirements(RobotContainer.INTAKE);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch (RobotContainer.STATE) {
      case COLLECTING:
        INTAKE.setVolts(Constants.Intake.INTAKE_COLLECT_VOLTS);
        break;
      case COLLECTING_SLOW:
        INTAKE.setVolts(Constants.Intake.INTAKE_COLLECT_SLOW_VOLTS);
        break;
      default:
        INTAKE.stopBoth();
        break;

    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
