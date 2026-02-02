package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class VisionSubsystem extends SubsystemBase {
  // Member variables must be prefixed with m_
  private final PhotonCamera m_camera;
  private final PhotonPoseEstimator m_poseEstimator;

  public VisionSubsystem() {
    m_camera = new PhotonCamera(Constants.Vision.kcameraName);

    AprilTagFieldLayout fieldLayout =
        AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);
    m_poseEstimator =
        new PhotonPoseEstimator(
            fieldLayout,
            Constants.Vision.kRobotToCam3d);
  }

  public Optional<VisionMeasurement> getLatestMeasurement(Pose2d currentEstimatedPose) {
    PhotonPipelineResult result = getNewestUnreadResult();
    if (result == null || !result.hasTargets()) return Optional.empty();

    if (result.targets.size() < Constants.Vision.kMinAprilTagsForPose) {
      return Optional.empty();
    }

    if (result.targets.size() == 1
        && result.getBestTarget().getPoseAmbiguity()
            > Constants.Vision.kMaxAcceptableSingleTagAmbiguity) {
      return Optional.empty();
    }

    // Provide a 3D reference pose (z=0, roll/pitch=0) for "closest to reference" selection.
    Pose3d referencePose =
        new Pose3d(
            currentEstimatedPose.getX(),
            currentEstimatedPose.getY(),
            0.0,
            new Rotation3d(0.0, 0.0, currentEstimatedPose.getRotation().getRadians()));

    // Primary: coprocessor multi-tag solve (PhotonVision is running on Limelight). :contentReference[oaicite:3]{index=3}
    Optional<EstimatedRobotPose> estimate = m_poseEstimator.estimateCoprocMultiTagPose(result);

    // Fallback: choose solution closest to our current pose (robust for single-tag).
    if (estimate.isEmpty()) {
      estimate = m_poseEstimator.estimateClosestToReferencePose(result, referencePose);
    }

    // Last resort: lowest ambiguity target-based solve.
    if (estimate.isEmpty()) {
      estimate = m_poseEstimator.estimateLowestAmbiguityPose(result);
    }

    if (estimate.isEmpty()) return Optional.empty();

    return Optional.of(
        new VisionMeasurement(
            estimate.get().estimatedPose.toPose2d(),
            estimate.get().timestampSeconds,
            Constants.Vision.kVisionStdDevs));
  }

  /**
   * PhotonCamera.getLatestResult() is deprecated; use getAllUnreadResults().
   * Note: getAllUnreadResults() clears an internal FIFO; call exactly once per loop. :contentReference[oaicite:4]{index=4}
   */
  private PhotonPipelineResult getNewestUnreadResult() {
    List<PhotonPipelineResult> unread = m_camera.getAllUnreadResults();
    if (unread.isEmpty()) return null;
    return unread.get(unread.size() - 1); // newest
  }

  public static record VisionMeasurement(
      Pose2d pose, double timestampSeconds, Matrix<N3, N1> stdDevs) {}
}
