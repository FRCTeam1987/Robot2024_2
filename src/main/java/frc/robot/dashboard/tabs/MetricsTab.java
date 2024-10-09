package frc.robot.dashboard.tabs;

import static frc.robot.RobotContainer.AMP_SENSORS;
import static frc.robot.RobotContainer.ELEVATOR;
import static frc.robot.RobotContainer.INTAKE;
import static frc.robot.RobotContainer.SHOOTER;
import static frc.robot.RobotContainer.WRIST;
import static frc.robot.RobotContainer.getLocalizationState;
import static frc.robot.RobotContainer.tracker;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.dashboard.TabUtil;

/** Add your docs here. */
public class MetricsTab {
    private final ShuffleboardTab tab;

    public MetricsTab() {
        tab = TabUtil.createTab("METRICS");
        tab.addBoolean("Rear LB", SHOOTER::isRearBroken);
        tab.addBoolean("Center LB", SHOOTER::isCenterBroken);
        tab.addDouble("Left RPM", SHOOTER::getRPMLeader);
        tab.addDouble("Right RPM", SHOOTER::getRPMFollower);
        tab.addDouble("Feeder RPM", SHOOTER::getRPMFeeder);
        tab.addBoolean("Amp L", AMP_SENSORS::getSensorLeft);
        tab.addBoolean("Amp R", AMP_SENSORS::getSensorRight);
        tab.addDouble("Wrist Deg", WRIST::getDegrees);
        tab.addDouble("Elev Len", ELEVATOR::getLengthInches);
        tab.addDouble("Int Top RPM", INTAKE::getRPMTop);
        tab.addDouble("Int Bot RPM", INTAKE::getRPMBottom);
        tab.addString("Zone", () -> getLocalizationState().fieldZone().toString());
        tab.addDouble("Distance Driven", tracker::getDistanceDriven);
    }
}
