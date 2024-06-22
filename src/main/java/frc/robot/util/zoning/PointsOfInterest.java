// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util.zoning;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/** Add your docs here. */
public class PointsOfInterest {
  private static final AprilTagFieldLayout field = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();

  private static final Translation2d AMP_ROBOT_OFFSET = new Translation2d(0, 0.76);

  public static PointsOfInterest BLUE = new PointsOfInterest(
      new Pose2d(field.getTagPose(6).get().getTranslation().toTranslation2d().minus(new Translation2d(0, 0.76)),
          Rotation2d.fromDegrees(90)),
      new Translation2d(1.0, 7.05),
      field.getTagPose(7).get().getTranslation().toTranslation2d());

  public static PointsOfInterest RED = new PointsOfInterest(
      new Pose2d(field.getTagPose(5).get().getTranslation().toTranslation2d().minus(AMP_ROBOT_OFFSET),
          BLUE.AMP_SCORE.getRotation()),
      LocalizationUtil.blueFlipToRed(BLUE.PASS_TARGET),
      LocalizationUtil.blueFlipToRed(BLUE.SPEAKER));

  public static PointsOfInterest get(final Alliance alliance) {
    return alliance == Alliance.Red ? RED : BLUE;
  }

  public final Pose2d AMP_SCORE;
  public final Translation2d PASS_TARGET;
  public final Translation2d SPEAKER;

  public PointsOfInterest(final Pose2d ampScore, final Translation2d passTarget, final Translation2d speaker) {
    AMP_SCORE = ampScore;
    PASS_TARGET = passTarget;
    SPEAKER = speaker;
  }
}
