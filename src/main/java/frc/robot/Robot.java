package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    SmartDashboard.putNumber("P Value", Constants.DriveConstants.kTurnP);
    SmartDashboard.putNumber("I Value", Constants.DriveConstants.kTurnI);
    SmartDashboard.putNumber("D Value", Constants.DriveConstants.kTurnD);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("Left Drive Encoder", m_robotContainer.m_robotDrive.getLeftEncoder().getPosition());
    SmartDashboard.putNumber("Right Drive Encoder", m_robotContainer.m_robotDrive.getRightEncoder().getPosition());
    SmartDashboard.putNumber("AVG Drive Encoder", m_robotContainer.m_robotDrive.getAverageEncoderPosition());
    SmartDashboard.putNumber("Gryo Heading", m_robotContainer.m_robotDrive.getHeading());
  }


  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
