// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.dashboard.tabs;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/** Add your docs here. */
public class Tab {

  protected final ShuffleboardTab tab;

  public Tab(final String name) {
    tab = Shuffleboard.getTab("name");
  }

  public ShuffleboardTab get() {
    return tab;
  }
}
