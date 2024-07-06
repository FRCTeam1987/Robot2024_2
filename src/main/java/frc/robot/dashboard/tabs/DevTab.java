package frc.robot.dashboard.tabs;

import static frc.robot.RobotContainer.getDriveMode;
import static frc.robot.RobotContainer.getLocalizationState;
import static frc.robot.RobotContainer.getRobotState;
import static frc.robot.RobotContainer.getScoreMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.actions.AimAtSpeaker;
import frc.robot.dashboard.TabUtil;

/** Add your docs here. */
public class DevTab {
    private final ShuffleboardTab tab;
    private GenericEntry SHOOTER_RPM;

    public DevTab() {
        tab = TabUtil.createTab("DEV");
        SHOOTER_RPM = tab.add("SHOOTER_RPM", 300).getEntry();
        tab.addDouble("DIST_TO_AMP", () -> getLocalizationState().getAmpPassDistance());
        tab.addString("State", () -> getRobotState() + " " + getScoreMode() + " " + getDriveMode());
        tab.addBoolean("Rear LB", () -> RobotContainer.SHOOTER.isRearBroken());
        tab.addBoolean("Center LB", () -> RobotContainer.SHOOTER.isCenterBroken());
        tab.add(new AimAtSpeaker());
    }

    public double getRPM() {
        return SHOOTER_RPM.getDouble(325.0);
    }
}
