// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.auto.actions.AutoAimAndShoot;
import frc.robot.commands.auto.actions.AutoCollectNote;
import frc.robot.commands.auto.actions.FlowShoot;
import frc.robot.commands.auto.actions.FollowCollectNote;
import frc.robot.commands.auto.actions.InstantShoot;
import frc.robot.commands.auto.actions.PathFind;
import frc.robot.commands.auto.defaults.DefaultAutoElevator;
import frc.robot.commands.auto.defaults.DefaultAutoIntake;
import frc.robot.commands.auto.defaults.DefaultAutoShooter;
import frc.robot.commands.auto.defaults.DefaultAutoWrist;
import frc.robot.commands.auto.logic.AutoState;
import frc.robot.commands.auto.routines.Amp_1_2;
import frc.robot.commands.auto.routines.Amp_2_1;
import frc.robot.commands.auto.routines.Madtown;
import frc.robot.commands.auto.routines.Source_5_4;
import frc.robot.commands.auto.routines._Source_5_4;
import frc.robot.util.InstCmd;
import frc.robot.util.WaitUntilDebounceCommand;

import static frc.robot.RobotContainer.*;

/** Add your docs here. */
public class AutoCommands {

  public static boolean SHOULD_WATCH_FOR_NOTE = false;
  public static boolean INITIAL_WAS_INTERRUPTED = false;

  private static final Map<String, Command> namedCommands = Collections.unmodifiableMap(new HashMap<>() {
    {
      put("PrepPoop", new InstCmd(() -> setAutoState(AutoState.POOP_PREP)));
      put("StartPoop", new InstCmd(() -> setAutoState(AutoState.POOPING)));
      put("StopPoop", new InstCmd(() -> setAutoState(AutoState.DEFAULT)));
      put("PrepShoot", new InstCmd(() -> setAutoState(AutoState.SHOOT_PREP)));
      put("StartPoopMonitor", POOP_MONITOR.StartPoopMonitorCommand());
      put("StopPoopMonitor", POOP_MONITOR.StopPoopMonitorCommand());
      put("AutoCollectNote", new AutoCollectNote(() -> 2.75));
      put("PathFindToSourceShot", PathFind.toSourceShot());
      put("PathFindToAmpShot", PathFind.toAmpShot());
      put("WaitUntilHasNote", new WaitUntilCommand(() -> SHOOTER.hasNote()));
      put("StartPoopMonitor", POOP_MONITOR.StartPoopMonitorCommand());
      put("StopPoopMonitor", POOP_MONITOR.StopPoopMonitorCommand());
      put("StartWatchForNote", new InstCmd(() -> SHOULD_WATCH_FOR_NOTE = true));
      put("StopWatchForNote", new InstCmd(() -> SHOULD_WATCH_FOR_NOTE = false));
      put("FollowCollectNote", new FollowCollectNote());
      put("InstantShoot", new InstantShoot());
      put("StartRotationOverrideSpeaker", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtSpeaker(true)));
      put("StopRotationOverrideSpeaker", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtSpeaker(false)));
      put("StartRotationOverrideNote", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtNote(true)));
      put("StopRotationOverrideNote", new InstCmd(() -> DRIVETRAIN.setPPShouldPointAtNote(false)));
      put("AutoAimAndShoot", new AutoAimAndShoot());
      put("FlowShoot", new FlowShoot());
      put("StartLLPoseUpdate", new InstCmd(() -> DRIVETRAIN.setShouldUpdatePoseFromVision(true)));
      put("StopLLPoseUpdate", new InstCmd(() -> DRIVETRAIN.setShouldUpdatePoseFromVision(false)));
      put("PoopIfSeesNote", new ConditionalCommand(
        new InstCmd(() -> setAutoState(AutoState.POOPING)).andThen(
          new WaitUntilDebounceCommand(() -> SHOOTER.hasNote(), 0.1, DebounceType.kFalling)
        ),
        new InstCmd(),
        () -> VISION.hasTargets()
      ));
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
      new DefaultAutoShooter(),
      new DefaultAutoElevator()
    );
  }

  public static SendableChooser<Command> getRoutines() {
    final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
    autoChooser.setDefaultOption("Do Nothing", new InstCmd());
    autoChooser.addOption("Amp 1-2", wrap(new Amp_1_2()));
    autoChooser.addOption("Amp 2-1", wrap(new Amp_2_1()));
    autoChooser.addOption("Madtown", new ConditionalCommand(
      wrap(new Madtown(Alliance.Blue)),
      wrap(new Madtown(Alliance.Red)),
      AutoCommands::isBlueAlliance)
    );
    autoChooser.addOption("Middle 3", wrap(AutoBuilder.buildAuto("middle-3")));
    autoChooser.addOption("Source 5-4 Default", AutoBuilder.buildAuto("source_5-4_initial")); 
    autoChooser.addOption("Source 5-4", new ConditionalCommand(
      wrap(new Source_5_4(Alliance.Blue)),
      wrap(new Source_5_4(Alliance.Red)),
      AutoCommands::isBlueAlliance)
    );
    autoChooser.addOption("_Source_5_4", new _Source_5_4());
    autoChooser.addOption("Split 3", wrap(AutoBuilder.buildAuto("split-3")));
    return autoChooser;
  }
};