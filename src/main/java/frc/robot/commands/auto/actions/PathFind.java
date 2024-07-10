// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.util.zoning.LocalizationUtil;

/** Add your docs here. */
public class PathFind {

  public static final Pose2d BLUE_AUTO_SOURCE_SHOT = new Pose2d(3.7, 3.15, Rotation2d.fromDegrees(-30.0));
  public static final Pose2d RED_AUTO_SOURCE_SHOT = new Pose2d(
    LocalizationUtil.blueFlipToRed(BLUE_AUTO_SOURCE_SHOT.getTranslation()),
    Rotation2d.fromDegrees(-150)
  );

  public static final Pose2d BLUE_AUTO_AMP_SHOT = new Pose2d(4.1, 6.2, Rotation2d.fromDegrees(10.0));
  public static final Pose2d RED_AUTO_AMP_SHOT = new Pose2d(
    LocalizationUtil.blueFlipToRed(BLUE_AUTO_AMP_SHOT.getTranslation()),
    Rotation2d.fromDegrees(170)
  );

  public static final Pose2d BLUE_MADTOWN_SHOT = new Pose2d(4.35, 5.15, Rotation2d.fromDegrees(-88));
  public static final Pose2d RED_MADTOWN_SHOT = new Pose2d(
    LocalizationUtil.blueFlipToRed(BLUE_MADTOWN_SHOT.getTranslation()),
    Rotation2d.fromDegrees(-178.0)
  );

  public static final Pose2d BLUE_CLEAN_UP = new Pose2d(3.3, 5.5, Rotation2d.fromDegrees(180));
  public static final Pose2d RED_CLEAN_UP = new Pose2d(LocalizationUtil.blueFlipToRed(BLUE_CLEAN_UP).getTranslation(), Rotation2d.fromDegrees(0));

  public static final Pose2d BLUE_CENTER_SCORE = new Pose2d(2.6, 5.55, Rotation2d.fromDegrees(0));
  public static final Pose2d RED_CENTER_SCORE = new Pose2d(LocalizationUtil.blueFlipToRed(BLUE_CENTER_SCORE).getTranslation(), Rotation2d.fromDegrees(180));

  private static Command pathfindToPose(Pose2d pose) {
    return AutoBuilder.pathfindToPose(
      pose,
      new PathConstraints(5.0, 4.0, Units.degreesToRadians(540.0), Units.degreesToRadians(360.0)),
      0.0,
      0.0
    );
  }

  public static Command toSourceShot() {
    return new InstantCommand(() -> RobotContainer.setAutoState(AutoState.SHOOT_PREP)).andThen(
      new ConditionalCommand(
        pathfindToPose(BLUE_AUTO_SOURCE_SHOT),
        pathfindToPose(RED_AUTO_SOURCE_SHOT),
        () -> RobotContainer.DRIVETRAIN.getAlliance().equals(Alliance.Blue)
      )
    );
  }

  public static Command toAmpShot() {
    return new InstantCommand(() -> RobotContainer.setAutoState(AutoState.SHOOT_PREP)).andThen(
      new ConditionalCommand(
        pathfindToPose(BLUE_AUTO_AMP_SHOT),
        pathfindToPose(RED_AUTO_AMP_SHOT),
        () -> RobotContainer.DRIVETRAIN.getAlliance().equals(Alliance.Blue)
      )
    );
  }

  public static Command toMadtownshot() {
    return new InstantCommand(() -> RobotContainer.setAutoState(AutoState.SHOOT_PREP)).andThen(
      new ConditionalCommand(
        pathfindToPose(BLUE_MADTOWN_SHOT),
        pathfindToPose(RED_MADTOWN_SHOT),
        () -> RobotContainer.DRIVETRAIN.getAlliance().equals(Alliance.Blue)
      )
    );
  }

  public static Command toCleanUp() {
    return new ConditionalCommand(
      pathfindToPose(BLUE_CLEAN_UP),
      pathfindToPose(RED_CLEAN_UP),
      () -> RobotContainer.DRIVETRAIN.getAlliance().equals(Alliance.Blue)
    );
  }

  public static Command toCenterScore() {
    return new ConditionalCommand(
      pathfindToPose(BLUE_CENTER_SCORE),
      pathfindToPose(RED_CENTER_SCORE), 
      () -> RobotContainer.DRIVETRAIN.getAlliance().equals(Alliance.Blue)
    );
  }
}
