// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.defaults;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.AutoState;
import frc.robot.subsystems.Shooter;
import frc.robot.util.FieldZones;

public class DefaultAutoShooter extends Command {

  private final Shooter shooter;

  /** Creates a new DefaultAutoShooter. */
  public DefaultAutoShooter() {
    this.shooter = RobotContainer.SHOOTER;
    addRequirements(this.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final AutoState state = RobotContainer.getAutoState();
    switch (state) {
      case COLLECTING:
        if (shooter.isCenterBroken()) {
          shooter.stopFeeder();
        } else {
          shooter.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        }
        final double poseX = RobotContainer.DRIVETRAIN.getPose().getX();
        if (poseX < FieldZones.BLUE_WING_LINE_X || poseX > FieldZones.RED_WING_LINE_X) {
          shooter.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        } else {
          shooter.setRPMShootNoSpin(Constants.Shooter.SHOOTER_POOP_RPM);
        }
        break;
      case POOP_PREP:
        shooter.stopFeeder();
        shooter.setRPMShootNoSpin(Constants.Shooter.SHOOTER_POOP_RPM);
        break;
      case POOPING:
        shooter.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        shooter.setRPMShootNoSpin(Constants.Shooter.SHOOTER_POOP_RPM);
        break;
      case SHOOT_PREP:
        if (shooter.isRearBroken() && !shooter.isCenterBroken()) {
          shooter.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        } else {
          shooter.stopFeeder();
        }
        shooter.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        break;
      case SHOOTING:
        shooter.setFeederVoltage(Constants.Shooter.FEEDER_SHOOT_VOLTS);
        shooter.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        break;
      default:
        shooter.setRPMShoot(Constants.Shooter.SHOOTER_RPM);
        if (shooter.isCenterBroken()) {
          shooter.stopFeeder();
          if (state != AutoState.SHOOTING && state != AutoState.SHOOT_PREP) {
            RobotContainer.setAutoState(AutoState.SHOOT_PREP);
          }
        } else if (shooter.isRearBroken() && !shooter.isCenterBroken()) {
          shooter.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
        } else {
          shooter.stopFeeder();
        }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
