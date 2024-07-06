package frc.robot.dashboard.tabs;

import static frc.robot.RobotContainer.AMP_SENSORS;
import static frc.robot.RobotContainer.ELEVATOR;
import static frc.robot.RobotContainer.INTAKE;
import static frc.robot.RobotContainer.SHOOTER;
import static frc.robot.RobotContainer.WRIST;
import static frc.robot.RobotContainer.getDriveMode;
import static frc.robot.RobotContainer.getLocalizationState;
import static frc.robot.RobotContainer.getRobotState;
import static frc.robot.RobotContainer.getScoreMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.actions.AimAtSpeaker;
import frc.robot.commands.auto.actions.AutoCollectNote;
import frc.robot.dashboard.TabUtil;

/** Add your docs here. */
public class MetricsTab {
    private final ShuffleboardTab tab;

    public MetricsTab() {
        tab = TabUtil.createTab("METRICS");
        tab.addBoolean("Rear LB", () -> RobotContainer.SHOOTER.isRearBroken());
        tab.addBoolean("Center LB", () -> RobotContainer.SHOOTER.isCenterBroken());
        tab.addDouble("Left RPM", () -> SHOOTER.getRPMLeader());
        tab.addDouble("Right RPM", () -> SHOOTER.getRPMFollower());
        tab.addDouble("Feeder RPM", () -> SHOOTER.getRPMFeeder());
        tab.addBoolean("Amp L", () -> AMP_SENSORS.getSensorLeft());
        tab.addBoolean("Amp R", () -> AMP_SENSORS.getSensorRight());
        tab.addDouble("Wrist Deg", () -> WRIST.getDegrees());
        tab.addDouble("Elev Len", () -> ELEVATOR.getLengthInches());
        tab.addDouble("Int Top RPM", () -> INTAKE.getRPMTop());
        tab.addDouble("Int Bot RPM", () -> INTAKE.getRPMBottom());
        tab.addString("Zone", () -> getLocalizationState().getFieldZone().toString());
    }
}
