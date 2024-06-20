package frc.robot.util.extensions;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.defaults.DefaultSwerve;
import frc.robot.commands.teleop.stated.IntakeNoteState;
import frc.robot.commands.teleop.stated.ShootNoteState;
import frc.robot.commands.teleop.stateless.SubLocalize;

public class Controls extends RobotContainer {
    
  public static void configureDriverController() {
    RobotContainer.DRIVETRAIN.setDefaultCommand( // Drivetrain will execute this command periodically
    new DefaultSwerve()
    );
    DRIVER_CONTROLLER
      .rightBumper()
        .onTrue(new ShootNoteState());
    DRIVER_CONTROLLER
      .leftBumper().onTrue(new IntakeNoteState());
    DRIVER_CONTROLLER.back().onTrue(new SubLocalize());
  }

  public static void configureCoDriverController() {

  }
}
