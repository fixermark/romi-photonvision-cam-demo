// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.RomiDrivetrain;

import java.util.OptionalDouble;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Tracks a target seen by a camera. */
public class TrackTarget extends CommandBase {
  private final PhotonVision m_camera;
  private final RomiDrivetrain m_drivetrain;
  
  private static final double DEADZONE_DEGREES = 2.0;  
  private static final double POWER = 0.015;

  /**
   * Creates a new TrackTarget command.
   *
   * @param camera Camera subsystem to track
   * @param drivetrain Drivetrain to control
   */
  public TrackTarget(PhotonVision camera, RomiDrivetrain drivetrain) {
    m_camera = camera;
    m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain, m_camera);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    OptionalDouble maybeTarget = m_camera.getYaw();
    var error = maybeTarget.orElse(0);

    double power = 0;
    if (Math.abs(error) > DEADZONE_DEGREES) {
      power = -error * POWER;
    }
    
    m_drivetrain.tankDrive(power, 0);
    SmartDashboard.putNumber("power", power);
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
