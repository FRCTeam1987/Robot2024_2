package frc.robot.util;

import frc.robot.RobotContainer;

public class Controls extends RobotContainer {
    
  public static void configureDriverController() {
    // DRIVER_CONTROLLER.b().onTrue(new ShootSubwooferFlat(ELEVATOR, WRIST, SHOOTER));

    // // DRIVER_CONTROLLER
    // //     .y()
    // //     .onTrue(
    // //         new ConditionalCommand(
    // //             new GoHome(ELEVATOR, WRIST, SHOOTER, INTAKE)
    // //                 .andThen(() -> isReverseAmpPrimed = false),
    // //             new PrepRevAmp(ELEVATOR, WRIST)
    // //                 .andThen(new WaitCommand(0.8))
    // //                 .andThen(new FireRevAmp(SHOOTER))
    // //                 .andThen(new WaitCommand(0.1))
    // //                 .andThen(new InstantCommand(() -> ELEVATOR.setLengthInches(4.2)))
    // //                 .andThen(new InstantCommand(() -> isReverseAmpPrimed = true)),
    // //             () -> isReverseAmpPrimed));

    // DRIVER_CONTROLLER
    //     .y()
    //     .onTrue(
    //         new ConditionalCommand(
    //             // Compare prepped to not prepped (If prepped, try shoot. Else, prep)
    //             new ConditionalCommand(
    //                 // Compare shot to not shot (If shot, go home. If not, try shot)
    //                 new GoHome(ELEVATOR, WRIST, SHOOTER, INTAKE)
    //                     .andThen(
    //                         () -> {
    //                           isAmpPrepped = false;
    //                           isAmpShot = false;
    //                         }),
    //                 new ConditionalCommand(
    //                     // Compare working shot (If working, shoot. Else rumble.)
    //                     new FireRevAmp(SHOOTER)
    //                         .andThen(new WaitCommand(0.1))
    //                         .andThen(new InstantCommand(() -> ELEVATOR.setLengthInches(4.2)))
    //                         .andThen(new InstantCommand(() -> isAmpShot = true)),
    //                     new AsyncRumble(
    //                         DRIVER_CONTROLLER.getHID(), RumbleType.kBothRumble, 1.0, 400L),
    //                     () -> AMP_SENSORS.getBothSensors()),
    //                 () -> isAmpShot),
    //             new PrepRevAmp(ELEVATOR, WRIST)
    //                 .andThen(new InstantCommand(() -> isAmpPrepped = true)),
    //             () -> isAmpPrepped));
    // DRIVER_CONTROLLER
    //     .back()
    //     .onTrue(
    //         DRIVETRAIN
    //             .runOnce(
    //                 () -> {
    //                   DRIVETRAIN.seedFieldRelative();
    //                   DRIVETRAIN.getPigeon2().reset();
    //                 })
    //             .andThen(new InstantCommand(() -> updatePoseVision(0.01, false))));
    // // DRIVER_CONTROLLER.back().onTrue(new InstantCommand(() -> updatePoseVision(0.01, false)));
    // DRIVER_CONTROLLER
    //     .start()
    //     .onTrue(
    //         new GoHome(ELEVATOR, WRIST, SHOOTER, INTAKE)
    //             .andThen(new InstantCommand(() -> WRIST.goHome())));
    // DRIVER_CONTROLLER.x().onTrue(new PoopNote(SHOOTER, 500));
    // DRIVER_CONTROLLER
    //     .leftBumper()
    //     .onTrue(
    //         new IntakeNoteSequence(SHOOTER, INTAKE, WRIST, ELEVATOR)
    //             .andThen(
    //                 new AsyncRumble(DRIVER_CONTROLLER.getHID(), RumbleType.kBothRumble, 1.0, 700L))
    //         // No instantcommand wrapper?
    //         )
    //     .whileTrue(new InstantCommand(() -> enableTeleopPointToNote()))
    //     .onFalse(new InstantCommand(() -> disableTeleopPointToNote()));

    // DRIVER_CONTROLLER
    //     .rightTrigger(0.2)
    //     .whileTrue(
    //         new PointAtSpeaker(
    //             DRIVETRAIN,
    //             () -> -TranslationXSlewRate.calculate(DRIVER_CONTROLLER.getLeftY()),
    //             () -> -TranslationYSlewRate.calculate(DRIVER_CONTROLLER.getLeftX()),
    //             () -> DRIVER_CONTROLLER.getRightX(),
    //             () -> false,
    //             () -> false));
    // DRIVER_CONTROLLER
    //     .rightBumper()
    //     .onTrue(new ShootNote(SHOOTER, ELEVATOR, Constants.Shooter.SHOOTER_RPM));
    // // DRIVER_CONTROLLER.leftTrigger(0.1).onTrue(new LobNote(SHOOTER, WRIST, ELEVATOR));

    // DRIVER_CONTROLLER
    //     .leftTrigger(0.2)
    //     .whileTrue(
    //         new ParallelDeadlineGroup(
    //             new SequentialCommandGroup(
    //                 new WaitUntilCommand(
    //                     () ->
    //                         DRIVER_CONTROLLER.getHID().getLeftTriggerAxis() > 0.90
    //                             && Util.isPointedAtLob(DRIVETRAIN)),
    //                 new LobNote(SHOOTER, WRIST, ELEVATOR, lobRPM)),
    //             new PointAtSpeaker(
    //                 DRIVETRAIN,
    //                 () -> -TranslationXSlewRate.calculate(DRIVER_CONTROLLER.getLeftY()),
    //                 () -> -TranslationYSlewRate.calculate(DRIVER_CONTROLLER.getLeftX()),
    //                 () -> DRIVER_CONTROLLER.getRightX(),
    //                 () -> true,
    //                 () -> false

    //                 // Other logic for aiming etc
    //                 )));

    // // WIP
    // // DRIVER_CONTROLLER
    // //     .leftTrigger()
    // //     .onTrue(
    // //         new ParallelDeadlineGroup(
    // //                 new ParallelCommandGroup(
    // //                     new WaitUntilCommand(
    // //                         () -> {
    // //                           Pose2d currentPose = DRIVETRAIN.getPose();
    // //                           Rotation2d currentRotation = currentPose.getRotation();
    // //                           DriverStation.reportWarning(
    // //                               "current: "
    // //                                   + currentRotation.getDegrees()
    // //                                   + ", target: "
    // //                                   + Util.getRotationToAllianceLob(currentPose).getDegrees(),
    // //                               false);
    // //                           return Util.isWithinTolerance(
    // //                               currentRotation.getDegrees(),
    // //                               Util.getRotationToAllianceLob(currentPose).getDegrees(),
    // //                               30);
    // //                         }),
    // //                     new PrintCommand("STARTING LOB SEQUENCE")),
    // //                 new PointAtAprilTag(
    // //                     DRIVETRAIN,
    // //                     () -> -TranslationXSlewRate.calculate(DRIVER_CONTROLLER.getLeftY()),
    // //                     () -> -TranslationYSlewRate.calculate(DRIVER_CONTROLLER.getLeftX()),
    // //                     () -> DRIVER_CONTROLLER.getRightX(),
    // //                     true))
    // //             .andThen(new LobNote(SHOOTER, WRIST, ELEVATOR)))
    // //     .onFalse(new InstantCommand());
  }

  public static void configureCoDriverController() {

    // CO_DRIVER_CONTROLLER
//     //     .leftBumper()
//     //     .onTrue(Util.pathfindToPose(Util.findNearestPoseToTrapClimbs(getPose())));
//     CO_DRIVER_CONTROLLER.start().onTrue(new StopAll(WRIST, SHOOTER, INTAKE, ELEVATOR));
//     CO_DRIVER_CONTROLLER.rightBumper().onTrue(new PoopNote(SHOOTER, 2500));

//     CO_DRIVER_CONTROLLER
//         .y()
//         .onTrue(
//             new ConditionalCommand(
//                 new ConditionalCommand(
//                     new Climb(ELEVATOR, WRIST, SHOOTER),
//                     new InstantCommand(
//                             () -> {
//                               ELEVATOR.setLengthInches(Constants.Climb.CLIMB_START_HEIGHT);
//                               WRIST.goHome();
//                             },
//                             ELEVATOR,
//                             WRIST)
//                         .andThen(() -> isClimbPrimed = true),
//                     () -> isClimbPrimed),
//                 new InstantCommand(),
//                 () -> DriverStation.getMatchTime() < 45.0));

//     CO_DRIVER_CONTROLLER.x().onTrue(new ReverseIntake(SHOOTER, INTAKE, WRIST, ELEVATOR));
//     CO_DRIVER_CONTROLLER.leftTrigger().onTrue(new ShootTall(ELEVATOR, WRIST, SHOOTER));
//     CO_DRIVER_CONTROLLER
//         .b()
//         .onTrue(
//             new InstantCommand(
//                     () -> {
//                       SHOOTER.setFeederVoltage(Constants.Shooter.FEEDER_FEEDFWD_VOLTS);
//                       INTAKE.setVolts(Constants.INTAKE_COLLECT_VOLTS);
//                     })
//                 .andThen(new WaitCommand(0.1))
//                 .andThen(
//                     new InstantCommand(
//                         () -> {
//                           SHOOTER.stopFeeder();
//                           INTAKE.stopTop();
//                           INTAKE.stopCollecting();
//                         })));

//     CO_DRIVER_CONTROLLER.rightTrigger().onTrue(new ShootSubwooferFlat(ELEVATOR, WRIST, SHOOTER));
//     CO_DRIVER_CONTROLLER
//         .a()
//         .onTrue(
//             new ParallelDeadlineGroup(
//                 new IntakeNoteSequence(SHOOTER, INTAKE, WRIST, ELEVATOR),
//                 new DriveToNoteAuto(DRIVETRAIN, INTAKE_PHOTON, SHOOTER, INTAKE, WRIST, ELEVATOR)));

//     CO_DRIVER_CONTROLLER
//         .leftStick()
//         .onTrue(
//             new InstantCommand(
//                 () -> {
//                   if (CommandSwerveDrivetrain.getAlliance() == Alliance.Blue) {
//                     DRIVETRAIN.seedFieldRelative(
//                         new Pose2d(1.37, 5.52, Rotation2d.fromDegrees(0.0)));
//                   } else {
//                     DRIVETRAIN.seedFieldRelative(
//                         new Pose2d(15.2, 5.5, Rotation2d.fromDegrees(-180)));
//                   }
//                 }));
//   }

//   private void configureDrivetrain() {

//     DRIVETRAIN.setDefaultCommand( // Drivetrain will execute this command periodically
//         new SwerveCommand(
//             DRIVETRAIN,
//             drive,
//             () -> -TranslationXSlewRate.calculate(DRIVER_CONTROLLER.getLeftY()),
//             () -> -TranslationYSlewRate.calculate(DRIVER_CONTROLLER.getLeftX()),
//             () -> DRIVER_CONTROLLER.getRightX(),
//             () -> DRIVER_CONTROLLER.getHID().getPOV()));

//     // DRIVETRAIN.setDefaultCommand( // Drivetrain will execute this command periodically
//     //     DRIVETRAIN
//     //         .applyRequest(
//     //             () ->
//     //                 drive
//     //                     .withVelocityX(
//     //                         Util.squareValue(-DRIVER_CONTROLLER.getLeftY())
//     //                             * DriveConstants.kSpeedAt12VoltsMps) // Drive forward with
//     //                     // negative Y (forward)
//     //                     .withVelocityY(
//     //                         Util.squareValue(-DRIVER_CONTROLLER.getLeftX())
//     //                             * DriveConstants
//     //                                 .kSpeedAt12VoltsMps) // Drive left with negative X (left)
//     //                     .withRotationalRate(
//     //                         Util.squareValue(-DRIVER_CONTROLLER.getRightX())
//     //                             * Math.PI
//     //                             * 3.5) // Drive counterclockwise with negative X (left)
//     //             )
//     //         .ignoringDisable(true));

//     // DRIVETRAIN.setDefaultCommand(
//     //     new TeleopSwerve(
//     //         DRIVETRAIN,
//     //         () -> -DRIVER_CONTROLLER.getLeftY(),
//     //         () -> -DRIVER_CONTROLLER.getLeftX(),
//     //         DRIVER_CONTROLLER::getRightX,
//     //         () -> 1.0,
//     //         () -> DRIVER_CONTROLLER.getHID().getPOV(),
//     //         () -> false));
  }
}
