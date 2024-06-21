// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.dashboard.tabs;

import frc.robot.commands.auto.AutoCommands;

/** Add your docs here. */
public class MatchTab extends Tab {
  public MatchTab() {
    super("MATCH");
    tab.add("Autos", AutoCommands.getRoutines());
  }
}
