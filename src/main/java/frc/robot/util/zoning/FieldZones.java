// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util.zoning;

import static frc.robot.RobotContainer.DRIVETRAIN;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/** Adds your docs here. */
public class FieldZones {
  public enum Zone {
    ALLIANCE_WING, NEUTRAL_WING, OPPONENT_WING, ALLIANCE_HOME, OPPONENT_HOME, ALLIANCE_STAGE, OPPONENT_STAGE, ALLIANCE_YIELD, OPPONENT_YIELD
  }

  public static final double X_MIN = 0.0;
  public static final double Y_MIN = 0.0;
  public static final double BLUE_WING_LINE_X = 5.85 + 0.30;
  public static final double RED_WING_LINE_X = 10.72 - 0.30;
  public static final double BLUE_HOME_LINE_X = 1.9 + 1.5;
  public static final double RED_HOME_LINE_X = 14.65 - 1.5;

  public static final RectanglePoseArea AMP_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, 6.0),
      new Translation2d(4.7, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea AMP_RED = new RectanglePoseArea(
      new Translation2d(LocalizationUtil.blueFlipToRed(AMP_BLUE.getMaxX()), AMP_BLUE.getMinY()),
      new Translation2d(LocalizationUtil.FIELD_LENGTH, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea SPEAKER_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, 2.75),
      new Translation2d(4.5, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea SPEAKER_RED = new RectanglePoseArea(
      new Translation2d(LocalizationUtil.blueFlipToRed(SPEAKER_BLUE.getMaxX()), SPEAKER_BLUE.getMinY()),
      new Translation2d(LocalizationUtil.FIELD_LENGTH, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea SUBWOOFER_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, 3.85),
      new Translation2d(1.75, 7.05));
  public static final RectanglePoseArea SUBWOOFER_RED = new RectanglePoseArea(
      new Translation2d(LocalizationUtil.blueFlipToRed(SUBWOOFER_BLUE.getMaxX()), SUBWOOFER_BLUE.getMinY()),
      new Translation2d(LocalizationUtil.FIELD_LENGTH, SUBWOOFER_BLUE.getMaxY()));
  public static final RectanglePoseArea WING_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, Y_MIN),
      new Translation2d(BLUE_WING_LINE_X, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea WING_NEUTRAL = new RectanglePoseArea(new Translation2d(6.0, 0.0),
      new Translation2d(10.75, 8.0));
  public static final RectanglePoseArea WING_RED = new RectanglePoseArea(new Translation2d(RED_WING_LINE_X, Y_MIN),
      new Translation2d(LocalizationUtil.FIELD_LENGTH, LocalizationUtil.FIELD_WIDTH));

  public static final RectanglePoseArea STAGE_BLUE = new RectanglePoseArea(
      new Translation2d(2.9, 2.7),
      new Translation2d(6.3, 6.15));

  public static final RectanglePoseArea STAGE_RED = new RectanglePoseArea(
      new Translation2d(10.5, 2.3),
      new Translation2d(13.5, 5.7));

  public static final RectanglePoseArea YIELD_BLUE = new RectanglePoseArea(
      new Translation2d(BLUE_WING_LINE_X, 2.7),
      new Translation2d(BLUE_WING_LINE_X + 1.5, 5.6));

  public static final RectanglePoseArea YIELD_RED = new RectanglePoseArea(
      new Translation2d(RED_WING_LINE_X - 1.5, 2.7),
      new Translation2d(RED_WING_LINE_X, 5.6));

  public static Zone getZoneFromTranslation(final Alliance alliance, final Translation2d translation) {
    final double x = translation.getX();
    final Pose2d pose = DRIVETRAIN.getPose();
    if (alliance == Alliance.Red) {
      if (x > RED_WING_LINE_X) {
        if (STAGE_RED.isPoseWithinArea(pose))
          return Zone.ALLIANCE_STAGE;
        if (x > RED_HOME_LINE_X)
          return Zone.ALLIANCE_HOME;
        return Zone.ALLIANCE_WING;
      }
      if (x < RED_WING_LINE_X && x > BLUE_WING_LINE_X) {
        if (YIELD_RED.isPoseWithinArea(pose))
          return Zone.ALLIANCE_YIELD;
        if (YIELD_BLUE.isPoseWithinArea(pose))
          return Zone.OPPONENT_YIELD;
        return Zone.NEUTRAL_WING;
      }
      if (x < BLUE_WING_LINE_X) {
        if (STAGE_BLUE.isPoseWithinArea(pose))
          return Zone.OPPONENT_STAGE;
        if (x < BLUE_HOME_LINE_X)
          return Zone.OPPONENT_HOME;
        return Zone.OPPONENT_WING;
      }
    } else {
      if (x < BLUE_WING_LINE_X) {
        if (STAGE_BLUE.isPoseWithinArea(pose))
          return Zone.ALLIANCE_STAGE;
        if (x < BLUE_HOME_LINE_X)
          return Zone.ALLIANCE_HOME;
        return Zone.ALLIANCE_WING;
      }
      if (x < RED_WING_LINE_X && x > BLUE_WING_LINE_X) {
        if (YIELD_BLUE.isPoseWithinArea(pose))
          return Zone.ALLIANCE_YIELD;
        if (YIELD_RED.isPoseWithinArea(pose))
          return Zone.OPPONENT_YIELD;
        return Zone.NEUTRAL_WING;
      }
      if (x > RED_WING_LINE_X) {
        if (STAGE_RED.isPoseWithinArea(pose))
          return Zone.OPPONENT_STAGE;
        if (x > RED_HOME_LINE_X)
          return Zone.OPPONENT_HOME;
        return Zone.OPPONENT_WING;
      }
    }
    return Zone.ALLIANCE_WING;
  }
}
