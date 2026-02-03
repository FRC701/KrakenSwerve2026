package frc.robot.subsystems;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class VisionTelemetry {
  private final String m_prefix;
  public VisionTelemetry(String prefix) {
    m_prefix = prefix.endsWith("/") ? prefix : (prefix + "/");
  }

  public void publishRawResult(PhotonPipelineResult r, double cacheUpdateTimeSec) {
    SmartDashboard.putNumber(m_prefix + "CacheUpdateTime_s", cacheUpdateTimeSec);
    SmartDashboard.putBoolean(m_prefix + "HasResult", r != null);
    if (r == null) {
      SmartDashboard.putBoolean(m_prefix + "HasTargets", false);
      SmartDashboard.putNumber(m_prefix + "TargetCount", 0);
      SmartDashboard.putString(m_prefix + "BestTagId", "none");
      SmartDashboard.putNumber(m_prefix + "BestAmbiguity", -1.0);
      SmartDashboard.putNumber(m_prefix + "BestYawDeg", 0.0);
      SmartDashboard.putNumber(m_prefix + "BestPitchDeg", 0.0);
      SmartDashboard.putNumber(m_prefix + "BestArea", 0.0);
      return;
    }
    SmartDashboard.putBoolean(m_prefix + "HasTargets", r.hasTargets());
    SmartDashboard.putNumber(m_prefix + "TargetCount", r.targets.size());
    if (!r.hasTargets()) {
      SmartDashboard.putString(m_prefix + "BestTagId", "none");
      SmartDashboard.putNumber(m_prefix + "BestAmbiguity", -1.0);
      SmartDashboard.putNumber(m_prefix + "BestYawDeg", 0.0);
      SmartDashboard.putNumber(m_prefix + "BestPitchDeg", 0.0);
      SmartDashboard.putNumber(m_prefix + "BestArea", 0.0);
      return;
    }
    PhotonTrackedTarget best = r.getBestTarget();
    SmartDashboard.putString(m_prefix + "BestTagId", Integer.toString(best.getFiducialId()));
    SmartDashboard.putNumber(m_prefix + "BestAmbiguity", best.getPoseAmbiguity());
    SmartDashboard.putNumber(m_prefix + "BestYawDeg", best.getYaw());
    SmartDashboard.putNumber(m_prefix + "BestPitchDeg", best.getPitch());
    SmartDashboard.putNumber(m_prefix + "BestArea", best.getArea());
  }

  public void publishFusionState(boolean accepted,
                                String rejectReason,
                                String strategyUsed,
                                Pose2d lastVisionPose,
                                double lastVisionTimestampSec) {
    SmartDashboard.putBoolean(m_prefix + "Accepted", accepted);
    SmartDashboard.putString(m_prefix + "RejectReason", rejectReason);
    SmartDashboard.putString(m_prefix + "StrategyUsed", strategyUsed);
    SmartDashboard.putNumber(m_prefix + "PoseX_m", lastVisionPose.getX());
    SmartDashboard.putNumber(m_prefix + "PoseY_m", lastVisionPose.getY());
    SmartDashboard.putNumber(m_prefix + "PoseHeading_deg", lastVisionPose.getRotation().getDegrees());
    SmartDashboard.putNumber(m_prefix + "PoseTimestamp_s", lastVisionTimestampSec);
    double now = Timer.getFPGATimestamp();
    SmartDashboard.putNumber(m_prefix + "PoseAge_s", lastVisionTimestampSec > 0.0 ? (now - lastVisionTimestampSec) : -1.0);
  }
}
