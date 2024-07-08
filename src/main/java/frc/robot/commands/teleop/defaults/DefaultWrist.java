package frc.robot.commands.teleop.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.util.Util;

import static frc.robot.RobotContainer.*;

public class DefaultWrist extends Command {

  private boolean doTheJiggle;

  public DefaultWrist() {
    addRequirements(RobotContainer.WRIST);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    switch (RobotContainer.STATE) {
      case COLLECTING:
      case COLLECTING_SLOW:
        WRIST.setDegrees(15.0);
        break;
      case RECOVERY:
        break;
      case SHOOTING:
        WRIST.setDegrees(Util.getInterpolatedWristAngleSpeaker());
        break;
      case AMP_PREP:
      case AMP_SCORE:
        WRIST.setDegrees(Constants.Wrist.AMP_WRIST_DEGREES);
        break;
      case PASS:
        WRIST.setDegrees(Constants.Wrist.PASS_WRIST_DEGREES);
        break;
      case POOPING_PREP:
      case POOPING:
        WRIST.setDegrees(Constants.Wrist.POOP_DEG);
        break;
      case AMP_EXIT:
        WRIST.goHome();
        break;
      case FAST_SUB_PREP:
      case FAST_SUB:
      case SUBWOOFER_PREP:
      case SUBWOOFER:
        WRIST.setDegrees(Constants.Wrist.SUBWOOFER_SHOT_DEG);
        break;
      case PODIUM_PREP:
      case PODIUM:
        WRIST.setDegrees(Constants.Wrist.PODIUM_SHOT_DEG);
        break;
      case CLIMB_INIT:
      case CLIMB_PULLDOWN:
      case CLIMB_LEVEL:
      case TRAP_ELEV_MIDWAY:
        WRIST.goHome();
        break;
      case TRAP_WRIST_MIDWAY:
      case TRAP_ELEV_FULL:
        WRIST.setDegrees(Constants.Wrist.TRAP_WRIST_DEGREES_MIDWAY);
        break;
      case TRAP_WRIST_FULL:
      case TRAP_SCORE:
        WRIST.setDegrees(Constants.Wrist.TRAP_WRIST_DEGREES);
        break;
      case TRAP_DOTHEJIGGLE:
        doTheJiggle = Util.isWithinTolerance(WRIST.getDegrees(), Constants.Wrist.TRAP_WRIST_DEGREES, 3.0);
        if (doTheJiggle) {
          WRIST.setDegrees(Constants.Wrist.TRAP_WRIST_DEGREES - 7.0, 1);
        } else {
          WRIST.setDegrees(Constants.Wrist.TRAP_WRIST_DEGREES, 1);
        }
        break;
      default:
        switch (getScoreMode()) {
          case DEFENSE:
            WRIST.goHome();
            break;
          case AMP:
          case SPEAKER:
          default:
            switch (RobotContainer.getLocalizationState().fieldZone()) {
              case ALLIANCE_HOME:
              case ALLIANCE_WING:
              case ALLIANCE_STAGE:
              case ALLIANCE_YIELD:
                if (SCORE_MODE == ScoreMode.SPEAKER) {
                  if (SHOOTER.isCenterBroken()) {
                    if (!Util.isValidShot()) {
                      WRIST.setDegrees(35.0);
                    } else {
                      WRIST.setDegrees(Util.getInterpolatedWristAngleSpeaker());
                    }
                  } else {
                    WRIST.setDegrees(12.0);
                  }
                }
                break;
              case OPPONENT_YIELD:
              case OPPONENT_HOME:
              case OPPONENT_WING:
              case NEUTRAL_WING:
                if (SHOOTER.isCenterBroken()) {
                  WRIST.setDegrees(Constants.Wrist.PASS_WRIST_DEGREES);
                } else {
                  WRIST.goHome();
                }
                break;
              case OPPONENT_STAGE:
              default:
                WRIST.goHome();
                break;

            }
            break;

        }

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
