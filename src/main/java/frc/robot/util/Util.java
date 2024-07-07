/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotContainer;
import frc.robot.util.interpolable.InterpolatingDouble;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

import java.util.Collections;
import java.util.List;

public class Util {
  public static final AprilTagFieldLayout field = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
  public static final Pose2d TAG_4_POSE = field.getTagPose(4).get().toPose2d(); // RED ALLIANCE SPEAKER CENTER
  public static final Pose2d TAG_7_POSE = field.getTagPose(7).get().toPose2d(); // BLUE ALLIANCE SPEAKER CENTER
  public static final Pose2d TAG_5_POSE = field.getTagPose(5).get().toPose2d(); // RED AMP
  public static final Pose2d TAG_6_POSE = field.getTagPose(6).get().toPose2d(); // BLUE AMP
  public static final Pose2d MANUAL_RED_AMP = new Pose2d(14.7, 7.42, new Rotation2d(Math.toRadians(90.0))); // RED AMP
  public static final Pose2d MANUAL_BLUE_AMP = new Pose2d(1.84, 7.42, new Rotation2d(Math.toRadians(90.0))); // BLUE AMP
  public static final Pose2d MANUAL_RED_LOB = new Pose2d(14.7, 5.95, new Rotation2d(Math.toRadians(90.0))); // RED AMP
  public static final Pose2d MANUAL_BLUE_LOB = new Pose2d(1.84, 5.95, new Rotation2d(Math.toRadians(90.0))); // BLUE AMP
  public static final List<Pose2d> TRAP_TAGS = Collections.unmodifiableList(List.of(
      field.getTagPose(11).get().toPose2d(), // RED SOURCE
      field.getTagPose(12).get().toPose2d(), // RED AMP
      field.getTagPose(13).get().toPose2d(), // RED MIDDLE
      field.getTagPose(14).get().toPose2d(), // BLUE MIDDLE
      field.getTagPose(15).get().toPose2d(), // BLUE AMP
      field.getTagPose(16).get().toPose2d() // BLUE SOURCE
  ));
  public static final List<Pose2d> AMP_TAGS = Collections.unmodifiableList(List.of(TAG_6_POSE, TAG_5_POSE));
  public static final double DEADBAND = 0.05;

  public static Pose2d getAllianceSpeaker() {
    // return alliance == DriverStation.Alliance.Blue ? TAG_7_POSE : TAG_4_POSE;
    return RobotContainer.DRIVETRAIN.getAlliance() == DriverStation.Alliance.Blue
        ? TAG_7_POSE
        : TAG_4_POSE;
  }

  public static Pose2d getAllianceAmp() {
    // return alliance == DriverStation.Alliance.Blue ? TAG_7_POSE : TAG_4_POSE;
    return RobotContainer.DRIVETRAIN.getAlliance() == DriverStation.Alliance.Blue
        ? MANUAL_BLUE_AMP
        : MANUAL_RED_AMP;
  }

  public static Pose2d getAllianceLob() {
    // return alliance == DriverStation.Alliance.Blue ? TAG_7_POSE : TAG_4_POSE;
    return RobotContainer.DRIVETRAIN.getAlliance() == DriverStation.Alliance.Blue
        ? MANUAL_BLUE_LOB
        : MANUAL_RED_LOB;
  }

  public static Rotation2d getRotationToAllianceLob(Pose2d opose) {
    Transform2d delta = new Transform2d(
        Util.getAllianceLob().getTranslation().minus(opose.getTranslation()), new Rotation2d());
    return new Rotation2d(Math.atan2(delta.getY(), delta.getX()))
        .plus(Rotation2d.fromDegrees(180.0));
  }

  public static double getShooterSpeedFromDistanceForLob(double distance) {
    return Constants.DISTANCE_TO_PASS_RPM.getInterpolated(new InterpolatingDouble(distance)).value;
  }

  public static boolean isWithinTolerance(
      double currentValue, double targetValue, double tolerance) {
    return Math.abs(currentValue - targetValue) <= tolerance;
  }

  public static boolean canSeeTarget(String limelight) {
    return LimelightHelpers.getBotPoseEstimate_wpiBlue(limelight).tagCount > 0;
  }

  public static void setupUtil() {
  }

  public static double getInterpolatedWristAngleSpeaker() {
    return Constants.DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.getInterpolated(
        new InterpolatingDouble(RobotContainer.getLocalizationState().getSpeakerDistance())).value;
  }

  public static boolean isValidShot() {
    double dist = RobotContainer.getLocalizationState().getSpeakerDistance();
    return (dist > 2.25 && dist < 5.25);
  }

  public static boolean isLobShot() {
    return RobotContainer.getLocalizationState().getAmpPassDistance() > 5.5;
  }

  public static double squareValue(double value) {
    return Math.copySign(Math.pow(value, 2), value);
  }

  public static Pose2d findNearestPose(Pose2d currentPose, Pose2d... otherPoses) {
    return currentPose.nearest(List.of(otherPoses));
  }

  // TODO: Find actual tag positions and ideal offsets
  public static Pose2d findNearestPoseToTrapClimbs(Pose2d currentPose) {
    return currentPose.nearest(TRAP_TAGS);
  }

  public static boolean isPointedAtSpeaker() {
    return Util.isWithinTolerance(
        RobotContainer.DRIVETRAIN.getPose().getRotation().getDegrees(),
        RobotContainer.getLocalizationState().getSpeakerAngle().getDegrees(),
        2.5);
  }

  public static boolean isPointedAtPass() {
    return Util.isWithinTolerance(
        RobotContainer.DRIVETRAIN.getPose().getRotation().getDegrees(),
        RobotContainer.getLocalizationState().getAmpPassAngle().getDegrees(),
        3.0);
  }
}
