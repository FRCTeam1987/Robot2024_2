// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stateless;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RumbleLock extends Command {
  /** Creates a new RumbleLock. */
  private final XboxController CONTROLLER;
  private final BooleanSupplier CONDITIONAL;
  private final Trigger BUTTON;
  private final Runnable FINISH;
  private boolean isFinished = false;

  public RumbleLock(XboxController CONTROLLER, Trigger BUTTON, BooleanSupplier CONDITIONAL, Runnable FINISH) {
    this.CONTROLLER = CONTROLLER;
    this.CONDITIONAL = CONDITIONAL;
    this.BUTTON = BUTTON;
    this.FINISH = FINISH;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (BUTTON.getAsBoolean()) {
      if (CONDITIONAL.getAsBoolean()) {
        FINISH.run();
        isFinished = true;
      } else {
        // help me
        CommandScheduler.getInstance().schedule(new AsyncRumble(CONTROLLER, RumbleType.kBothRumble, 1.0, 400L));
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
