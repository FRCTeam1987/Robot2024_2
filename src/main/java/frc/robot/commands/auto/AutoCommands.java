// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.HashMap;
import java.util.Map;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.auto.actions.AutoCollectNote;
import frc.robot.commands.auto.actions.PathFind;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

/** Add your docs here. */
public class AutoCommands {
  private static final Map<String, Command> namedCommands = new HashMap<>() {{
    put("PrepPoop", new InstCmd(() -> setAutoState(AutoState.POOP_PREP)));
    put("StartPoop", new InstCmd(() -> setAutoState(AutoState.POOPING)));
    put("StopPoop", new InstCmd(() -> setAutoState(AutoState.DEFAULT)));
    put("StartPoopMonitor", POOP_MONITOR.StartPoopMonitorCommand());
    put("StopPoopMonitor", POOP_MONITOR.StopPoopMonitorCommand());
    put("AutoCollectNote", new AutoCollectNote(2.5));
    put("PathFindToSourceShot", PathFind.toSourceShot());
    put("PathFindToAmpShot", PathFind.toAmpShot());
    put("WaitUntilHasNote", new WaitUntilCommand(() -> SHOOTER.hasNote()));
  }};

  public static void addAllNamedCommands() {
    NamedCommands.registerCommands(namedCommands);
  }
}
