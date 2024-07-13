// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.dashboard.tabs;

import static frc.robot.RobotContainer.SEMAPHORE;
import static frc.robot.RobotContainer.getAutoState;
import static frc.robot.RobotContainer.getDriveMode;
import static frc.robot.RobotContainer.getRobotState;
import static frc.robot.RobotContainer.getScoreMode;
import static frc.robot.RobotContainer.setRobotState;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.AutoCommands;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.commands.teleop.stated.TrapState;
import frc.robot.commands.teleop.stateless.recovery.AutoHomeElevator;
import frc.robot.commands.teleop.stateless.recovery.AutoHomeWrist;
import frc.robot.commands.teleop.stateless.recovery.ForceZeroAll;
import frc.robot.commands.teleop.stateless.recovery.ReverseIntake;
import frc.robot.dashboard.TabUtil;
import frc.robot.util.InstCmd;
import frc.robot.util.Util;

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
    tab.add("Increase distance 10cm", new InstantCommand(() -> Util.incrementDistanceOffset(0.1)));
    tab.add("Decrease distance 10cm", new InstantCommand(() -> Util.incrementDistanceOffset(-0.1)));
    InstCmd climb1 = new InstCmd(() -> setRobotState(RobotState.CLIMB_INIT));
    climb1.addRequirements(SEMAPHORE);
    tab.add("1 Climb", climb1);
    SequentialCommandGroup climb2 = new SequentialCommandGroup(
      new InstCmd(() -> setRobotState(RobotState.CLIMB_PULLDOWN)),
      new WaitUntilCommand(() -> RobotContainer.ELEVATOR.isAtSetpoint()),
      new WaitCommand(0.8),
      new InstCmd(() -> setRobotState(RobotState.CLIMB_LEVEL)),
      new WaitUntilCommand(() -> RobotContainer.ELEVATOR.isAtSetpoint()),
      new ConditionalCommand(
          new TrapState(),
          new InstCmd(() -> System.out.println("Climb Finished. No note.")),
          () -> RobotContainer.SHOOTER.isCenterBroken())
    );
    climb2.addRequirements(SEMAPHORE);
    tab.add("2 Climb", climb2);
  }

  public Command getSelectedAuto() {
    return autoSelector.getSelected();
  }
}
