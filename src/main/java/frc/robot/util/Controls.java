package frc.robot.util;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.statechangers.StateShootNote;
import frc.robot.commands.stateless.SwerveCommand;

public class Controls extends RobotContainer {
    
  public static void configureDriverController() {
    RobotContainer.DRIVETRAIN.setDefaultCommand( // Drivetrain will execute this command periodically
    new SwerveCommand(
            DRIVETRAIN,
            drive,
            () -> -Constants.Limiters.TRANSLATION_X_SLEW_RATE.calculate(DRIVER_CONTROLLER.getLeftY()),
            () -> -Constants.Limiters.TRANSLATION_Y_SLEW_RATE.calculate(DRIVER_CONTROLLER.getLeftX()),
            () -> DRIVER_CONTROLLER.getRightX(),
            () -> DRIVER_CONTROLLER.getHID().getPOV())
    );
    DRIVER_CONTROLLER
      .rightBumper()
        .onTrue(new StateShootNote());
  }

  public static void configureCoDriverController() {

  }
}
