package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class VisionSubsystem extends SubsystemBase {
  private final PhotonCamera m_camera;
  private final PhotonPoseEstimator m_poseEstimator;
  private final VisionTelemetry m_telemetry = new VisionTelemetry("Vision");

  private PhotonPipelineResult m_cachedResult = null;
  private double m_cacheUpdateTimeSec = 0.0;

  private boolean m_lastAccepted = false;
  private String m_lastRejectReason = "none";
  private String m_lastStrategyUsed = "none";
  private Pose2d m_lastVisionPose = new Pose2d();
  private double m_lastVisionTimestamp = 0.0;

  public VisionSubsystem() {
    m_camera = new PhotonCamera(Constants.Vision.kcameraName);
    AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);
    m_poseEstimator = new PhotonPoseEstimator(fieldLayout, Constants.Vision.kRobotToCam3d);
  }

  public Optional<VisionMeasurement> getLatestMeasurement(Pose2d currentEstimatedPose) {
    PhotonPipelineResult result = m_cachedResult;
    m_lastAccepted = false;
    m_lastRejectReason = "no_result";
    m_lastStrategyUsed = "none";
    if (result == null) return Optional.empty();
    if (!result.hasTargets()) {
      m_lastRejectReason = "no_targets";
      return Optional.empty();
    }
    if (result.targets.size() < Constants.Vision.kMinAprilTagsForPose) {
      m_lastRejectReason = "too_few_tags";
      return Optional.empty();
    }
    if (result.targets.size() == 1
        && result.getBestTarget().getPoseAmbiguity() > Constants.Vision.kMaxAcceptableSingleTagAmbiguity) {
      m_lastRejectReason = "single_tag_ambiguity";
      return Optional.empty();
    }

    Pose3d referencePose =
        new Pose3d(
            currentEstimatedPose.getX(),
            currentEstimatedPose.getY(),
            0.0,
            new Rotation3d(0.0, 0.0, currentEstimatedPose.getRotation().getRadians()));

    Optional<EstimatedRobotPose> estimate = m_poseEstimator.estimateCoprocMultiTagPose(result);
    if (estimate.isPresent()) m_lastStrategyUsed = "coproc_multitag";
    if (estimate.isEmpty()) {
      estimate = m_poseEstimator.estimateClosestToReferencePose(result, referencePose);
      if (estimate.isPresent()) m_lastStrategyUsed = "closest_to_reference";
    }
    if (estimate.isEmpty()) {
      estimate = m_poseEstimator.estimateLowestAmbiguityPose(result);
      if (estimate.isPresent()) m_lastStrategyUsed = "lowest_ambiguity";
    }
    if (estimate.isEmpty()) {
      m_lastRejectReason = "no_pose_solution";
      return Optional.empty();
    }

    Pose2d visionPose = estimate.get().estimatedPose.toPose2d();
    double ts = estimate.get().timestampSeconds;

    m_lastAccepted = true;
    m_lastRejectReason = "none";
    m_lastVisionPose = visionPose;
    m_lastVisionTimestamp = ts;

    return Optional.of(new VisionMeasurement(visionPose, ts, Constants.Vision.kVisionStdDevs));
  }

  private void refreshCachedResult() {
    List<PhotonPipelineResult> unread = m_camera.getAllUnreadResults();
    if (!unread.isEmpty()) {
      m_cachedResult = unread.get(unread.size() - 1);
      m_cacheUpdateTimeSec = Timer.getFPGATimestamp();
    }
  }

  @Override
  public void periodic() {
    refreshCachedResult();
    m_telemetry.publishRawResult(m_cachedResult, m_cacheUpdateTimeSec);
    m_telemetry.publishFusionState(m_lastAccepted, m_lastRejectReason, m_lastStrategyUsed, m_lastVisionPose, m_lastVisionTimestamp);
  }

  public static record VisionMeasurement(Pose2d pose, double timestampSeconds, Matrix<N3, N1> stdDevs) {}
}
