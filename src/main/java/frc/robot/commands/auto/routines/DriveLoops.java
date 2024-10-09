// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.util.zoning.LocalizationUtil;

/** Add your docs here. */
public class DriveLoops {

    public static SequentialCommandGroup get() {
        return new InstantCommand(() -> {
            final Translation2d startTranslation = LocalizationUtil.blueFlipToRed(new Translation2d(2.39, 5.53));
            RobotContainer.tracker.reset(startTranslation);
            RobotContainer.DRIVETRAIN.seedFieldRelative(new Pose2d(startTranslation, Rotation2d.fromDegrees(-90.0)));
        }).andThen(
            new RepeatCommand(AutoBuilder.buildAuto("loop-auto")
                .andThen(new WaitCommand(1.0))
            )    
        );
    }
}
