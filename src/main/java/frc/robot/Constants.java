// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.filter.SlewRateLimiter;
import frc.robot.util.interpolable.InterpolatingDouble;
import frc.robot.util.interpolable.InterpolatingTreeMap;

/** Add your docs here. */
public class Constants {

        public static class Debouncers {
                public static final double TRAP_DEBOUNCE_TIME = 0.06;
        }

        public static class Limiters {
                public static final double TRANSLATION_SLEW_RATE = 2.5;
                public static final double TRANSLATION_SLEW_RATE_CLOSE = 1.5;
                public static final SlewRateLimiter TRANSLATION_X_SLEW_RATE = new SlewRateLimiter(
                                Constants.Limiters.TRANSLATION_SLEW_RATE);
                public static final SlewRateLimiter TRANSLATION_Y_SLEW_RATE = new SlewRateLimiter(
                                Constants.Limiters.TRANSLATION_SLEW_RATE);
                public static final SlewRateLimiter TRANSLATION_X_SLEW_RATE_CLOSE = new SlewRateLimiter(
                                Constants.Limiters.TRANSLATION_SLEW_RATE_CLOSE);
                public static final SlewRateLimiter TRANSLATION_Y_SLEW_RATE_CLOSE = new SlewRateLimiter(
                                Constants.Limiters.TRANSLATION_SLEW_RATE_CLOSE);
        }

        public static class Limelight {
                public static final String LEFT_LOW = "limelight-leftlo";
                public static final String RIGHT_LOW = "limelight-rightlo";
                public static final List<String> LL3GS = List.of(LEFT_LOW, RIGHT_LOW);
                public static final String FRONT = "limelight-intake";
                public static final List<String> LL3S = List.of(FRONT);
                public static final List<String> LLS = List.of(LEFT_LOW, RIGHT_LOW, FRONT);
        }

        public static class Photon {
                public static final String INTAKE_PHOTON_CAMERA_NAME = "Arducam_OV9782_USB_Camera";
                public static final double INTAKE_CAMERA_HEIGHT_METERS = 0.651830;
                public static final double INTAKE_CAMERA_ANGLE_DEGREES = 60.0;
        }

        public static class Drive {
                public static final double DRIVE_MOTOR_AMPS_NORMAL = 60;
                public static final double DRIVE_MOTOR_AMPS_DEFENSE = DRIVE_MOTOR_AMPS_NORMAL + 20.0;
        }

        public static class IDs {
                public static final String CANBUS_ATTACHED = "canfd";
                public static final String CANBUS_DETACHED = "rio";

                public static final int LEFT_CANDLE = 28;
                public static final int RIGHT_CANDLE = 29;

                public static final int ELEVATOR_LEADER_ID = 60;
                public static final int ELEVATOR_FOLLOWER_ID = 61;

                public static final int CLIMB_LEFT = 58;
                public static final int CLIMB_RIGHT = 57;

                public static final int INTAKE_TOP_ID = 51; // front / top most roller
                public static final int INTAKE_BOTTOM_ID = 52; // inside / bottom most roller

                public static final int WRIST_ID = 54;

                public static final int SHOOTER_LEADER_ID = 53;
                public static final int SHOOTER_FOLLOWER_ID = 56;
                public static final int SHOOTER_FEEDER_ID = 55;
                public static final int SHOOTER_FEEDER_ID_TEMP = 10;

                public static final int PROXIMITY_SENSOR_LEFT_ID = 9;
                public static final int PROXIMITY_SENSOR_RIGHT_ID = 8;

        }

        public static class Wrist {

                public static final double WRIST_KP = 250.0; // 200
                public static final double WRIST_KI = 0.0;
                public static final double WRIST_KD = 0.01;
                public static final double WRIST_KV = 0.03;

                public static final double WRIST_ALLOWABLE_ERROR = 0.0014;

                public static final double WRIST_CURRENT_LIMIT = 25;

                public static final double WRIST_MOTION_ACCELERATION = 100;
                public static final double WRIST_MOTION_CRUISE_VELOCITY = 8600;
                public static final double WRIST_MOTION_JERK = 0;

                public static final double WRIST_MIN_DEG = 7;
                public static final double WRIST_MAX_DEG = 120; // temp 115;

                public static final double MAX_SHOOT_DEG = 35.0;
                public static final double COLLECT_DEG = 26.0;
                public static final double POOP_DEG = 26.0;
                public static final double SUBWOOFER_SHOT_DEG = 50.0;
                public static final double PODIUM_SHOT_DEG = 21.0;

                public static final double WRIST_MAX_ROT = 3;
                public static final double WRIST_MIN_ROT = 0;

                public static final double CONVERSION_FACTOR_DEGREES_TO_ROTS = (WRIST_MAX_ROT - WRIST_MIN_ROT)
                                / (WRIST_MAX_DEG - WRIST_MIN_DEG);
                public static final double CONVERSION_FACTOR_ROTS_TO_DEGREES = (WRIST_MAX_DEG - WRIST_MIN_DEG)
                                / (WRIST_MAX_ROT - WRIST_MIN_ROT);

                public static final double INITIAL_ANGLE_DEGREES = 7.0;

                public static final double AMP_WRIST_DEGREES = 8.0;
                public static final double PASS_WRIST_DEGREES = 43.0;

                public static final double TRAP_WRIST_DEGREES = 119.0;
                public static final double TRAP_WRIST_DEGREES_MIDWAY = 85.0;
        }

        public static class Elevator {

                public static final double EXTENSION_KP = 2.5;
                public static final double EXTENSION_KI = 0.0;
                public static final double EXTENSION_KD = 0.1;
                public static final double EXTENSION_KV = 0.15;

                public static final double EXTENSION_KP_1 = 8.0;
                public static final double EXTENSION_KI_1 = 0.6;
                public static final double EXTENSION_KD_1 = 0.1;
                public static final double EXTENSION_KV_1 = 0.45;

                public static final double EXTENSION_MOTION_ACCELERATION = 45000;
                public static final double EXTENSION_CRUISE_VELOCITY = 65000;
                public static final double EXTENSION_ALLOWABLE_ERROR = 0.275;

                public static final double EXTENSION_CURRENT_LIMIT = 60.0;

                public static final double MINIMUM_EXTENSION_LENGTH_INCHES = 0.0;
                public static final double MAXIMUM_EXTENSION_LENGTH_INCHES = 30.5;

                public static final double MINIMUM_EXTENSION_ROTATIONS = 0.0;
                public static final double MAXIMUM_EXTENSION_ROTATIONS = 55.871;

                public static final double CONVERSION_FACTOR_INCHES_TO_TICKS = (MAXIMUM_EXTENSION_ROTATIONS
                                - MINIMUM_EXTENSION_ROTATIONS)
                                / (MAXIMUM_EXTENSION_LENGTH_INCHES - MINIMUM_EXTENSION_LENGTH_INCHES);
                public static final double CONVERSION_FACTOR_TICKS_TO_INCHES = (MAXIMUM_EXTENSION_LENGTH_INCHES
                                - MINIMUM_EXTENSION_LENGTH_INCHES)
                                / (MAXIMUM_EXTENSION_ROTATIONS - MINIMUM_EXTENSION_ROTATIONS);

                public static final double AMP_ELEVATOR_HEIGHT = 30.5;
                public static final double AMP_ELEVATOR_EXIT_HEIGHT = 4.2;

                public static final double PASS_ELEVATOR_HEIGHT = 6.4;

                public static final double SUBWOOFER_SHOT_HEIGHT = 6.5;
                public static final double PODIUM_SHOT_HEIGHT = 28;

                public static final double CLIMB_PULLDOWN_HEIGHT = 6.75;
                public static final double CLIMB_LEVEL_HEIGHT = 11.0;
                public static final double CLIMB_START_HEIGHT = 28;

                public static final double TRAP_ELEVATOR_HEIGHT = 30.0;
                public static final double TRAP_ELEVATOR_HEIGHT_MIDWAY = 24.0;
        }

        public static class Shooter {
                public static final double FEEDER_FEEDFWD_VOLTS = 4; // 6 // 4
                public static final double FEEDER_FEEDFWD_VOLTS_SLOW = 2.0; // 6 // 4
                public static final double FEEDER_FEEDFWD_VOLTS_AGRESSIVE = 6; // 6 // 4
                public static final double FEEDER_SHOOT_VOLTS = 10.0; // 4
                public static final double FEEDER_RETRACT_VOLTS = -2;

                public static final double SHOOTER_RPM = 4500;
                public static final double SHOOTER_POOP_RPM = 650;
                public static final double SHOOTER_RPM_CLOSERANGE = 3500; // NEEDS to be smaller
                public static final double SHOOTER_LOB_RPM = 3000; // NEEDS to be smaller
                public static final double SHOOTER_IDLE_SHOOTING_RPM = 3000; // NEEDS to be smaller
                public static final double SHOOTER_IDLE_RPM = 2500; // 2500
                public static final double SHOOTER_IDLE_RPM_CLOSE = 4200; // 2500
                public static final double SHOOTER_IDLE_CLOSERANGE_RPM = 2500; // NEEDS to be smaller
                public static final double SPIN_RATIO = 0.66; // 0.85
                public static final double LESS_SPIN_RATIO = 0.80; // 0.85
                public static final double ANTI_SPIN_RATIO = 1.34; // 0.85
                public static final int SHOOTER_AMP_RPM = 550;

                public static final double SHOT_DEBOUNCE_TIME = 0.08;
                public static final double PASS_DEBOUNCE_TIME = 0.16;
                public static final double AMP_FEEDER_VOLTAGE = -7.0;
                public static final double FEEDER_FEEDBACKWARD = -2.0;

                public static final double SUBWOOFER_SHOT_RPM = 2750;

                public static final double TRAP_RPM_SPEED = 350; //480
        }

        public static class Intake {
                public static final double INTAKE_COLLECT_VOLTS = -8; // 6
                public static final double INTAKE_COLLECT_SLOW_VOLTS = -7;
                public static final double INTAKE_COLLECT_VOLTS_MANUAL = -8; // 6 //-9
                public static final double INTAKE_RPM = -5000;
        }

        public static final InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble>
        DISTANCE_TO_PASS_RPM = // (Meters, Wrist Degrees)
                        new InterpolatingTreeMap<>();
        public static final InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble>
        DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER = // (Meters, Wrist Degrees)
                        new InterpolatingTreeMap<>();

        static {
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(2.334), new InterpolatingDouble(35.50));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(2.40), new InterpolatingDouble(35.10));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(2.50), new InterpolatingDouble(34.23));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(2.69), new InterpolatingDouble(32.87));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(2.87), new InterpolatingDouble(31.69));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(3.03), new InterpolatingDouble(30.24));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(3.207), new InterpolatingDouble(28.86));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(3.388),
                                new InterpolatingDouble(28.21)); // Closer values may require a slower shooter RPM Speed
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(3.70), new InterpolatingDouble(27.08));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(4.03), new InterpolatingDouble(25.63));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(4.23), new InterpolatingDouble(25.07));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(4.46), new InterpolatingDouble(24.65));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(4.69), new InterpolatingDouble(24.35));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(4.99), new InterpolatingDouble(23.21));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(5.21), new InterpolatingDouble(22.81));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(5.40), new InterpolatingDouble(22.92));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(5.69), new InterpolatingDouble(21.63));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(5.89), new InterpolatingDouble(21.47));
                DISTANCE_TO_WRISTANGLE_RELATIVE_SPEAKER.put(
                                new InterpolatingDouble(6.15), new InterpolatingDouble(21.45));

                // DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(10.28), new
                // InterpolatingDouble(3000.0 * 1.07));
                // DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(9.5), new
                // InterpolatingDouble(2750.0 * 1.07));
                // DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(8.6), new
                // InterpolatingDouble(2700.0 * 1.07));
                // DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(8.3), new
                // InterpolatingDouble(2620.0 * 1.07));
                // DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(8.0), new
                // InterpolatingDouble(2580.0 * 1.07));
                // DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(7.88), new
                // InterpolatingDouble(2440.0 * 1.07));

                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(5.2), new InterpolatingDouble(1500.0));

                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(6.0), new InterpolatingDouble(1700.0));

                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(6.6), new InterpolatingDouble(1800.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(7.17), new InterpolatingDouble(1900.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(8.5), new InterpolatingDouble(2200.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(9.0), new InterpolatingDouble(2350.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(9.5), new InterpolatingDouble(2450.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(10.0), new InterpolatingDouble(2550.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(10.5), new InterpolatingDouble(2650.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(11.0), new InterpolatingDouble(2725.0));
                DISTANCE_TO_PASS_RPM.put(new InterpolatingDouble(11.5), new InterpolatingDouble(2825.0));

        }
}
