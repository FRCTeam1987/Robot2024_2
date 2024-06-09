// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.math.geometry.Rotation2d;

/** Add your docs here. */
public class LocalizationState {
  private final FieldZones.Zone fieldZone;
  private final Rotation2d passAngle;
  private final double passDistance;
  private final Rotation2d speakerAngle;
  private final double speakerDisance;

  public LocalizationState(final FieldZones.Zone fieldZone, final Rotation2d passAngle, final double passDistance, final Rotation2d speakerAngle, final double speakerDistance) {
    this.fieldZone = fieldZone;
    this.passAngle = passAngle;
    this.passDistance = passDistance;
    this.speakerAngle = speakerAngle;
    this.speakerDisance = speakerDistance;
  }

  public FieldZones.Zone getFieldZone() {
    return fieldZone;
  }

  public Rotation2d getPassAngle() {
    return passAngle;
  }

  public double getPassDistance() {
    return passDistance;
  }

  public Rotation2d getSpeakerAngle() {
    return speakerAngle;
  }

  public double getSpeakerDistance() {
    return speakerDisance;
  }
}
