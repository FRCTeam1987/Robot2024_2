package frc.robot.commands.teleop.defaults;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.logic.DriveMode;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.generated.TunerConstants;
import frc.robot.util.InstCmd;
import frc.robot.util.Util;
import frc.robot.util.zoning.FieldZones;
import frc.robot.util.zoning.LocalizationState;
import frc.robot.util.zoning.FieldZones.Zone;

import static frc.robot.RobotContainer.*;

/**
 * This command, when executed, instructs the drivetrain subsystem to drive
 * based on the specified
 * values from the controller(s). This command is designed to be the default
 * command for the
 * drivetrain subsystem.
 *
 * <p>
 * Requires: the Drivetrain subsystem
 *
 * <p>
 * Finished When: never
 *
 * <p>
 * At End: stops the drivetrain
 */
public class DefaultSwerve extends Command {

  private HolonomicDriveController HOLONOMIC_CONTROLLER;
  private FieldZones.Zone prevZone = FieldZones.Zone.ALLIANCE_WING;

  public DefaultSwerve() {
    this.HOLONOMIC_CONTROLLER = new HolonomicDriveController(new PIDController(1, 0, 0), new PIDController(1.0, 0, 0),
        new ProfiledPIDController(9.0, 0, 0,
            new TrapezoidProfile.Constraints(TunerConstants.kSpeedAt12VoltsMps, 3.8)));
    addRequirements(DRIVETRAIN);
  }

  @Override
  public void initialize() {
    new Trigger(() -> getScoreMode() == ScoreMode.DEFENSE)
        .onTrue(new InstCmd(() -> {
          // DRIVETRAIN.configDriveAmps(Constants.Drive.DRIVE_MOTOR_AMPS_DEFENSE);
          // DriverStation.reportWarning("==== increase amps", false);
          SmartDashboard.putString("amp limit", "defense");
        }))
        .onFalse(new InstCmd(() -> {
          // DRIVETRAIN.configDriveAmps(Constants.Drive.DRIVE_MOTOR_AMPS_NORMAL);
          // DriverStation.reportWarning("==== decrease amps", false);
          SmartDashboard.putString("amp limit", "normal");
        }));
  }

  @Override
  public void execute() {
    final double X_PERCENTAGE = Util
        .squareValue(Constants.Limiters.TRANSLATION_X_SLEW_RATE.calculate(-DRIVER_CONTROLLER.getLeftY()))
        * TunerConstants.kSpeedAt12VoltsMps;
    final double Y_PERCENTAGE = Util
        .squareValue(Constants.Limiters.TRANSLATION_Y_SLEW_RATE.calculate(-DRIVER_CONTROLLER.getLeftX()))
        * TunerConstants.kSpeedAt12VoltsMps;
    double ROT;
    double AUTO_ROT = 0.0;
    double CARDINAL_ROT = 0.0;

    if (Math.abs(X_PERCENTAGE) < 0.01 && Math.abs(Y_PERCENTAGE) < 0.01 && Math.abs(DRIVER_CONTROLLER.getRightX()) < 0.01
        && getScoreMode() == ScoreMode.DEFENSE) {
      DRIVETRAIN.setControl(RobotContainer.brake);
      return;
    }

    final LocalizationState LOCAL_STATE = RobotContainer.getLocalizationState();

    switch (RobotContainer.getLocalizationState().fieldZone()) {
      case ALLIANCE_WING:
      case ALLIANCE_STAGE:
      case ALLIANCE_HOME:
        if (SHOOTER.hasNote()) {
          if (prevZone != Zone.ALLIANCE_WING && prevZone != Zone.ALLIANCE_STAGE && prevZone != Zone.ALLIANCE_HOME)
            setDriveMode(DriveMode.AUTOMATIC);
        }
        switch (RobotContainer.getScoreMode()) {
          case SPEAKER:
            AUTO_ROT = getRPS(LOCAL_STATE.speakerAngle());
            break;
          case AMP:
            AUTO_ROT = getRPS(new Rotation2d(Math.toRadians(90.0)));
            break;
          default:
            break;
        }
        break;
      case OPPONENT_HOME:
        AUTO_ROT = getRPS(LOCAL_STATE.centerPassAngle());
        break;
      case OPPONENT_STAGE:
      case OPPONENT_WING:
      case ALLIANCE_YIELD:
      case OPPONENT_YIELD:
      case NEUTRAL_WING:
        if (SHOOTER.hasNote()) {
          if (prevZone != Zone.OPPONENT_STAGE && prevZone != Zone.OPPONENT_WING && prevZone != Zone.ALLIANCE_YIELD
              && prevZone != Zone.NEUTRAL_WING)
            setDriveMode(DriveMode.AUTOMATIC);
        }
        // if (prevZone == FieldZones.Zone.OPPONENT_WING
        // || prevZone == FieldZones.Zone.OPPONENT_STAGE || prevZone !=
        // FieldZones.Zone.OPPONENT_HOME)
        AUTO_ROT = getRPS(LOCAL_STATE.ampPassAngle());
        break;
      default:
        break;
    }

    if (DRIVER_CONTROLLER.getHID().getPOV() != -1) {
      setDriveMode(DriveMode.CARDINAL_LOCKING);
      CARDINAL_ROT = getRPS(new Rotation2d(Math.toRadians(calculateSetpoint(DRIVER_CONTROLLER.getHID().getPOV()))));
    }

    if (DRIVER_CONTROLLER.getRightTriggerAxis() > 0.2)
      setDriveMode(DriveMode.AUTOMATIC);

    if (Math.abs(DRIVER_CONTROLLER.getRightX()) > 0.1) {
      setDriveMode(DriveMode.MANUAL);
    }

      ROT = switch (getDriveMode()) {
          case AUTOMATIC -> AUTO_ROT;
          // ROT = Util.squareValue(-DRIVER_CONTROLLER.getRightX()) * Math.PI * 3.5;
          case CARDINAL_LOCKING -> CARDINAL_ROT;
          default -> Util.squareValue(-DRIVER_CONTROLLER.getRightX()) * Math.PI * 3.5;
      };

    DRIVETRAIN
        .setControl(drive.withVelocityX(X_PERCENTAGE).withVelocityY(Y_PERCENTAGE).withRotationalRate(ROT));

    prevZone = LOCAL_STATE.fieldZone();
  }

  @Override
  public void end(boolean interrupted) {

  }

  public double getRPS(Rotation2d setpoint) {
    return HOLONOMIC_CONTROLLER.calculate(DRIVETRAIN.getPose(),
        DRIVETRAIN.getPose(), 0.0, setpoint).omegaRadiansPerSecond;
  }

  public int calculateSetpoint(int point) {
      return switch (point) {
          case 0 -> 180;
          case 90 -> 90;
          case 180 -> 0;
          case 270 -> -90;
          default -> 0;
      };
  }
}
