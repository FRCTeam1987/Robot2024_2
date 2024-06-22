// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.dashboard.tabs;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.AutoCommands;

/** Add your docs here. */
public class MatchTab {
  private final ShuffleboardTab tab;
  private final SendableChooser<Command> autoSelector;
  public MatchTab() {
    AutoCommands.addAllNamedCommands();
    tab = TabUtil.createTab("MATCH");
    autoSelector = AutoCommands.getRoutines();
    tab.add("Auto Chooser", autoSelector);
  }

  public Command getSelectedAuto() {
    return autoSelector.getSelected();
  }
}
