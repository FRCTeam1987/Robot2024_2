package frc.robot.commands.teleop.defaults;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Candles.CandleSide;

public class DefaultCandles extends Command {
    public DefaultCandles() {
        addRequirements(RobotContainer.CANDLES);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        switch (getScoreMode()) {
            case AMP:
                CANDLES.setColor(CandleSide.LEFT, new Color8Bit(255, 0, 0));
                break;
            case DEFENSE:
                CANDLES.setColor(CandleSide.LEFT, new Color8Bit(0, 255, 0));
                break;
            case SPEAKER:
                CANDLES.setColor(CandleSide.LEFT, new Color8Bit(0, 0, 255));
                break;
            default:
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
