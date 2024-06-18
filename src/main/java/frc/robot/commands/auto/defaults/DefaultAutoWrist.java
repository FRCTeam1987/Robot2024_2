// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.util.FieldZones;
import frc.robot.util.Util;

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
      case COLLECTING:
        if (RobotContainer.getLocalizationState().getFieldZone() == FieldZones.Zone.ALLIANCE_WING) {
          WRIST.setDegrees(MathUtil.clamp(Util.getInterpolatedWristAngleSpeaker(), Constants.Wrist.WRIST_MIN_DEG, Constants.Wrist.MAX_SHOOT_DEG));
        } else {
          WRIST.setDegrees(Constants.Wrist.COLLECT_DEG);
        }
        break;
      case POOP_PREP:
      case POOPING:
        WRIST.setDegrees(Constants.Wrist.POOP_DEG);
        break;
      default:
        WRIST.setDegrees(MathUtil.clamp(Util.getInterpolatedWristAngleSpeaker(), Constants.Wrist.WRIST_MIN_DEG, Constants.Wrist.MAX_SHOOT_DEG));
    }
  }
}
