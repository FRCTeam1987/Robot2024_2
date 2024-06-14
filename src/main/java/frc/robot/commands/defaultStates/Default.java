// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaultStates;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class Default extends Command {
  /** Creates a new Default. */
  public Default() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public final void execute() {
      switch(RobotContainer.STATE) {
        case COLLECTING:
          collecting();
          break;
        case COLLECT_PREP:
          collect_prep();
          break;
        case POOPING:
          pooping();
          break;
        case POOP_PREP:
          poop_prep();
          break;
        case SHOOTING:
          shooting();
          break;
        case SHOOT_PREP:
          shoot_prep();
          break;
        case DEFAULT:
          defaulting();
          break;
      }
  }


  public void pooping() {

  }

  public void poop_prep() {

  }

  public void shooting() {
    
  }

  public void shoot_prep() {

  }

  public void collecting() {

  }

  public void collect_prep() {

  }

  public void defaulting() { //on a loan!

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
