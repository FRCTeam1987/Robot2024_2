// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.util.Util;

public class DefaultAutoWrist extends Command {
  /** Creates a new DefaultAutoWrist. */
  public DefaultAutoWrist() {
    addRequirements(RobotContainer.WRIST);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (RobotContainer.getAutoState()) {
      case COLLECTING:
        double poseX = RobotContainer.DRIVETRAIN.getState().Pose.getX();
        if (poseX < 6.0 || poseX > 16.56 - 6.0) {
          RobotContainer.WRIST.setDegrees(MathUtil.clamp(Util.getInterpolatedWristAngleSpeaker(), 10.0, 35.0));
        } else {
          RobotContainer.WRIST.setDegrees(26);
        }
        break;
      case POOPING:
        RobotContainer.WRIST.setDegrees(22);
        break;
      case SHOOT_PREP:
      case SHOOTING:
      case DEFAULT:
      default:
        RobotContainer.WRIST.setDegrees(MathUtil.clamp(Util.getInterpolatedWristAngleSpeaker(), 10.0, 35.0));
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
