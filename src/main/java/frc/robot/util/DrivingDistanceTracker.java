// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.math.geometry.Translation2d;

/** Add your docs here. */
public class DrivingDistanceTracker {

    private static double distanceDriven;
    private static Translation2d previousLocation;

    public DrivingDistanceTracker() {
        reset();
    }

    public double getDistanceDriven() {
        return distanceDriven;
    }

    public void reset() {
        reset(new Translation2d());
    }

    public void reset(final Translation2d currentTranslation) {
        distanceDriven = 0.0;
        previousLocation = currentTranslation;
    }

    public void update(final Translation2d currentTranslation) {
        final double distance = previousLocation.getDistance(currentTranslation);
        if (Math.abs(distance) < 0.05) {
            return;
        }
        distanceDriven += distance;
        previousLocation = currentTranslation;
    }
}
