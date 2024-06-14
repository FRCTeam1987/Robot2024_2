// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Arrays;
import java.util.List;

/** Add your docs here. */
public class Constants {

  public static class Limelight {
    public static final String LEFT_LOW = "limelight-leftlo";
    public static final String RIGHT_LOW = "limelight-rightlo";
    public static final List<String> LL3GS = Arrays.asList(LEFT_LOW, RIGHT_LOW);
  }
  
  public static class Photon {
    public static final String INTAKE_PHOTON_CAMERA_NAME = "Arducam_OV9782_USB_Camera";
    public static final double INTAKE_CAMERA_HEIGHT_METERS = 0.651830;
    public static final double INTAKE_CAMERA_ANGLE_DEGREES = 60.0;
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

    public static final double WRIST_MAX_ROT = 3;
    public static final double WRIST_MIN_ROT = 0;

    public static final double CONVERSION_FACTOR_DEGREES_TO_ROTS =
        (WRIST_MAX_ROT - WRIST_MIN_ROT) / (WRIST_MAX_DEG - WRIST_MIN_DEG);
    public static final double CONVERSION_FACTOR_ROTS_TO_DEGREES =
        (WRIST_MAX_DEG - WRIST_MIN_DEG) / (WRIST_MAX_ROT - WRIST_MIN_ROT);

    public static final double INITIAL_ANGLE_DEGREES = 7.0;
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
    public static final double EXTENSION_ALLOWABLE_ERROR = 0.375;

    public static final double EXTENSION_CURRENT_LIMIT = 90.0;

    public static final double MINIMUM_EXTENSION_LENGTH_INCHES = 0.0;
    public static final double MAXIMUM_EXTENSION_LENGTH_INCHES = 30.5;

    public static final double MINIMUM_EXTENSION_ROTATIONS = 0.0;
    public static final double MAXIMUM_EXTENSION_ROTATIONS = 55.871;

    public static final double CONVERSION_FACTOR_INCHES_TO_TICKS =
        (MAXIMUM_EXTENSION_ROTATIONS - MINIMUM_EXTENSION_ROTATIONS)
            / (MAXIMUM_EXTENSION_LENGTH_INCHES - MINIMUM_EXTENSION_LENGTH_INCHES);
    public static final double CONVERSION_FACTOR_TICKS_TO_INCHES =
        (MAXIMUM_EXTENSION_LENGTH_INCHES - MINIMUM_EXTENSION_LENGTH_INCHES)
            / (MAXIMUM_EXTENSION_ROTATIONS - MINIMUM_EXTENSION_ROTATIONS);
  }

  public static class Shooter {
    public static final double FEEDER_FEEDFWD_VOLTS = 4; // 6 // 4
    public static final double FEEDER_FEEDFWD_VOLTS_AGRESSIVE = 6; // 6 // 4
    public static final double FEEDER_SHOOT_VOLTS = 8; // 4
    public static final double FEEDER_RETRACT_VOLTS = -2;
    public static final double FEEDER_AUTO_VOLTS = 8.0;

    public static final double SHOOTER_RPM = 4500;
    public static final double SHOOTER_RPM_CLOSERANGE = 3500; // NEEDS to be smaller
    public static final double SHOOTER_LOB_RPM = 3000; // NEEDS to be smaller
    public static final double SHOOTER_IDLE_SHOOTING_RPM = 3000; // NEEDS to be smaller
    public static final double SHOOTER_IDLE_RPM = 2500; // 2500
    public static final double SHOOTER_IDLE_RPM_CLOSE = 4200; // 2500
    public static final double SHOOTER_IDLE_CLOSERANGE_RPM = 2500; // NEEDS to be smaller
    public static final double SPIN_RATIO = 0.66; // 0.85
    public static final double ANTI_SPIN_RATIO = 1.34; // 0.85
    public static final int SHOOTER_AMP_RPM = 550;
  }
}
