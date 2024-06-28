// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.stateless;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;

public class MultiButton extends Command {
  static boolean isRunning;
  BooleanSupplier buttonA;
  BooleanSupplier buttonB;
  boolean buttonALatch = false;
  boolean buttonBLatch = false;
  boolean isFinished = false;
  Command runA;
  Command runB;
  Command runBoth;

  /** Creates a new MultiButton. */
  public MultiButton(BooleanSupplier buttonA, BooleanSupplier buttonB, Command runA, Command runB, Command runBoth) {
    this.buttonA = buttonA;
    this.buttonB = buttonB;
    this.runA = runA;
    this.runB = runB;
    this.runBoth = runBoth;
    buttonALatch = false;
    buttonBLatch = false;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the Command is initially scheduled.
  @Override
  public void initialize() {
    if (isRunning) {
      isFinished = true;
    } else {
      isRunning = true;
    }

  }

  // Called every time the scheduler runs while the Command is scheduled.
  @Override
  public void execute() {
    if (buttonA.getAsBoolean()) {
      buttonALatch = true;
    }
    if (buttonB.getAsBoolean()) {
      buttonBLatch = true;
    }
    if (buttonALatch && buttonBLatch) {
      runBoth.schedule();
      isFinished = true;
      return;
    }
    if (!buttonA.getAsBoolean() && buttonALatch && !buttonBLatch) {
      runA.schedule();
      isFinished = true;
      return;
    }
    if (!buttonB.getAsBoolean() && buttonBLatch && !buttonALatch) {
      runB.schedule();
      isFinished = true;
      return;
    }
  }

  // Called once the Command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isRunning = false;
  }

  // Returns true when the Command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
