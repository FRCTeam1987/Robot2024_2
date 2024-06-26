// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.actions.AutoCollectNote;
import frc.robot.commands.auto.actions.FollowCollectNote;
import frc.robot.commands.auto.actions.InstantShoot;
import frc.robot.commands.auto.actions.PathFind;
import frc.robot.commands.auto.defaults.DefaultAutoIntake;
import frc.robot.commands.auto.defaults.DefaultAutoShooter;
import frc.robot.commands.auto.defaults.DefaultAutoWrist;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.commands.auto.routines.Amp_1_2;
import frc.robot.commands.auto.routines.Madtown;
import frc.robot.commands.auto.routines.Source_5_4;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.*;

/** Add your docs here. */
public class AutoCommands {
  private static final Map<String, Command> namedCommands = Collections.unmodifiableMap(new HashMap<>() {
    {
      put("PrepPoop", new InstCmd(() -> setAutoState(AutoState.POOP_PREP)));
      put("StartPoop", new InstCmd(() -> setAutoState(AutoState.POOPING)));
      put("StopPoop", new InstCmd(() -> setAutoState(AutoState.DEFAULT)));
      put("PrepShoot", new InstantCommand(() -> setAutoState(AutoState.SHOOT_PREP)));
      put("StartPoopMonitor", POOP_MONITOR.StartPoopMonitorCommand());
      put("StopPoopMonitor", POOP_MONITOR.StopPoopMonitorCommand());
      put("AutoCollectNote", new AutoCollectNote(() -> 2.75));
      put("PathFindToSourceShot", PathFind.toSourceShot());
      put("PathFindToAmpShot", PathFind.toAmpShot());
      put("WaitUntilHasNote", new WaitUntilCommand(() -> SHOOTER.hasNote()));
      put("StartPoopMonitor", POOP_MONITOR.StartPoopMonitorCommand());
      put("StopPoopMonitor", POOP_MONITOR.StopPoopMonitorCommand());
      put("StartWatchForNote", new InstantCommand(() -> Madtown.SHOULD_WATCH_FOR_NOTE = true));
      put("StopWatchForNote", new InstantCommand(() -> Madtown.SHOULD_WATCH_FOR_NOTE = false));
      put("FollowCollectNote", new FollowCollectNote());
      put("InstantShoot", new InstantShoot());
      put("StartRotationOverrideSpeaker", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtSpeaker(true)));
      put("StopRotationOverrideSpeaker", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtSpeaker(false)));
      put("StartRotationOverrideNote", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtNote(true)));
      put("StopRotationOverrideNote", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtNote(false)));
      put("AutoAimAndShoot", new AutoAimAndShoot());
      put("StartLLPoseUpdate", new InstantCommand(() -> DRIVETRAIN.setShouldUpdatePoseFromVision(true)));
      put("StopLLPoseUpdate", new InstantCommand(() -> DRIVETRAIN.setShouldUpdatePoseFromVision(false)));
    }
  });

  public static void addAllNamedCommands() {
    NamedCommands.registerCommands(namedCommands);
  }

  private static Boolean isBlueAlliance() {
    return DRIVETRAIN.getAlliance().equals(Alliance.Blue);
  }

  private static Command wrap(final Command autoCommand) {
    return new ParallelCommandGroup(
      autoCommand,
      new DefaultAutoIntake(),
      new DefaultAutoWrist(),
      new DefaultAutoShooter()
    );
  }

  public static SendableChooser<Command> getRoutines() {
    final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
    autoChooser.setDefaultOption("Do Nothing", new InstantCommand());
    autoChooser.addOption("Amp 1-2", wrap(new Amp_1_2()));
    autoChooser.addOption("Madtown", new ConditionalCommand(
    wrap(new Madtown(Alliance.Blue)),
    wrap(new Madtown(Alliance.Red)),
    AutoCommands::isBlueAlliance));
    autoChooser.addOption("Middle 3", wrap(AutoBuilder.buildAuto("middle-3")));
    // autoChooser.addOption("Source 5-4", new ConditionalCommand(
    // wrap(new Source_5_4(Alliance.Blue)),
    // wrap(new Source_5_4(Alliance.Red)),
    // AutoCommands::isBlueAlliance));
    autoChooser.addOption("Split 3", wrap(AutoBuilder.buildAuto("split-3")));
    return autoChooser;
  }
};