// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util.zoning;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * Add your docs here.
 */
public record LocalizationState(FieldZones.Zone fieldZone, Rotation2d ampPassAngle, double ampPassDistance,
                                Rotation2d centerPassAngle, double centerPassDistance, Rotation2d speakerAngle,
                                double speakerDistance) {
}
