// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util.zoning;

import java.util.Arrays;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N2;

/** Add your docs here. */
public class Polygon2d {

    private static final int X = 0;
    private static final int Y = 1;

    public static boolean contains(Vector<N2>[] polygon, Vector<N2> testPoint) {
        int i, j;
        boolean result = false;
        for (i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
            if ((polygon[i].get(Y) > testPoint.get(Y)) != (polygon[j].get(Y) > testPoint.get(Y))
                && (testPoint.get(X) < (polygon[j].get(X) - polygon[i].get(X)) * (testPoint.get(Y) - polygon[i].get(Y)) / (polygon[j].get(Y) - polygon[i].get(Y)))
            ) {
                result = !result;
            }
        }
        return result;
    }

    private final Vector<N2>[] m_points;

    public Polygon2d(Vector<N2>... points) {
        m_points = Arrays.copyOf(points, points.length);
    }

    public int getNumberOfPoints() {
        return m_points.length;
    }
}
