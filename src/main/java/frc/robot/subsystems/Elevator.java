// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

  private final TalonFX ELEVATOR_LEADER;
  private final TalonFX ELEVATOR_FOLLOWER;

  public Elevator(
      final int ELEVATOR_LEADER_ID,
      final int ELEVATOR_FOLLOWER_ID,
      String CANBUS) {

    ELEVATOR_LEADER = new TalonFX(ELEVATOR_LEADER_ID, CANBUS);
    ELEVATOR_FOLLOWER = new TalonFX(ELEVATOR_FOLLOWER_ID, CANBUS);

    TalonFXConfiguration extensionConfig = new TalonFXConfiguration();
    extensionConfig.Slot0.kP = Constants.Elevator.EXTENSION_KP;
    extensionConfig.Slot0.kI = Constants.Elevator.EXTENSION_KI;
    extensionConfig.Slot0.kD = Constants.Elevator.EXTENSION_KD;
    extensionConfig.Slot0.kV = Constants.Elevator.EXTENSION_KV;
    extensionConfig.Slot1.kP = Constants.Elevator.EXTENSION_KP_1;
    extensionConfig.Slot1.kI = Constants.Elevator.EXTENSION_KI_1;
    extensionConfig.Slot1.kD = Constants.Elevator.EXTENSION_KD_1;
    extensionConfig.Slot1.kV = Constants.Elevator.EXTENSION_KV_1;
    extensionConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    extensionConfig.CurrentLimits.StatorCurrentLimit = Constants.Elevator.EXTENSION_CURRENT_LIMIT;
    extensionConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    extensionConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    extensionConfig.Feedback.RotorToSensorRatio = -1;

    extensionConfig.MotionMagic.MotionMagicAcceleration = Constants.Elevator.EXTENSION_MOTION_ACCELERATION;
    extensionConfig.MotionMagic.MotionMagicCruiseVelocity = Constants.Elevator.EXTENSION_CRUISE_VELOCITY;

    ELEVATOR_LEADER.getConfigurator().apply(extensionConfig);
    ELEVATOR_FOLLOWER.getConfigurator().apply(extensionConfig);

    setZero();

    ELEVATOR_FOLLOWER.setControl(new Follower(ELEVATOR_LEADER.getDeviceID(), false));

  }

  public void setVoltage(double volts) {
    ELEVATOR_LEADER.setVoltage(volts);
  }

  public double getVelocity() {
    return ELEVATOR_LEADER.getVelocity().getValueAsDouble();
  }

  public void stop() {
    ELEVATOR_LEADER.setVoltage(0);
  }

  public void setLengthInches(double LENGTH, int slot) {
    if (LENGTH > Constants.Elevator.MAXIMUM_EXTENSION_LENGTH_INCHES
        || LENGTH < Constants.Elevator.MINIMUM_EXTENSION_LENGTH_INCHES) {
      DriverStation.reportError("Elevator out of bounds", false);
    } else {
      MotionMagicVoltage ctrl = new MotionMagicVoltage(0, true, 0, slot, false, false, false);
      ELEVATOR_LEADER.setControl(
          ctrl.withPosition(LENGTH * Constants.Elevator.CONVERSION_FACTOR_INCHES_TO_TICKS));
    }
  }

  public void setLengthInches(double length) {
    setLengthInches(length, 0);
  }

  public double getLengthInches() {
    return ELEVATOR_LEADER.getRotorPosition().getValueAsDouble()
        * Constants.Elevator.CONVERSION_FACTOR_TICKS_TO_INCHES;
  }

  public void goHome() {
    setLengthInches(0.0, 0);
  }

  public void zeroPosition() {
    ELEVATOR_LEADER.setPosition(0);
  }

  public void coastElevator() {
    ELEVATOR_LEADER.setNeutralMode(NeutralModeValue.Coast);
    ELEVATOR_FOLLOWER.setNeutralMode(NeutralModeValue.Coast);
  }

  public void brakeElevator() {
    ELEVATOR_LEADER.setNeutralMode(NeutralModeValue.Brake);
    ELEVATOR_FOLLOWER.setNeutralMode(NeutralModeValue.Brake);
  }

  public boolean isAtSetpoint() {
    return ELEVATOR_LEADER.getClosedLoopError().getValueAsDouble() < Constants.Elevator.EXTENSION_ALLOWABLE_ERROR;
  }

  public boolean isAtSetpoint(double allowableError) {
    return ELEVATOR_LEADER.getClosedLoopError().getValueAsDouble() < allowableError;
  }

  public void setZero() {
    ELEVATOR_LEADER.setPosition(0);
  }

}
