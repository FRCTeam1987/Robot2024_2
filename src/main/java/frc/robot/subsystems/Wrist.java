package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Wrist extends SubsystemBase {
  private final TalonFX WRIST_MOTOR;

  private boolean shouldLockWristDown = false;

  public Wrist(final int wristMotorID, String CANBUS) {
    WRIST_MOTOR = new TalonFX(wristMotorID, CANBUS);
    final TalonFXConfiguration WRIST_CONFIG = new TalonFXConfiguration();

    WRIST_CONFIG.Slot0.kP = Constants.Wrist.WRIST_KP;
    WRIST_CONFIG.Slot0.kI = Constants.Wrist.WRIST_KI;
    WRIST_CONFIG.Slot0.kD = Constants.Wrist.WRIST_KD;
    WRIST_CONFIG.Slot0.kV = Constants.Wrist.WRIST_KV;

    WRIST_CONFIG.Slot1.kP = 350.0;
    WRIST_CONFIG.Slot1.kI = Constants.Wrist.WRIST_KI;
    WRIST_CONFIG.Slot1.kD = Constants.Wrist.WRIST_KD;
    WRIST_CONFIG.Slot1.kV = Constants.Wrist.WRIST_KV;

    WRIST_CONFIG.CurrentLimits.StatorCurrentLimit = Constants.Wrist.WRIST_CURRENT_LIMIT;
    WRIST_CONFIG.CurrentLimits.StatorCurrentLimitEnable = true;

    WRIST_CONFIG.MotionMagic.MotionMagicCruiseVelocity = 15; // 50
    WRIST_CONFIG.MotionMagic.MotionMagicAcceleration = WRIST_CONFIG.MotionMagic.MotionMagicCruiseVelocity / 4.0;
    WRIST_CONFIG.Feedback.SensorToMechanismRatio = (120.0 / 10.0) * (80.0 / 12.0);

    WRIST_MOTOR.getConfigurator().apply(WRIST_CONFIG);

    setZero();

    WRIST_MOTOR.setNeutralMode(NeutralModeValue.Brake);

    disableWristLockdown();

  }

  public void goHome() {
    setDegrees(Constants.Wrist.INITIAL_ANGLE_DEGREES, 0);
  }

  public void setCoast() {
    WRIST_MOTOR.setNeutralMode(NeutralModeValue.Coast);
  }

  public void setBrake() {
    WRIST_MOTOR.setNeutralMode(NeutralModeValue.Brake);
  }

  public void zeroSensor() {
    WRIST_MOTOR.setPosition(Constants.Wrist.INITIAL_ANGLE_DEGREES / 360.0);
  }

  public double getError() {
    return WRIST_MOTOR.getClosedLoopError().getValueAsDouble();
  }

  public boolean isAtSetpoint() {

    return WRIST_MOTOR.getClosedLoopError().getValueAsDouble() < Constants.Wrist.WRIST_ALLOWABLE_ERROR;
  }

  public boolean isAtSetpoint(double allowableError) {

    return WRIST_MOTOR.getClosedLoopError().getValueAsDouble() < allowableError;
  }

  public double getVelocity() {
    return WRIST_MOTOR.getVelocity().getValueAsDouble();
  }

  public void setVoltage(double volts) {
    WRIST_MOTOR.setVoltage(volts);
  }

  public double getDegrees() {
    // initial angle irl is 13 degrees
    // 44 degrees at 1.67 rotations
    // Reduction is 18T to 36T pulley, 10T to 100T gear
    return WRIST_MOTOR.getPosition().getValueAsDouble() * 360.0;
  }

  public void setDegrees(double degrees, int slot) {
    if (degrees > Constants.Wrist.WRIST_MAX_DEG || degrees < Constants.Wrist.WRIST_MIN_DEG) {
      System.out.println("Out of Wrist Range! " + degrees);
    } else {
      double arbFF = 0.4 * Math.sin(Math.toRadians(90.0 - degrees));
      WRIST_MOTOR.setControl(
          new MotionMagicVoltage(degrees / 360.0, true, arbFF, slot, false, false, false));
    }
  }

  public void setDegrees(double degrees) {
    setDegrees(degrees, 0);
  }

  public void stop() {
    WRIST_MOTOR.set(0);
  }

  public void coast() {
    WRIST_MOTOR.setNeutralMode(NeutralModeValue.Coast);
  }

  public void brake() {
    WRIST_MOTOR.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setZero() {
    WRIST_MOTOR.setPosition(Constants.Wrist.INITIAL_ANGLE_DEGREES / 360.0);
  }

  public void enableWristLockdown() {
    shouldLockWristDown = true;
  }

  public void disableWristLockdown() {
    shouldLockWristDown = false;
  }

  public boolean shouldLockDownWrist() {
    return shouldLockWristDown;
  }
}
