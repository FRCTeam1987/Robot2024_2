// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.util.zoning.FieldZones;
import frc.robot.util.Util;

import static frc.robot.Constants.Wrist.*;
import static frc.robot.RobotContainer.WRIST;

public class DefaultAutoWrist extends Command {
  /** Creates a new DefaultAutoWrist. */
  public DefaultAutoWrist() {
    addRequirements(WRIST);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (RobotContainer.getAutoState()) {
      case INSTANT_SUB:
      case INSTANT_SUB_PREP:
        WRIST.setDegrees(Constants.Wrist.SUBWOOFER_SHOT_DEG);
        break;
      case COLLECTING:
        if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING
            || RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_HOME) {
          WRIST.setDegrees(MathUtil.clamp(Util.getInterpolatedWristAngleSpeaker(), WRIST_MIN_DEG, MAX_SHOOT_DEG));
        } else {
          WRIST.setDegrees(COLLECT_DEG);
        }
        break;
      case POOP_PREP:
      case POOPING:
        WRIST.setDegrees(POOP_DEG);
        break;
      default:
        WRIST.setDegrees(MathUtil.clamp(Util.getInterpolatedWristAngleSpeaker(), WRIST_MIN_DEG, MAX_SHOOT_DEG));
    }
  }
}
