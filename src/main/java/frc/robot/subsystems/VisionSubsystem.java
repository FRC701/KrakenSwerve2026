// FRC2106 Junkyard Dogs - Continuity Base Code - www.team2106.org

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.io.PrintStream;
import java.lang.System;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.FieldLayoutRebuiltWelded;

// Ignore unused variable warnings
@SuppressWarnings("unused")

public class VisionSubsystem extends SubsystemBase {

    // vars
    private Pose3d m_FieldToRobotAprilTagPose;
    private Pose2d m_fieldRobotPose;
    private Pose3d m_RobotPose3d;
    private double camtotargetx = 0;
    private double m_cameraToTargetTranslationx = 0;
    private boolean m_FieldToRobotAprilTagPoseNull = true;
    private boolean m_fieldRobotPoseNull = true;
    private boolean m_RobotPose3dNull = true;
    private boolean m_RobotToCam3dNull = true;
    private String mCameraToTargetString;
    private String m_cameraToTargetTranslationString;
    private double distanceToTarget = 0;
    private Pose2d m_AprilTagPose2d;
    private Pose3d robotPose3dRelativeToField;
    // public PhotonPoseEstimator photonPoseEstimator;
    // public AprilTagFieldLayout atfl;
    private final Field2d m_field = new Field2d();

    // Create a vision photon camera
    PhotonCamera mVisionCamera;
    // Camera result for vision camera
    private PhotonPipelineResult mCameraResult;

    // Pose estimator
    private PhotonPoseEstimator mPoseEstimator;
    // read in Cam to robot transform
    private Transform3d m_robotToCamTransform3d = Constants.VisionConstants.robotToCam3d;
    // 2d version
    private Transform2d m_robotToCamTransform2d = Constants.VisionConstants.robotToCam2d;
    // get entire apriltag layout
    private AprilTagFieldLayout mAprilTagFieldLayout = AprilTagFields.k2026RebuiltWelded.loadAprilTagLayoutField();
    // get pose of specific april tag from java file

    Pigeon2 ahrs = new Pigeon2(0); // CAN ID

    private final Joystick joystick = new Joystick(0);

    // Subsystem Constructor
    public VisionSubsystem() {
        // visionCamera.setVersionCheckEnabled(false);
        // visionCamera.setDriverMode(true);
        // Port forward photon vision so we can access it with an ethernet cable
        // PortForwarder.add(5800, "photonvision.local", 5800);

        mVisionCamera = new PhotonCamera(Constants.VisionConstants.cameraName);

        // Update camera results before periodic
        updateCameraResults();
        mVisionCamera.setLED(VisionLEDMode.kDefault);

        SmartDashboard.putData("Field", m_field);// Do this in either robot or
        // subsystem init
    }

    // Update the camera results
    private void updateCameraResults() {
        mCameraResult = mVisionCamera.getLatestResult();
    }

    // Checks if camera sees targets, must use to stop null exceptions!
    public Boolean hasTargets() {
        return (mCameraResult.hasTargets());
    }

    private void updatePoses() {
        if (hasTargets()) {
            // object/fiducial tag space (X forward, Y left, Z up)
            // define 2D pose of aprilTag
            // get best target April Tag ID
            int AprilTagID = getTargetID();// OK
            // AprilTagID=16;
            Pose3d m_AprilTagPose3d = FieldLayoutRebuiltWelded.aprilTags.get(AprilTagID);// Blue Speaker (left)
            Pose2d m_AprilTagPose2d = m_AprilTagPose3d.toPose2d();// OK

            Transform3d m_CameraToTargetTransform3d = getTargetTransform();// OK
            // Return the heading of the robot as a edu.wpi.first.math.geometry.Rotation2d.
            Rotation2d m_gyroAngle = ahrs.getRotation2d();// OK
            // Return the horizontal (X) distance of the robot to the best identified
            // apriltag in meters
            final double m_targetDistance = getTargetDistance();// OK

            final double m_targetHeight = getTargetTransformHeight();
            // get yaw to target
            Rotation2d m_targetYaw = Rotation2d.fromDegrees(-getTargetYaw());// OK
            // get the target's camera-relative translation.
            Translation2d m_cameraToTargetTranslation = PhotonUtils.estimateCameraToTargetTranslation(m_targetDistance,
                    m_targetYaw);// OK
            // get the Transform2d that takes us from the camera to the target.
            Transform2d m_CameraToTargetTransform2d = PhotonUtils.estimateCameraToTarget(
                    m_cameraToTargetTranslation, m_AprilTagPose2d, m_gyroAngle);// OK

            // Estimates the pose of the robot in the field coordinate system, given the
            // pose of the fiducial tag, the robot relative to the camera, and the target
            // relative to the camera.

            // Calculate robot's field relative pose

            Pose3d m_AprilTagTargetPose3d = FieldLayoutRebuiltWelded.aprilTags.get(AprilTagID);
            Pose3d robotPose3dRelativeToField = PhotonUtils.estimateFieldToRobotAprilTag(m_CameraToTargetTransform3d,
                    m_AprilTagTargetPose3d, m_robotToCamTransform3d);// Not OK
            // calculate distance to target

            double distanceToTarget = PhotonUtils.getDistanceToPose(robotPose3dRelativeToField.toPose2d(),
                    m_AprilTagPose2d);// OK
            // Estimate the position of the robot in the field.
            Pose2d m_fieldRobotPose = PhotonUtils.estimateFieldToRobot(
                    Constants.VisionConstants.kCameraHeightMeters,m_targetHeight,
                    Constants.VisionConstants.kCameraMountAngle, getTargetPitch(), m_targetYaw,
                    m_gyroAngle, m_AprilTagPose2d,
                    m_robotToCamTransform2d);

            // Do this in either robot periodic or subsystem periodic
            m_field.setRobotPose(robotPose3dRelativeToField.toPose2d());

        }
    }

    // Returns the single best target from the camera
    private PhotonTrackedTarget getBestTarget() {
        return (mCameraResult.getBestTarget());
    }

    // Returns all targets from the camera in an array
    private List<PhotonTrackedTarget> getTargetList() {
        return (mCameraResult.getTargets());
    }

    // Returns a picked target from the target list
    private PhotonTrackedTarget getTargetFromList(int num) {
        return (mCameraResult.getTargets().get(num));
    }

    // Returns the size of the target list
    private int getListSize() {
        return (mCameraResult.getTargets().size());
    }

    // Returns a percentage of how much area a target takes up, 0 - 100 percent
    private double getTargetArea() {
        return (getBestTarget().getArea());
    }

    private double getTargetPitch() {
        return (getBestTarget().getPitch());
    }

    private double getTargetYaw() {
        return (getBestTarget().getYaw());
    }

    private String getString() {
        return (getBestTarget().toString());
    }

    // Returns the april tag ID number
    public int getTargetID() {
        return (getBestTarget().getFiducialId());
    }

    private double getTargetDistance() {
        return getTargetTransform().getX();
    }

    public Transform3d getTargetTransform() {
        return (getBestTarget().getBestCameraToTarget());
    }

    public double getTargetTransformHeight() {
        return (getBestTarget().getBestCameraToTarget().getZ());
    }

    // public double getDistance() {
    //     double distance = (Constants.VisionConstants.kTargetHeightMeters
    //             - Constants.VisionConstants.kCameraHeightMeters)
    //             / Math.tan(Constants.VisionConstants.kCameraMountAngle + getTargetPitch());
    //     return distance;
    // }

    public double getPoseAmbiguity() {

        return getBestTarget().getPoseAmbiguity();
    }

    // Get the transform that maps camera space (X = forward, Y = left, Z = up) to
    // object/fiducial tag space (X forward, Y left, Z up)
    // private void updateSmartDashboardNavx() {
    // SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
    // SmartDashboard.putBoolean("IMU_IsCalibrating", ahrs.isCalibrating());
    // SmartDashboard.putNumber("IMU_Yaw", ahrs.getYaw());
    // SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
    // SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());

    // /* Display tilt-corrected, Magnetometer-based heading (requires */
    // /* magnetometer calibration to be useful) */

    // SmartDashboard.putNumber("IMU_CompassHeading", ahrs.getCompassHeading());

    // /* Display 9-axis Heading (requires magnetometer calibration to be useful) */
    // SmartDashboard.putNumber("IMU_FusedHeading", ahrs.getFusedHeading());

    // /* These functions are compatible w/the WPI Gyro Class, providing a simple */
    // /* path for upgrading from the Kit-of-Parts gyro to the navx MXP */

    // SmartDashboard.putNumber("IMU_TotalYaw", ahrs.getAngle());
    // SmartDashboard.putNumber("IMU_YawRateDPS", ahrs.getRate());

    // /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect)
    // */

    // SmartDashboard.putNumber("IMU_Accel_X", ahrs.getWorldLinearAccelX());
    // SmartDashboard.putNumber("IMU_Accel_Y", ahrs.getWorldLinearAccelY());
    // SmartDashboard.putBoolean("IMU_IsMoving", ahrs.isMoving());
    // SmartDashboard.putBoolean("IMU_IsRotating", ahrs.isRotating());

    // /* Display estimates of velocity/displacement. Note that these values are */
    // /* not expected to be accurate enough for estimating robot position on a */
    // /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding
    // */
    // /* of these errors due to single (velocity) integration and especially */
    // /* double (displacement) integration. */

    // SmartDashboard.putNumber("Velocity_X", ahrs.getVelocityX());
    // SmartDashboard.putNumber("Velocity_Y", ahrs.getVelocityY());
    // SmartDashboard.putNumber("Displacement_X", ahrs.getDisplacementX());
    // SmartDashboard.putNumber("Displacement_Y", ahrs.getDisplacementY());

    // /* Display Raw Gyro/Accelerometer/Magnetometer Values */
    // /* NOTE: These values are not normally necessary, but are made available */
    // /* for advanced users. Before using this data, please consider whether */
    // /* the processed data (see above) will suit your needs. */

    // SmartDashboard.putNumber("RawGyro_X", ahrs.getRawGyroX());
    // SmartDashboard.putNumber("RawGyro_Y", ahrs.getRawGyroY());
    // SmartDashboard.putNumber("RawGyro_Z", ahrs.getRawGyroZ());
    // SmartDashboard.putNumber("RawAccel_X", ahrs.getRawAccelX());
    // SmartDashboard.putNumber("RawAccel_Y", ahrs.getRawAccelY());
    // SmartDashboard.putNumber("RawAccel_Z", ahrs.getRawAccelZ());
    // SmartDashboard.putNumber("RawMag_X", ahrs.getRawMagX());
    // SmartDashboard.putNumber("RawMag_Y", ahrs.getRawMagY());
    // SmartDashboard.putNumber("RawMag_Z", ahrs.getRawMagZ());
    // SmartDashboard.putNumber("IMU_Temp_C", ahrs.getTempC());
    // SmartDashboard.putNumber("IMU_Timestamp", ahrs.getLastSensorTimestamp());

    // /* Omnimount Yaw Axis Information */
    // /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount */
    // AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
    // SmartDashboard.putString("YawAxisDirection", yaw_axis.up ? "Up" : "Down");
    // SmartDashboard.putNumber("YawAxis", yaw_axis.board_axis.getValue());

    // /* Sensor Board Information */
    // SmartDashboard.putString("FirmwareVersion", ahrs.getFirmwareVersion());

    // /* Quaternion Data */
    // /* Quaternions are fascinating, and are the most compact representation of */
    // /* orientation data. All of the Yaw, Pitch and Roll Values can be derived */
    // /* from the Quaternions. If interested in motion processing, knowledge of */
    // /* Quaternions is highly recommended. */
    // SmartDashboard.putNumber("QuaternionW", ahrs.getQuaternionW());
    // SmartDashboard.putNumber("QuaternionX", ahrs.getQuaternionX());
    // SmartDashboard.putNumber("QuaternionY", ahrs.getQuaternionY());
    // SmartDashboard.putNumber("QuaternionZ", ahrs.getQuaternionZ());

    // /* Connectivity Debugging Support */
    // SmartDashboard.putNumber("IMU_Byte_Count", ahrs.getByteCount());
    // SmartDashboard.putNumber("IMU_Update_Count", ahrs.getUpdateCount());
    // }

    // Update the smart dashboard
    private void updateSmartDashboard() {
        // Put targets? value
        SmartDashboard.putBoolean("Targets?", hasTargets());

        // Check if targets are found before putting values to prevent null!
        if (hasTargets()) {

            SmartDashboard.putString("Target ID", getTargetID() + "");
            SmartDashboard.putString("Target Area", getTargetArea() + "%");
            SmartDashboard.putString("Target Pitch", getTargetPitch() + "");
            SmartDashboard.putString("Target Yaw", getTargetYaw() + "");
            SmartDashboard.putString("Target Height", getTargetTransformHeight() + "");
            SmartDashboard.putString("Target String", getString() + "");
            SmartDashboard.putNumber("Camera Height", Constants.VisionConstants.kCameraHeightMeters);
            SmartDashboard.putNumber("Camera Pitch", Constants.VisionConstants.kCameraMountAngle);
            SmartDashboard.putString("Camera Name", Constants.VisionConstants.cameraName);
            SmartDashboard.putNumber("Target Distance X-Plane", getTargetDistance()); // OK//m_targetDistance
            // SmartDashboard.putNumber("camtotargetX", camtotargetx);// OK
            // SmartDashboard.putNumber("m_cameraToTargetTranslation X",
            // m_cameraToTargetTranslationx);// OK
            // SmartDashboard.putNumber("distance to target", distanceToTarget);// OK

        } else {
            SmartDashboard.putString("Target ID", "No ID Found!");
            SmartDashboard.putString("Target Pitch", "-1");
            SmartDashboard.putString("Target Area", "0" + "%");
            SmartDashboard.putNumber("Target Distance X-Plane", -1);
        }
        boolean zero_yaw_pressed = joystick.getTrigger();
        if (zero_yaw_pressed) {
            ahrs.setYaw(0);
        }
        if (ahrs.isConnected()) {
            /* Display 6-axis Processed Angle Data */
            double rollDeg = ahrs.getRoll().getValueAsDouble();
            double pitchDeg = ahrs.getPitch().getValueAsDouble();
            double yawDeg = ahrs.getYaw().getValueAsDouble();

            SmartDashboard.putNumber("Gyro/RollDeg", rollDeg);
            SmartDashboard.putNumber("Gyro/PitchDeg", pitchDeg);
            SmartDashboard.putNumber("Gyro/YawDeg", yawDeg);
        }

        SmartDashboard.putString("LED State", mVisionCamera.getLEDMode().toString());
        // SmartDashboard.putBoolean("m_FieldToRobotAprilTagPoseNull",
        // m_FieldToRobotAprilTagPoseNull);
        // SmartDashboard.putBoolean("m_RobotPose3dNull", m_RobotPose3dNull);
        // SmartDashboard.putBoolean("m_fieldRobotPoseNull", m_fieldRobotPoseNull);

    }

    public void setLEDOn() {
        mVisionCamera.setLED(VisionLEDMode.kBlink);
        DriverStation.reportWarning("CHANGE LED", true);
    }

    // A periodic loop, updates smartdashboard and camera results
    @Override
    public void periodic() {
        SmartDashboard.putString("Camera", mVisionCamera.toString());
        updateCameraResults();
        updatePoses();
        updateSmartDashboard();
        // updateSmartDashboardNavx();
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

}