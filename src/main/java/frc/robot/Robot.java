// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.teleop.stated.FastSub;

public class Robot extends TimedRobot {
  private Command AUTO_COMMAND;

  private RobotContainer ROBOT_CONTAINER;

  @Override
  public void robotInit() {
    ROBOT_CONTAINER = new RobotContainer();
    ROBOT_CONTAINER.robotInit();
    ROBOT_CONTAINER.robotPeriodic();
    ROBOT_CONTAINER.teleopInit();
  }

  @Override
  public void robotPeriodic() {
    ROBOT_CONTAINER.robotPeriodic();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    // AUTO_COMMAND = ROBOT_CONTAINER.getAutonomousCommand();
    AUTO_COMMAND = new FastSub();
    if (AUTO_COMMAND != null) {
      AUTO_COMMAND.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
  }

  @Override
  public void teleopInit() {

    if (AUTO_COMMAND != null) {
      AUTO_COMMAND.cancel();
    }

    // CommandScheduler.getInstance().schedule(new FastSub());
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
