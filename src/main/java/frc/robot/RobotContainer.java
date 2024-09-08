// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.commands.teleop.defaults.DefaultCandles;
import frc.robot.commands.teleop.defaults.DefaultElevator;
import frc.robot.commands.teleop.defaults.DefaultIntake;
import frc.robot.commands.teleop.defaults.DefaultShooter;
import frc.robot.commands.teleop.defaults.DefaultWrist;
import frc.robot.commands.teleop.logic.DriveMode;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.dashboard.Dashboard;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AmpSensors;
import frc.robot.subsystems.Candles;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PoopMonitor;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Semaphore;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Wrist;
import frc.robot.util.extensions.Controls;
import frc.robot.util.zoning.FieldZones;
import frc.robot.util.zoning.LocalizationState;
import frc.robot.util.zoning.LocalizationUtil;
import frc.robot.util.zoning.PointsOfInterest;

public class RobotContainer {
  public static final double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  public static final double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  public static final CommandXboxController DRIVER_CONTROLLER = new CommandXboxController(0); // My joystick
  public static final CommandXboxController CODRIVER_CONTROLLER = new CommandXboxController(1); // My joystick

  public static final CommandSwerveDrivetrain DRIVETRAIN = TunerConstants.DriveTrain; // My drivetrain

  public static RobotState STATE = RobotState.DEFAULT;
  public static ScoreMode SCORE_MODE = ScoreMode.SPEAKER;
  public static DriveMode DRIVE_MODE = DriveMode.MANUAL;

  private static RobotState PREV_STATE = RobotState.DEFAULT;
  private static ScoreMode PREV_SCORE_MODE = ScoreMode.SPEAKER;
  private static DriveMode PREV_DRIVE_MODE = DriveMode.MANUAL;

  public static final Shooter SHOOTER = new Shooter(Constants.IDs.SHOOTER_LEADER_ID, Constants.IDs.SHOOTER_FOLLOWER_ID,
      Constants.IDs.SHOOTER_FEEDER_ID, Constants.IDs.CANBUS_DETACHED);
  public static final Elevator ELEVATOR = new Elevator(Constants.IDs.ELEVATOR_LEADER_ID, Constants.IDs.ELEVATOR_FOLLOWER_ID,
      Constants.IDs.CANBUS_ATTACHED);
  public static final Intake INTAKE = new Intake(Constants.IDs.INTAKE_BOTTOM_ID, Constants.IDs.INTAKE_TOP_ID,
      Constants.IDs.CANBUS_ATTACHED);
  public static final Wrist WRIST = new Wrist(Constants.IDs.WRIST_ID, Constants.IDs.CANBUS_DETACHED);
  public static final AmpSensors AMP_SENSORS = new AmpSensors(Constants.IDs.PROXIMITY_SENSOR_LEFT_ID,
      Constants.IDs.PROXIMITY_SENSOR_RIGHT_ID);
  public static final Candles CANDLES = new Candles(Constants.IDs.LEFT_CANDLE, Constants.IDs.RIGHT_CANDLE,
      Constants.IDs.CANBUS_ATTACHED);
  public static final Vision VISION = new Vision(Constants.Photon.INTAKE_PHOTON_CAMERA_NAME,
      Constants.Photon.INTAKE_CAMERA_HEIGHT_METERS, Constants.Photon.INTAKE_CAMERA_ANGLE_DEGREES);
  public static final PoopMonitor POOP_MONITOR = new PoopMonitor();
  public static final Semaphore SEMAPHORE = new Semaphore();

  public static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  public static final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  public static final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  public static final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  /* Path follower */
  // public Command runAuto = DRIVETRAIN.getAutoPath("Tests");

  public final Telemetry logger = new Telemetry(MaxSpeed);

  public RobotContainer() {

    Controls.configureDriverController();
    Controls.configureCoDriverController();
  }

  public void teleopInit() {
    configureDefaultCommands();
    System.out.println("teleopInit");
  }

  public void configureDefaultCommands() {
    CANDLES.setDefaultCommand(new DefaultCandles());
    SHOOTER.setDefaultCommand(new DefaultShooter());
    INTAKE.setDefaultCommand(new DefaultIntake());
    ELEVATOR.setDefaultCommand(new DefaultElevator());
    WRIST.setDefaultCommand(new DefaultWrist());
    CANDLES.setDefaultCommand(new DefaultCandles());
    if (DRIVETRAIN.getAlliance() == Alliance.Blue) {
      DRIVETRAIN.seedFieldRelative(
          new Pose2d(1.37, 5.52, Rotation2d.fromDegrees(0.0)));
    } else {
      DRIVETRAIN.seedFieldRelative(
          new Pose2d(15.2, 5.5, Rotation2d.fromDegrees(-180)));
    }
  }

  public Command getAutonomousCommand() {
    return DASHBOARD.MATCH_TAB.getSelectedAuto();
  }

  /***** Begin Team Logic *****/

  private static AutoState autoState = AutoState.DEFAULT;

  public static AutoState getAutoState() {
    return autoState;
  }

  public static void setAutoState(final AutoState newState) {
    autoState = newState;
  }

  private static LocalizationState localizationState = new LocalizationState(FieldZones.Zone.ALLIANCE_WING,
      new Rotation2d(), 0.0, new Rotation2d(), 0.0, new Rotation2d(), 0.0);

  public static LocalizationState getLocalizationState() {
    return localizationState;
  }

  private static void updateLocalizationState() {
    final Alliance alliance = DRIVETRAIN.getAlliance();
    final PointsOfInterest poi = PointsOfInterest.get(alliance);
    final Translation2d robot = DRIVETRAIN.getState().Pose.getTranslation();
    localizationState = new LocalizationState(
        FieldZones.getZoneFromTranslation(alliance, robot),
        LocalizationUtil.getRotationTowards(robot, poi.AMP_PASS_TARGET),
        robot.getDistance(poi.AMP_PASS_TARGET),
        LocalizationUtil.getRotationTowards(robot, poi.CENTER_PASS_TARGET),
        robot.getDistance(poi.CENTER_PASS_TARGET),
        LocalizationUtil.getRotationTowards(robot, poi.SPEAKER),
        robot.getDistance(poi.SPEAKER));
  }

  public void robotInit() {
    DRIVETRAIN.getDaqThread().setThreadPriority(99);
    PathfindingCommand.warmupCommand().schedule();
  }

  public void robotPeriodic() {
    DRIVETRAIN.updatePoseFromVision();
    updateLocalizationState();
  }

  public String getMode() {
    if (DRIVE_MODE != PREV_DRIVE_MODE || SCORE_MODE != PREV_SCORE_MODE || STATE != PREV_STATE) {
      PREV_STATE = STATE;
      PREV_DRIVE_MODE = DRIVE_MODE;
      PREV_SCORE_MODE = SCORE_MODE;
      return "ST: " + STATE + " SM: " + SCORE_MODE + " DM" + DRIVE_MODE;
    }
    return null;
  }

  public static RobotState getRobotState() {
    return STATE;
  }

  public static RobotState setRobotState(RobotState STATE) {
    return RobotContainer.STATE = STATE;
  }

  public static ScoreMode setScoreMode(ScoreMode MODE) {
    return RobotContainer.SCORE_MODE = MODE;
  }

  public static ScoreMode getScoreMode() {
    return SCORE_MODE;
  }

  public static DriveMode setDriveMode(DriveMode MODE) {
    return RobotContainer.DRIVE_MODE = MODE;
  }

  public static DriveMode getDriveMode() {
    return DRIVE_MODE;
  }

  public static final Dashboard DASHBOARD = new Dashboard();

}
