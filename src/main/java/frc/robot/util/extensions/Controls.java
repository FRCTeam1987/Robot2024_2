package frc.robot.util.extensions;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.defaults.DefaultSwerve;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.commands.teleop.stated.AmpNoteState;
import frc.robot.commands.teleop.stated.DefaultState;
import frc.robot.commands.teleop.stated.IntakeNoteState;
import frc.robot.commands.teleop.stated.PassNoteState;
import frc.robot.commands.teleop.stated.PodiumState;
import frc.robot.commands.teleop.stated.PoopNoteState;
import frc.robot.commands.teleop.stated.ShootNoteState;
import frc.robot.commands.teleop.stated.SubwooferState;
import frc.robot.commands.teleop.stateless.ReLocalizeSub;
import frc.robot.util.InstCmd;

public class Controls extends RobotContainer {

  public static void configureDriverController() {
    DRIVETRAIN.setDefaultCommand( // Drivetrain will execute this command periodically
        new DefaultSwerve());
    DRIVER_CONTROLLER
        .rightBumper()
        .onTrue(
            new ConditionalCommand(new ShootNoteState(), new InstCmd(),
                () -> SHOOTER.isCenterBroken() && STATE == RobotState.DEFAULT));
    DRIVER_CONTROLLER
        .leftBumper().onTrue(
            new ConditionalCommand(new IntakeNoteState(), new InstCmd(),
                () -> !SHOOTER.isRearBroken() && STATE == RobotState.DEFAULT));
    DRIVER_CONTROLLER.back().onTrue(new ReLocalizeSub());
    DRIVER_CONTROLLER.start().onTrue(new DefaultState());
    DRIVER_CONTROLLER.y().onTrue(
        new ConditionalCommand(new InstCmd(), new AmpNoteState(),
            () -> getRobotState() == RobotState.AMP_PREP
                || getRobotState() == RobotState.AMP_SCORE
                || getRobotState() == RobotState.AMP_EXIT
                || !SHOOTER.isCenterBroken()
                    && STATE == RobotState.DEFAULT));
    DRIVER_CONTROLLER.rightStick().onTrue(new InstCmd(() -> setScoreMode(ScoreMode.AMP)));
    DRIVER_CONTROLLER.leftStick().onTrue(new InstCmd(() -> setScoreMode(ScoreMode.SPEAKER)));
    DRIVER_CONTROLLER.x().onTrue(
        new ConditionalCommand(new PoopNoteState(), new InstCmd(),
            () -> SHOOTER.isCenterBroken() && STATE == RobotState.DEFAULT));
    DRIVER_CONTROLLER.leftTrigger().onTrue(
        new ConditionalCommand(new PassNoteState(), new InstCmd(),
            () -> SHOOTER.isCenterBroken() && STATE == RobotState.DEFAULT));
    DRIVER_CONTROLLER.b().onTrue(
        new ConditionalCommand(new SubwooferState(), new InstCmd(),
            () -> SHOOTER.isCenterBroken() && STATE == RobotState.DEFAULT));
    DRIVER_CONTROLLER.a().onTrue(
        new ConditionalCommand(new PodiumState(), new InstCmd(),
            () -> SHOOTER.isCenterBroken() && STATE == RobotState.DEFAULT));
  }

  public static void configureCoDriverController() {

  }
}
