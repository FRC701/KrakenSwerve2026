// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
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
                // Must match camera set at photonvision.local:5800

                // Default
                // public static final String cameraName = "Camera_Module_v1";
                public static final String cameraName = "photonvision";
                //public static final String cameraName = "limelight_v3";
                // ---------- Vision
                // Constants about how your camera is mounted to the robot
                public static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(120); // Angle "up" from
                                                                                              // horizontal
                public static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(5.504); // Height above floor

                // How far from the target we want to be (need to be up against the note)
                public static final double GOAL_RANGE_METERS = Units.feetToMeters(1);

                // Where one of the Blue Source AprilTags is located
                // public static final Pose3d TARGET_POSE = new Pose3d(
                //                 new Translation3d(
                //                                 Units.inchesToMeters(593.68),
                //                                 Units.inchesToMeters(9.68),
                //                                 Units.inchesToMeters(53.38)), // (center of vision target)
                //                 new Rotation3d(0.0, 0.0, Units.degreesToRadians(120)));
                // ----------
                //public static final double kTargetHeightMeters = Units.inchesToMeters(50);
                public static final double kCameraHeightMeters = Units.inchesToMeters(5.504);
                public static final double kCameraMountAngle = Units.degreesToRadians(120);
     
                public static final double kCameraYOffset = Units.inchesToMeters(11);
                public static final double kCameraXOffset = Units.inchesToMeters(11);
            
                // Robot to camera transform
                public static final Transform3d robotToCam3d = new Transform3d(
                                new Translation3d(kCameraXOffset, kCameraYOffset, kCameraHeightMeters),
                                new Rotation3d(0.0, kCameraMountAngle, 0.0));
                public static final Transform2d robotToCam2d =   new Transform2d(Units.inchesToMeters(0),
                                Units.inchesToMeters(0),
                                new Rotation2d(0.0, 0.0));

        }

}
