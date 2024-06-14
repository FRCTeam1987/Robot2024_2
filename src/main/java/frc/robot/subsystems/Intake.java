package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final TalonFX INTAKE_TOP;
  private final TalonFX INTAKE_BOTTOM;

  public Intake(final int INTAKE_OUT_ID, final int INTAKE_IN_ID, String CANBUS) {
    INTAKE_TOP = new TalonFX(INTAKE_OUT_ID, CANBUS);
    INTAKE_BOTTOM = new TalonFX(INTAKE_IN_ID, CANBUS);

    final Slot0Configs TOP_SLOT0_CFG = new Slot0Configs();
    TOP_SLOT0_CFG.kP = 0.8;
    TOP_SLOT0_CFG.kI = 0.6;
    TOP_SLOT0_CFG.kD = 0;
    TOP_SLOT0_CFG.kV = 0.01;

    final Slot0Configs BOTTOM_SLOT0_CFG = new Slot0Configs();
    BOTTOM_SLOT0_CFG.kP = 0.8;
    BOTTOM_SLOT0_CFG.kI = 0.6;
    BOTTOM_SLOT0_CFG.kD = 0;
    BOTTOM_SLOT0_CFG.kV = 0.01;

    final TalonFXConfiguration TOP_CFG = new TalonFXConfiguration();
    TOP_CFG.Slot0 = TOP_SLOT0_CFG;
    TOP_CFG.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.6;
    TOP_CFG.CurrentLimits.StatorCurrentLimit = 40;
    TOP_CFG.CurrentLimits.StatorCurrentLimitEnable = true;

    final TalonFXConfiguration BOTTOM_CFG = new TalonFXConfiguration();
    BOTTOM_CFG.Slot0 = BOTTOM_SLOT0_CFG;
    BOTTOM_CFG.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.6;
    BOTTOM_CFG.CurrentLimits.StatorCurrentLimit = 40;
    BOTTOM_CFG.CurrentLimits.StatorCurrentLimitEnable = true;

    INTAKE_TOP.getConfigurator().apply(TOP_CFG);
    INTAKE_BOTTOM.getConfigurator().apply(BOTTOM_CFG);

    INTAKE_BOTTOM.setInverted(false);

    INTAKE_TOP.setNeutralMode(NeutralModeValue.Coast);
    INTAKE_BOTTOM.setNeutralMode(NeutralModeValue.Coast);
  }

  public void setRPM(double RPM) {
    // CANNOT FOLLOW. DIFFERENT PID CTRL FOR EACH ROLLER.
    VelocityVoltage ctl = new VelocityVoltage(0);
    INTAKE_BOTTOM.setControl(ctl.withVelocity(RPM / 60.0));
    INTAKE_TOP.setControl(ctl.withVelocity(RPM / 60.0));
  }

  public void setVolts(double VOLTS) {
    // CANNOT FOLLOW. DIFFERENT PID CTRL FOR EACH ROLLER.
    INTAKE_TOP.setVoltage(VOLTS);
    INTAKE_BOTTOM.setVoltage(VOLTS);
  }

  public void stopBoth() {
    INTAKE_TOP.set(0.0);
    INTAKE_BOTTOM.set(0.0);
  }

  public void stopTop() {
    INTAKE_TOP.set(0.0);
  }

  public double getRPMTop() {
    return INTAKE_BOTTOM.getVelocity().getValueAsDouble() * 60;
  }

  public double getRPMBottom() {
    return INTAKE_TOP.getVelocity().getValueAsDouble() * 60;
  }

  public double getAmpsTop() {
    return INTAKE_TOP.getStatorCurrent().getValueAsDouble();
  }


}
