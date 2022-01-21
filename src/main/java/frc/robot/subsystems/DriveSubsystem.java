package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;

public class DriveSubsystem extends SubsystemBase {
  public CANSparkMax m_leftMotors = new CANSparkMax(DriveConstants.kLeftMotor1Port, MotorType.kBrushless);
  public CANSparkMax m_leftSlave = new CANSparkMax(DriveConstants.kLeftMotor2Port, MotorType.kBrushless);
  public CANSparkMax m_rightMotors = new CANSparkMax(DriveConstants.kRightMotor1Port, MotorType.kBrushless);
  public CANSparkMax m_rightSlave = new CANSparkMax(DriveConstants.kRightMotor2Port, MotorType.kBrushless);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  private RelativeEncoder m_leftEncoder = m_leftMotors.getEncoder();
  private RelativeEncoder m_rightEncoder = m_rightMotors.getEncoder();
  
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  public DriveSubsystem() {
    m_leftMotors.restoreFactoryDefaults();
    m_leftSlave.restoreFactoryDefaults();
    m_rightMotors.restoreFactoryDefaults();
    m_rightSlave.restoreFactoryDefaults();
    m_leftSlave.follow(m_leftMotors);
    m_rightSlave.follow(m_rightMotors);
    //m_rightMotors.setInverted(true);
    m_leftMotors.setInverted(true);
  }

  public void arcadeDrive(double fwd, double rot) {
    SmartDashboard.putString("Arcade Drive Values", fwd + " : " + -rot);
    m_drive.arcadeDrive(fwd, -rot);
  }

  public void tankDrive(double left, double right){
    //SmartDashboard.putString("Tank Drive Values", left + " : " + right);
    m_drive.tankDrive(left*.5, right*.5);
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  public double getAverageEncoderPosition() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
  }

  public RelativeEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  public RelativeEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  public void zeroHeading() {
    m_gyro.zeroYaw();
  }

  public double getHeading() {
    return Math.IEEEremainder(m_gyro.getAngle(), 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public double getTurnRate() {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }
}
