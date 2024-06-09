// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/** Adds your docs here. */
public class FieldZones {
  public enum Zone {
    AMP,
    NEUTRAL_WING,
    OPPONENT_WING,
    SPEAKER
  }

  private static final double X_MIN = 0.0;
  private static final double Y_MIN = 0.0;
  private static final double BLUE_WING_LINE_X = 5.85;
  private static final double RED_WING_LINE_X = 10.72;

  public static final RectanglePoseArea AMP_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, 6.0 ), new Translation2d(4.7, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea AMP_RED = new RectanglePoseArea(new Translation2d(LocalizationUtil.blueFlipToRed(AMP_BLUE.getMaxX()), AMP_BLUE.getMinY()), new Translation2d(LocalizationUtil.FIELD_LENGTH, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea SPEAKER_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, 2.75), new Translation2d(4.5, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea SPEAKER_RED = new RectanglePoseArea(new Translation2d(LocalizationUtil.blueFlipToRed(SPEAKER_BLUE.getMaxX()), SPEAKER_BLUE.getMinY()), new Translation2d(LocalizationUtil.FIELD_LENGTH, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea SUBWOOFER_BLUE = new RectanglePoseArea(new Translation2d(X_MIN, 3.85), new Translation2d(1.75, 7.05));
  public static final RectanglePoseArea SUBWOOFER_RED = new RectanglePoseArea(new Translation2d(LocalizationUtil.blueFlipToRed(SUBWOOFER_BLUE.getMaxX()), SUBWOOFER_BLUE.getMinY()), new Translation2d(LocalizationUtil.FIELD_LENGTH, SUBWOOFER_BLUE.getMaxY()));
  public static final RectanglePoseArea WING_BLUE = new RectanglePoseArea(new Translation2d(X_MIN,Y_MIN),new Translation2d(BLUE_WING_LINE_X, LocalizationUtil.FIELD_WIDTH));
  public static final RectanglePoseArea WING_NEUTRAL = new RectanglePoseArea(new Translation2d(6.0, 0.0), new Translation2d(10.75, 8.0));
  public static final RectanglePoseArea WING_RED = new RectanglePoseArea(new Translation2d(RED_WING_LINE_X,Y_MIN),new Translation2d(LocalizationUtil.FIELD_LENGTH, LocalizationUtil.FIELD_WIDTH));

  public static Zone getZoneFromPose(final Alliance alliance, final Translation2d translation) {
    if (alliance == Alliance.Red) {
      if (AMP_RED.isTranslationWithinArea(translation)) {
        return Zone.AMP;
      }
      if (SPEAKER_RED.isTranslationWithinArea(translation) || WING_RED.isTranslationWithinArea(translation)) {
        return Zone.SPEAKER;
      }
      if (WING_NEUTRAL.isTranslationWithinArea(translation)) {
        return Zone.NEUTRAL_WING;
      }
      if (WING_BLUE.isTranslationWithinArea(translation)) {
        return Zone.OPPONENT_WING;
      }
    } else {
      if (AMP_BLUE.isTranslationWithinArea(translation)) {
        return Zone.AMP;
      }
      if (SPEAKER_BLUE.isTranslationWithinArea(translation) || WING_BLUE.isTranslationWithinArea(translation)) {
        return Zone.SPEAKER;
      }
      if (WING_NEUTRAL.isTranslationWithinArea(translation)) {
        return Zone.NEUTRAL_WING;
      }
      if (WING_RED.isTranslationWithinArea(translation)) {
        return Zone.OPPONENT_WING;
      }
    }
    return Zone.SPEAKER;
  }
}
