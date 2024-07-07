// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stated;

import static frc.robot.RobotContainer.setDriveMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.teleop.logic.DriveMode;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.util.zoning.FieldZones;
import frc.robot.util.InstCmd;

import static frc.robot.RobotContainer.SEMAPHORE;
import static frc.robot.RobotContainer.SHOOTER;
import static frc.robot.RobotContainer.getLocalizationState;
import static frc.robot.RobotContainer.setRobotState;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeNoteState extends SequentialCommandGroup {
  /** Creates a new IntakeNoteState. */
  public IntakeNoteState() {

    addRequirements(SEMAPHORE);

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstCmd(() -> {
          setRobotState(RobotState.COLLECTING);
        }),
        new WaitUntilCommand(() -> SHOOTER.isRearBroken()),
        new InstCmd(() -> {
          setRobotState(RobotState.COLLECTING_SLOW);
        }),
        new WaitUntilCommand(() -> SHOOTER.isCenterBroken()),
        new InstCmd(() -> {
          if (getLocalizationState().getFieldZone() != FieldZones.Zone.OPPONENT_WING) {
            setDriveMode(DriveMode.AUTOMATIC);
          }
          setRobotState(RobotState.DEFAULT);
        }));
  }
}
