// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public class Constants {
    public static final class VisionConstants {
        // Camera name
        // Must match camera set in PhotonVision UI at photonvision.local:5800
        public static final String cameraName = "photonvision";
        // Robot -> Camera transform (camera pose relative to robot origin).
        // WPILib coordinate convention: +X forward, +Y left, +Z up.
        // https://docs.wpilib.org/en/stable/docs/software/basic-programming/coordinate-system.html
        // In most cases in WPILib programming, 0° is aligned with the positive X axis,
        // and 180° is aligned with the negative X axis. CCW rotation is positive, so
        // 90° is aligned with the positive Y axis, and -90° is aligned with the
        // negative Y axis.
        public static final double kCameraMountPitchAngle = Units.degreesToRadians(120);
        public static final double kCameraMountRollAngle = Units.degreesToRadians(0);
        public static final double kCameraMountYawAngle = Units.degreesToRadians(0);

        public static final double kCameraXOffset = Units.inchesToMeters(11);
        public static final double kCameraYOffset = Units.inchesToMeters(11);
        public static final double kCameraHeightMeters = Units.inchesToMeters(5.504);

        // Robot to camera transform
        public static final Transform3d kRobotToCam3d = new Transform3d(
                new Translation3d(kCameraXOffset, kCameraYOffset, kCameraHeightMeters),
                new Rotation3d(kCameraMountRollAngle, kCameraMountPitchAngle, kCameraMountYawAngle));
        public static final Transform2d kRobotToCam2d = new Transform2d(Units.inchesToMeters(kCameraXOffset),
                Units.inchesToMeters(kCameraYOffset),
                new Rotation2d(kCameraMountRollAngle, kCameraMountPitchAngle));


    }

}
