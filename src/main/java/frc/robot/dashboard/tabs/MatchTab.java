// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.dashboard.tabs;

import static frc.robot.RobotContainer.getAutoState;
import static frc.robot.RobotContainer.getDriveMode;
import static frc.robot.RobotContainer.getRobotState;
import static frc.robot.RobotContainer.getScoreMode;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.AutoCommands;
import frc.robot.commands.teleop.stateless.recovery.AutoHomeElevator;
import frc.robot.commands.teleop.stateless.recovery.AutoHomeWrist;
import frc.robot.commands.teleop.stateless.recovery.ForceZeroAll;
import frc.robot.commands.teleop.stateless.recovery.ReverseIntake;
import frc.robot.dashboard.TabUtil;

/** Add your docs here. */
public class MatchTab {
  private final ShuffleboardTab tab;
  private final SendableChooser<Command> autoSelector;

  public MatchTab() {
    AutoCommands.addAllNamedCommands();
    tab = TabUtil.createTab("MATCH");
    autoSelector = AutoCommands.getRoutines();
    tab.add("Auto Chooser", autoSelector);
    tab.add("Reverse Intake", new ReverseIntake());
    tab.add("Auto Home Wrist", new AutoHomeWrist());
    tab.add("Auto Home Elev", new AutoHomeElevator());
    tab.add("Force Zero", new ForceZeroAll());
    tab.addString("State | Score | Drive", () -> getRobotState() + " | " + getScoreMode() + " | " + getDriveMode());
    tab.addString("Auto State", () -> getAutoState().toString());
  }

  public Command getSelectedAuto() {
    return autoSelector.getSelected();
  }
}
