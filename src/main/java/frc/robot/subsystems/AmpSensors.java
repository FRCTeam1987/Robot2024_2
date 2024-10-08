// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AmpSensors extends SubsystemBase {

  private final DigitalInput LEFT_SENSOR;
  private final DigitalInput RIGHT_SENSOR;

  public AmpSensors(int PROXIMITY_LEFT, int PROXIMITY_RIGHT) {
    LEFT_SENSOR = new DigitalInput(PROXIMITY_LEFT);
    RIGHT_SENSOR = new DigitalInput(PROXIMITY_RIGHT);
  }

  public boolean getSensorLeft() {
    // 6.2 inches from wall
    return !LEFT_SENSOR.get();
  }

  public boolean getSensorRight() {
    // 6.2 inches from wall
    return !RIGHT_SENSOR.get();
  }

  public boolean getBothSensors() {
    return this.getBothSensors(true);
  }

  public boolean getBothSensors(boolean shouldReturnAsAND) {
    if (shouldReturnAsAND) {
      return this.getSensorLeft() && this.getSensorRight();
    } else {
      return this.getSensorLeft() || this.getSensorRight();
    }
  }
}
