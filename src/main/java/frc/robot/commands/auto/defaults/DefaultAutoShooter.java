// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.util.zoning.FieldZones.Zone;

import static frc.robot.RobotContainer.SHOOTER;
import static frc.robot.Constants.Shooter.*;

public class DefaultAutoShooter extends Command {

  /** Creates a new DefaultAutoShooter. */
  public DefaultAutoShooter() {
    addRequirements(SHOOTER);
  }

  @Override
  public void initialize() {
    SHOOTER.stopFeeder();
    SHOOTER.stopShooter();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final AutoState state = RobotContainer.getAutoState();
    final Zone currentZone = RobotContainer.getLocalizationState().fieldZone();
    switch (state) {
      case COLLECTING:
        if (SHOOTER.isCenterBroken()) {
          SHOOTER.stopFeeder();
        } else if (SHOOTER.isRearBroken() && currentZone == Zone.NEUTRAL_WING) {
          SHOOTER.setFeederVoltage(FEEDER_FEEDFWD_VOLTS_SLOW);
        } else {
          SHOOTER.setFeederVoltage(FEEDER_FEEDFWD_VOLTS);
        }
        if (currentZone == Zone.ALLIANCE_WING || currentZone == Zone.ALLIANCE_HOME
            || currentZone == Zone.ALLIANCE_STAGE) {
          SHOOTER.setRPMShoot(SHOOTER_RPM);
        } else {
          SHOOTER.setRPMShootNoSpin(SHOOTER_POOP_RPM);
        }
        break;
      case POOP_PREP:
        SHOOTER.stopFeeder();
        SHOOTER.setRPMShootNoSpin(SHOOTER_POOP_RPM);
        break;
      case POOPING:
        SHOOTER.setFeederVoltage(FEEDER_FEEDFWD_VOLTS);
        SHOOTER.setRPMShootNoSpin(SHOOTER_POOP_RPM);
        break;
      case FLOWING:
      case SHOOTING:
        SHOOTER.setFeederVoltage(FEEDER_SHOOT_VOLTS);
        SHOOTER.setRPMShoot(SHOOTER_RPM);
        break;
      case SHOOT_PREP:
        SHOOTER.stopFeeder();
        SHOOTER.setRPMShoot(SHOOTER_RPM);
        break;
      case INSTANT_SUB_PREP:
        SHOOTER.setShooterVoltage(18.0);
        break;
      case INSTANT_SUB:
        SHOOTER.setShooterVoltage(18.0);
        SHOOTER.setFeederVoltage(18.0);
        break;
      default:
        if (SHOOTER.isCenterBroken()) {
          SHOOTER.stopFeeder();
          if (state != AutoState.SHOOTING && state != AutoState.SHOOT_PREP) {
            RobotContainer.setAutoState(AutoState.SHOOT_PREP);
          }
          if (currentZone == Zone.ALLIANCE_WING || currentZone == Zone.ALLIANCE_HOME
              || currentZone == Zone.ALLIANCE_STAGE) {
            SHOOTER.setRPMShoot(SHOOTER_RPM);
          } else {
            SHOOTER.setRPMShootNoSpin(SHOOTER_POOP_RPM);
          }
        } else {
          if (SHOOTER.isRearBroken()) {
            SHOOTER.setFeederVoltage(FEEDER_FEEDFWD_VOLTS_SLOW);
          }
          SHOOTER.setRPMShootNoSpin(SHOOTER_POOP_RPM);
        }
    }
  }
}
