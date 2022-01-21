package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.TurnToAngleProfiled;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  public final DriveSubsystem m_robotDrive = new DriveSubsystem();
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

  public RobotContainer() {
    configureButtonBindings();

    m_robotDrive.setDefaultCommand(
        new RunCommand(
            () ->
                m_robotDrive.tankDrive(
                    m_driverController.getLeftY(), m_driverController.getRightY()),
            m_robotDrive));

    //m_robotDrive.setDefaultCommand(
    //    new RunCommand(
    //        () ->
    //            m_robotDrive.arcadeDrive(
    //                -m_driverController.getLeftY(), m_driverController.getRightX()),
    //        m_robotDrive));
  }

  private void configureButtonBindings() {
    // Drive at half speed when the right bumper is held
    new JoystickButton(m_driverController, Button.kRightBumper.value)
        .whenPressed(() -> m_robotDrive.setMaxOutput(0.5))
        .whenReleased(() -> m_robotDrive.setMaxOutput(1));

    // Stabilize robot to drive straight with gyro when left bumper is held
    new JoystickButton(m_driverController, Button.kLeftBumper.value)
        .whenHeld(
            new PIDCommand(
                new PIDController(
                    DriveConstants.kStabilizationP,
                    DriveConstants.kStabilizationI,
                    DriveConstants.kStabilizationD),
                // Close the loop on the turn rate
                m_robotDrive::getTurnRate,
                // Setpoint is 0
                0,
                // Pipe the output to the turning controls
                output -> m_robotDrive.arcadeDrive(m_driverController.getLeftY(), output),
                // Require the robot drive
                m_robotDrive));

    // Turn to 90 degrees when the 'X' button is pressed, with a 5 second timeout
    new JoystickButton(m_driverController, Button.kX.value)
        .whenPressed(new TurnToAngle(-90, m_robotDrive).withTimeout(5));

    // Turn to -90 degrees with a profile when the B button is pressed, with a 5 second timeout
    new JoystickButton(m_driverController, Button.kB.value)
        .whenPressed(new TurnToAngleProfiled(90, m_robotDrive).withTimeout(5));

    // Reset Heading when 'Y' button is pressed, with a 1 second timeout
    new JoystickButton(m_driverController, Button.kY.value)
        .whenPressed(new InstantCommand(() -> m_robotDrive.zeroHeading()));
  }

  public Command getAutonomousCommand() {
    // no auto
    return new InstantCommand();
  }
}
