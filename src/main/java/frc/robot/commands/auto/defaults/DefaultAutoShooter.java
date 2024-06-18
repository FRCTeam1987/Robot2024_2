// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.AutoState;
import frc.robot.util.zoning.FieldZones;

import static frc.robot.RobotContainer.SHOOTER;

public class DefaultAutoShooter extends Command {

  /** Creates a new DefaultAutoShooter. */
  public DefaultAutoShooter() {
    addRequirements(SHOOTER);
  }

  // TODO - Clean this up!
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final AutoState state = RobotContainer.getAutoState();
    final FieldZones.Zone currentZone = RobotContainer.getLocalizationState().getFieldZone();
    switch (state) {
      case COLLECTING:
        if (SHOOTER.isCenterBroken()) {
          SHOOTER.stopFeeder();
        } else {
          SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        }
        if (currentZone == FieldZones.Zone.ALLIANCE_WING) {
          SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        } else {
          SHOOTER.setRPMShootNoSpin(Constants.Shooter.SHOOTER_POOP_RPM);
        }
        break;
      case POOP_PREP:
        SHOOTER.stopFeeder();
        SHOOTER.setRPMShootNoSpin(Constants.Shooter.SHOOTER_POOP_RPM);
        break;
      case POOPING:
        SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        SHOOTER.setRPMShootNoSpin(Constants.Shooter.SHOOTER_POOP_RPM);
        break;
      case SHOOT_PREP:
        if (SHOOTER.isRearBroken() && !SHOOTER.isCenterBroken()) {
          SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        } else {
          SHOOTER.stopFeeder();
        }
        SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        break;
      case SHOOTING:
        SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_SHOOT_VOLTS);
        SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        break;
      default:
        SHOOTER.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        if (SHOOTER.isCenterBroken()) {
          SHOOTER.stopFeeder();
          if (state != AutoState.SHOOTING && state != AutoState.SHOOT_PREP) {
            RobotContainer.setAutoState(AutoState.SHOOT_PREP);
          }
        } else if (SHOOTER.isRearBroken() && !SHOOTER.isCenterBroken()) {
          SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        } else {
          SHOOTER.stopFeeder();
        }
    }
  }
}
