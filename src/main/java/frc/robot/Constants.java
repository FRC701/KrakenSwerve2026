// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.VecBuilder;
//import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public class Constants {
    public static final class Vision {
        // Camera name
        // Must match camera set in PhotonVision UI at photonvision.local:5800
        public static final String kcameraName = "photonvision";
        // Robot -> Camera transform (camera pose relative to robot origin).
        // WPILib coordinate convention: +X forward, +Y left, +Z up.
        // https://docs.wpilib.org/en/stable/docs/software/basic-programming/coordinate-system.html
        // In most cases in WPILib programming, 0° is aligned with the positive X axis,
        // and 180° is aligned with the negative X axis. CCW rotation is positive, so
        // 90° is aligned with the positive Y axis, and -90° is aligned with the
        // negative Y axis.
        
        public static final double kCameraMountPitchAngleRad = Units.degreesToRadians(120);
        public static final double kCameraMountRollAngleRad = Units.degreesToRadians(0);
        public static final double kCameraMountYawAngleRad = Units.degreesToRadians(0);

        public static final double kCameraForwardMeters = Units.inchesToMeters(11);
        public static final double kCameraLeftMeters = Units.inchesToMeters(11);
        public static final double kCameraUpMeters = Units.inchesToMeters(5.504);

        // Robot to camera transform
        public static final Transform3d kRobotToCam3d = new Transform3d(
                new Translation3d(kCameraForwardMeters, kCameraLeftMeters, kCameraUpMeters),
                new Rotation3d(kCameraMountRollAngleRad, kCameraMountPitchAngleRad, kCameraMountYawAngleRad));
        public static final Transform2d kRobotToCam2d = new Transform2d(Units.inchesToMeters(kCameraForwardMeters),
                Units.inchesToMeters(kCameraLeftMeters),
                new Rotation2d(kCameraMountRollAngleRad, kCameraMountPitchAngleRad));

  // Vision trust assumptions (1σ)
  public static final double kVisionPosStdDevMeters = 0.7;
  public static final double kVisionHeadingStdDevDeg = 10.0;

  public static final Matrix<N3, N1> kVisionStdDevs =
      VecBuilder.fill(
          kVisionPosStdDevMeters,
          kVisionPosStdDevMeters,
          Math.toRadians(kVisionHeadingStdDevDeg));

  // Acceptance rules
  public static final int kMinAprilTagsForPose = 1;
  public static final double kMaxAcceptableSingleTagAmbiguity = 0.25;
    }

}
