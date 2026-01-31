package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import java.util.Map;

public class FieldLayoutRebuiltAndyMark {

  /** Origin is the bottom left corner of the field image (Blue Alliance Station) */
  public static final double kFieldLength = Units.inchesToMeters(650.12);

  public static final double kFieldWidth = Units.inchesToMeters(316.64);

  // AprilTag locations

  // Field element locations (Rebuilt-welded; units are inches + degrees)
  //
/**  AprilTag Coordinates
The XYZ origin is established in the top right corner of the
field (as viewed in the image above). An X coordinate of 0 is
aligned with the Blue Alliance Station diamond plate (or
extrusion frame for the AndyMark perimeter). A Y coordinate
of 0 is aligned with the Scoring Table side guardrail
polycarbonate (or extrusion frame for the AndyMark
perimeter). A Z coordinate of 0 is on the surface of the
carpet. Distances are measured to the center of the front face
of the tag.
+Z is up in the air from the carpet, +X is toward the red
alliance station (left, in the image above), and +Y is away from
the scoring table (down, in the image above). The orientation
of the tag is specified by the Z-rotation. 0° faces the Red
Alliance Station, 90° faces the audience side (non-Scoring
Table side), and 180° faces the Blue Alliance Station.
**/
// Field element locations (from your table; units are inches + degrees)
// Field element locations (from your table; units are inches + degrees)
public static final Map<Integer, Pose3d> aprilTags =
    Map.ofEntries(
        Map.entry(
            1, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(467.08),
                Units.inchesToMeters(291.79),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            2, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(468.56),
                Units.inchesToMeters(182.08),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            3, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(444.80),
                Units.inchesToMeters(172.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            4, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(444.80),
                Units.inchesToMeters(158.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            5, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(468.56),
                Units.inchesToMeters(134.56),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            6, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(467.08),
                Units.inchesToMeters(24.85),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            7, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(470.03),
                Units.inchesToMeters(24.85),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            8, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(482.56),
                Units.inchesToMeters(134.56),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            9, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(492.33),
                Units.inchesToMeters(144.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            10, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(492.33),
                Units.inchesToMeters(158.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            11, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(482.56),
                Units.inchesToMeters(182.08),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            12, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(470.03),
                Units.inchesToMeters(291.79),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            13, // Outpost, Red
            new Pose3d(
                Units.inchesToMeters(649.58),
                Units.inchesToMeters(291.02),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            14, // Outpost, Red
            new Pose3d(
                Units.inchesToMeters(649.58),
                Units.inchesToMeters(274.02),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            15, // Tower, Red
            new Pose3d(
                Units.inchesToMeters(649.57),
                Units.inchesToMeters(169.78),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            16, // Tower, Red
            new Pose3d(
                Units.inchesToMeters(649.57),
                Units.inchesToMeters(152.78),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            17, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(183.03),
                Units.inchesToMeters(24.85),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            18, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(181.56),
                Units.inchesToMeters(134.56),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            19, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(205.32),
                Units.inchesToMeters(144.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            20, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(205.32),
                Units.inchesToMeters(158.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            21, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(181.56),
                Units.inchesToMeters(182.08),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            22, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(183.03),
                Units.inchesToMeters(291.79),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            23, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(180.08),
                Units.inchesToMeters(291.79),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            24, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(167.56),
                Units.inchesToMeters(182.08),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            25, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(157.79),
                Units.inchesToMeters(172.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            26, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(157.79),
                Units.inchesToMeters(158.32),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            27, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(167.56),
                Units.inchesToMeters(134.56),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            28, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(180.08),
                Units.inchesToMeters(24.85),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            29, // Outpost, Blue
            new Pose3d(
                Units.inchesToMeters(0.54),
                Units.inchesToMeters(25.62),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            30, // Outpost, Blue
            new Pose3d(
                Units.inchesToMeters(0.54),
                Units.inchesToMeters(42.62),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            31, // Tower, Blue
            new Pose3d(
                Units.inchesToMeters(0.55),
                Units.inchesToMeters(146.86),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            32, // Tower, Blue
            new Pose3d(
                Units.inchesToMeters(0.55),
                Units.inchesToMeters(163.86),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))));
}
