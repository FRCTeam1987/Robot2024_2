// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util.zoning;

import edu.wpi.first.math.geometry.Rotation2d;

/** Add your docs here. */
public class LocalizationState {
  private final FieldZones.Zone fieldZone;
  private final Rotation2d ampPassAngle;
  private final double ampPassDistance;
  private final Rotation2d centerPassAngle;
  private final double centerPassDistance;
  private final Rotation2d speakerAngle;
  private final double speakerDisance;

  public LocalizationState(final FieldZones.Zone fieldZone, final Rotation2d ampPassAngle, final double ampPassDistance,
      final Rotation2d centerPassAngle, final double centerPassDistance,
      final Rotation2d speakerAngle, final double speakerDistance) {
    this.fieldZone = fieldZone;
    this.ampPassAngle = ampPassAngle;
    this.ampPassDistance = ampPassDistance;
    this.centerPassAngle = centerPassAngle;
    this.centerPassDistance = centerPassDistance;
    this.speakerAngle = speakerAngle;
    this.speakerDisance = speakerDistance;
  }

  public FieldZones.Zone getFieldZone() {
    return fieldZone;
  }

  public Rotation2d getAmpPassAngle() {
    return ampPassAngle;
  }

  public double getAmpPassDistance() {
    return ampPassDistance;
  }

  public Rotation2d getCenterPassAngle() {
    return centerPassAngle;
  }

  public double getCenterPassDistance() {
    return centerPassDistance;
  }

  public Rotation2d getSpeakerAngle() {
    return speakerAngle;
  }

  public double getSpeakerDistance() {
    return speakerDisance;
  }
}
