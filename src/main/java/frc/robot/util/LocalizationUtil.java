// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;

/** Add your docs here. */
public class LocalizationUtil {

  public static final double FIELD_LENGTH = 16.56;
  public static final double FIELD_WIDTH = 8.18;

  public static Translation2d blueFlipToRed(final Translation2d blue) {
    return new Translation2d(
      blueFlipToRed(blue.getX()),
      blue.getY()
    );
  }

  public static Pose2d blueFlipToRed(final Pose2d blue) {
    return new Pose2d(
      blueFlipToRed(blue.getTranslation()),
      blue.getRotation()
    );
  }

  public static double blueFlipToRed(final double blueX) {
    return FIELD_LENGTH - blueX;
  }

  /**
   * Calculate the rotation needed for the robot to poin towards the point of interest.
   * @param robot
   * @param poi
   * @return
   */
  public static Rotation2d getRotationTowards(final Translation2d robot, final Translation2d poi) {
    Transform2d delta = new Transform2d(poi.minus(robot), new Rotation2d());
    return new Rotation2d(Math.atan2(delta.getY(), delta.getX()))
        .plus(Rotation2d.fromDegrees(180.0)); // Shooter shoots out the back.
  }
}
