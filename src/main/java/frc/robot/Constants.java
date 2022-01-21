package frc.robot;

public final class Constants {
  public static final class DriveConstants {
    public static final int kLeftMotor1Port = 3;
    public static final int kLeftMotor2Port = 4;
    public static final int kRightMotor1Port = 1;
    public static final int kRightMotor2Port = 2;

    public static final boolean kGyroReversed = false;

    public static final double kStabilizationP = 1;
    public static final double kStabilizationI = 0.5;
    public static final double kStabilizationD = 0;

    public static final double kTurnP = 0.1;
    public static final double kTurnI = 0.0000001;
    public static final double kTurnD = 0.01;

    public static final double kMaxTurnRateDegPerS = 100;
    public static final double kMaxTurnAccelerationDegPerSSquared = 300;

    public static final double kTurnToleranceDeg = 5;
    public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
  }
}
