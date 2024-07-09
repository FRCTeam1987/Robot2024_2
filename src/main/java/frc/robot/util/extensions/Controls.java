package frc.robot.util.extensions;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.defaults.DefaultSwerve;
import frc.robot.commands.teleop.logic.RobotState;
import frc.robot.commands.teleop.logic.ScoreMode;
import frc.robot.commands.teleop.stated.AmpNoteState;
import frc.robot.commands.teleop.stated.ClimbState;
import frc.robot.commands.teleop.stated.DefaultState;
import frc.robot.commands.teleop.stated.FastSub;
import frc.robot.commands.teleop.stated.IntakeNoteState;
import frc.robot.commands.teleop.stated.PassNoteState;
import frc.robot.commands.teleop.stated.PodiumState;
import frc.robot.commands.teleop.stated.PoopNoteState;
import frc.robot.commands.teleop.stated.ShootNoteState;
import frc.robot.commands.teleop.stateless.AsyncRumble;
import frc.robot.commands.teleop.stateless.ReLocalizeSub;
import frc.robot.commands.teleop.stateless.recovery.AutoHomeElevator;
import frc.robot.commands.teleop.stateless.recovery.AutoHomeWrist;
import frc.robot.commands.teleop.stateless.recovery.ForceZeroAll;
import frc.robot.commands.teleop.stateless.recovery.ReverseIntake;
import frc.robot.util.InstCmd;
import frc.robot.util.zoning.FieldZones;
import frc.robot.util.zoning.FieldZones.Zone;

public class Controls extends RobotContainer {

        public static void configureDriverController() {
                DRIVETRAIN.setDefaultCommand( // Drivetrain will execute this command periodically
                                new DefaultSwerve());
                DRIVER_CONTROLLER
                                .rightBumper()
                                .onTrue(
                                                new ConditionalCommand(new ShootNoteState(),
                                                                new AsyncRumble(DRIVER_CONTROLLER.getHID(),
                                                                                RumbleType.kBothRumble, 1.0, 400L),
                                                                () -> {
                                                                        final FieldZones.Zone zone = getLocalizationState().fieldZone();
                                                                        return SHOOTER.isCenterBroken()
                                                                                && getRobotState() == RobotState.DEFAULT
                                                                                && getScoreMode() == ScoreMode.SPEAKER
                                                                                && (zone == FieldZones.Zone.ALLIANCE_HOME
                                                                                        || zone == FieldZones.Zone.ALLIANCE_STAGE
                                                                                        || zone == FieldZones.Zone.ALLIANCE_WING
                                                                                );
                                                                }));
                DRIVER_CONTROLLER
                                .leftBumper().onTrue(
                                                new ConditionalCommand(
                                                                new IntakeNoteState(),
                                                                new AsyncRumble(DRIVER_CONTROLLER.getHID(),
                                                                                RumbleType.kBothRumble, 1.0, 400L),
                                                                () -> !SHOOTER.isCenterBroken()
                                                                                && getRobotState() == RobotState.DEFAULT));
                DRIVER_CONTROLLER.back().onTrue(new ReLocalizeSub());
                DRIVER_CONTROLLER.start().onTrue(new DefaultState());
                DRIVER_CONTROLLER.y().onTrue(
                                new ConditionalCommand(new InstCmd(), new AmpNoteState(),
                                                () -> getRobotState() == RobotState.AMP_PREP
                                                                || getRobotState() == RobotState.AMP_SCORE
                                                                || getRobotState() == RobotState.AMP_EXIT
                                                                || !SHOOTER.isCenterBroken()
                                                                                && getRobotState() == RobotState.DEFAULT
                                                                                && getScoreMode() == ScoreMode.AMP));
                // DRIVER_CONTROLLER.rightStick().onTrue(new InstCmd(() ->
                // setScoreMode(ScoreMode.AMP)));
                // DRIVER_CONTROLLER.leftStick().onTrue(new InstCmd(() ->
                // setScoreMode(ScoreMode.SPEAKER)));
                DRIVER_CONTROLLER.rightStick()
                                .onTrue(new InstCmd(() -> setScoreMode(ScoreMode.AMP)));
                DRIVER_CONTROLLER.leftStick()
                                .onTrue(new InstCmd(() -> setScoreMode(ScoreMode.SPEAKER)));

                new Trigger(DRIVER_CONTROLLER.leftStick()).and(DRIVER_CONTROLLER.rightStick())
                                .onTrue(new InstCmd(() -> setScoreMode(ScoreMode.DEFENSE)));

                DRIVER_CONTROLLER.x().onTrue(
                                new ConditionalCommand(new PoopNoteState(),
                                                new AsyncRumble(DRIVER_CONTROLLER.getHID(), RumbleType.kBothRumble, 1.0,
                                                                400L),
                                                () -> SHOOTER.isCenterBroken()
                                                                && getRobotState() == RobotState.DEFAULT));
                DRIVER_CONTROLLER.leftTrigger().onTrue(
                                new ConditionalCommand(new PassNoteState(),
                                                new AsyncRumble(DRIVER_CONTROLLER.getHID(), RumbleType.kBothRumble, 1.0,
                                                                400L),
                                                () -> SHOOTER.isCenterBroken()
                                                                && getRobotState() == RobotState.DEFAULT
                                                                && getLocalizationState()
                                                                                .fieldZone() != Zone.ALLIANCE_WING
                                                                && getLocalizationState()
                                                                                .fieldZone() != Zone.OPPONENT_STAGE
                                                                && getLocalizationState()
                                                                                .fieldZone() != Zone.OPPONENT_WING
                                                                && getLocalizationState()
                                                                                .fieldZone() != Zone.ALLIANCE_STAGE
                                                                && getLocalizationState()
                                                                                .fieldZone() != Zone.ALLIANCE_HOME));
                DRIVER_CONTROLLER.b().onTrue(
                                new ConditionalCommand(new FastSub(),
                                                new AsyncRumble(DRIVER_CONTROLLER.getHID(), RumbleType.kBothRumble, 1.0,
                                                                400L),
                                                () -> SHOOTER.isCenterBroken()
                                                                && getRobotState() == RobotState.DEFAULT
                                                                && getScoreMode() == ScoreMode.SPEAKER));
                DRIVER_CONTROLLER.a().onTrue(
                                new ConditionalCommand(new PodiumState(),
                                                new AsyncRumble(DRIVER_CONTROLLER.getHID(), RumbleType.kBothRumble, 1.0,
                                                                400L),
                                                () -> SHOOTER.isCenterBroken() && STATE == RobotState.DEFAULT
                                                                && getScoreMode() == ScoreMode.SPEAKER));

        }

        public static void configureCoDriverController() {
                CODRIVER_CONTROLLER.y().and(CODRIVER_CONTROLLER.leftTrigger()).and(CODRIVER_CONTROLLER.rightTrigger())
                                .onTrue(
                                                new ConditionalCommand(new ClimbState(), new InstCmd(),
                                                                () -> getRobotState() == RobotState.DEFAULT));
                CODRIVER_CONTROLLER.rightBumper()
                                .onTrue(new InstCmd(() -> setScoreMode(ScoreMode.AMP)));
                CODRIVER_CONTROLLER.leftBumper()
                                .onTrue(new InstCmd(() -> setScoreMode(ScoreMode.SPEAKER)));
                new Trigger(CODRIVER_CONTROLLER.rightBumper()).and(CODRIVER_CONTROLLER.leftBumper())
                                .onTrue(new InstCmd(() -> setScoreMode(ScoreMode.DEFENSE)));
                CODRIVER_CONTROLLER.povDown().onTrue(new InstCmd(() -> setScoreMode(ScoreMode.DEFENSE)));
                CODRIVER_CONTROLLER.a().onTrue(new ReverseIntake());
                CODRIVER_CONTROLLER.b().onTrue(new AutoHomeWrist());
                CODRIVER_CONTROLLER.x().onTrue(new AutoHomeElevator());
                CODRIVER_CONTROLLER.start().onTrue(new ForceZeroAll());
        }
}
