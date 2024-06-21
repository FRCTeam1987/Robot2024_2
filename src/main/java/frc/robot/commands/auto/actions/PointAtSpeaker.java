// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest.FieldCentric;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.util.Util;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class PointAtSpeaker extends Command {
  private final SwerveRequest.FieldCentric drive =
      new SwerveRequest.FieldCentric()
          .withDeadband(RobotContainer.MaxSpeed * 0.1)
          .withRotationalDeadband(RobotContainer.MaxAngularRate * 0.05) // Add a 5% deadband
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
  private DoubleSupplier velocityXSupplier = () -> 0.0; // getAllianceLob
  private DoubleSupplier velocityYSupplier = () -> 0.0;
  private double desiredRotation;
  private final PIDController THETA_CONTROLLER;
  private BooleanSupplier shouldLob = () -> false;
  private BooleanSupplier shouldStop = () -> false;
  double rotationRate = 0;

  public PointAtSpeaker(
      DoubleSupplier velocityXSupplier,
      DoubleSupplier velocityYSupplier) {
    this(
        velocityXSupplier,
        velocityYSupplier,
        () -> false,
        () -> false);
  }

  public PointAtSpeaker(
      DoubleSupplier velocityXSupplier,
      DoubleSupplier velocityYSupplier,
      BooleanSupplier shouldLob,
      BooleanSupplier shouldStop) {
    this.velocityXSupplier = velocityXSupplier;
    this.velocityYSupplier = velocityYSupplier;
    this.shouldLob = shouldLob;
    this.shouldStop = shouldStop;
    THETA_CONTROLLER = new PIDController(0.13, 0.01, 0.0); // (0.183, 0.1, 0.0013)
    THETA_CONTROLLER.enableContinuousInput(-180.0, 180.0);
    THETA_CONTROLLER.setTolerance(0.01, 0.01);
    addRequirements(RobotContainer.DRIVETRAIN);
  }

  @Override
  public void initialize() {}

  public double getRotationRate() {
    return rotationRate;
  }

  @Override
  public void execute() {
    Pose2d pose = RobotContainer.DRIVETRAIN.getPose();
    desiredRotation =
        shouldLob.getAsBoolean()
            ? RobotContainer.getLocalizationState().getPassAngle().getDegrees()
            : RobotContainer.getLocalizationState().getSpeakerAngle().getDegrees();
    double rotationRate =
        THETA_CONTROLLER.calculate(pose.getRotation().getDegrees(), desiredRotation);
    RobotContainer.DRIVETRAIN.setControl(
        drive
            .withVelocityX(
                Util.squareValue(velocityXSupplier.getAsDouble())
                    * RobotContainer.MaxSpeed) // Drive forward with
            // negative Y (forward)
            .withVelocityY(
                Util.squareValue(velocityYSupplier.getAsDouble())
                    * RobotContainer.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(rotationRate) // Drive counterclockwise with negative X (left)
        );
  }

  @Override
  public boolean isFinished() {
    return shouldStop.getAsBoolean();
  }

  @Override
  public void end(boolean interrupted) {
    // System.out.println("Command Finished!");
    FieldCentric driveRequest =
        drive
            .withVelocityX(0.0) // Drive forward with
            // negative Y (forward)
            .withVelocityY(0.0) // Drive left with negative X (left)
            .withRotationalRate(0.0); // Drive counterclockwise with negative X (left)

    RobotContainer.DRIVETRAIN.setControl(driveRequest);
    if (interrupted) {}
  }
}
