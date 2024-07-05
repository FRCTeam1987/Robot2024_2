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

  private static final Translation2d AMP_ROBOT_OFFSET = new Translation2d(0, 1.25);

  public static final PointsOfInterest BLUE = new PointsOfInterest(
      new Pose2d(field.getTagPose(6).get().getTranslation().toTranslation2d().minus(AMP_ROBOT_OFFSET),
          Rotation2d.fromDegrees(90)),
      new Translation2d(1.0, 7.05),
      new Translation2d(7.0, 6.75),
      field.getTagPose(7).get().getTranslation().toTranslation2d());

  public static final PointsOfInterest RED = new PointsOfInterest(
      new Pose2d(field.getTagPose(5).get().getTranslation().toTranslation2d().minus(AMP_ROBOT_OFFSET),
          BLUE.AMP_SCORE.getRotation()),
      LocalizationUtil.blueFlipToRed(BLUE.AMP_PASS_TARGET),
      LocalizationUtil.blueFlipToRed(BLUE.CENTER_PASS_TARGET),
      field.getTagPose(4).get().getTranslation().toTranslation2d());

  public static PointsOfInterest get(final Alliance alliance) {
    return alliance == Alliance.Red ? RED : BLUE;
  }

  public final Pose2d AMP_SCORE;
  public final Translation2d AMP_PASS_TARGET;
  public final Translation2d CENTER_PASS_TARGET;
  public final Translation2d SPEAKER;

  public PointsOfInterest(final Pose2d ampScore, final Translation2d ampPassTarget,
      final Translation2d centerPassTarget, final Translation2d speaker) {
    AMP_SCORE = ampScore;
    AMP_PASS_TARGET = ampPassTarget;
    CENTER_PASS_TARGET = centerPassTarget;
    SPEAKER = speaker;
  }
}
