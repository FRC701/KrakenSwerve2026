package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import java.util.Map;

public class FieldLayoutRebuiltWelded {

  /** Origin is the bottom left corner of the field image (Blue Alliance Station) */
  public static final double kFieldLength = Units.inchesToMeters(651.22);

  public static final double kFieldWidth = Units.inchesToMeters(317.69);

  // AprilTag locations
  // Field element locations (Rebuilt-welded; units are inches + degrees)
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
public static final Map<Integer, Pose3d> aprilTags =
    Map.ofEntries(
        Map.entry(
            1, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(467.64),
                Units.inchesToMeters(292.31),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            2, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(469.11),
                Units.inchesToMeters(182.60),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            3, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(445.35),
                Units.inchesToMeters(172.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            4, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(445.35),
                Units.inchesToMeters(158.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            5, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(469.11),
                Units.inchesToMeters(135.09),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            6, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(467.64),
                Units.inchesToMeters(25.37),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            7, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(470.59),
                Units.inchesToMeters(25.37),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            8, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(483.11),
                Units.inchesToMeters(135.09),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            9, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(492.88),
                Units.inchesToMeters(144.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            10, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(492.88),
                Units.inchesToMeters(158.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            11, // Hub, Red
            new Pose3d(
                Units.inchesToMeters(483.11),
                Units.inchesToMeters(182.60),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            12, // Trench, Red
            new Pose3d(
                Units.inchesToMeters(470.59),
                Units.inchesToMeters(292.31),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            13, // Outpost, Red
            new Pose3d(
                Units.inchesToMeters(650.92),
                Units.inchesToMeters(291.47),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            14, // Outpost, Red
            new Pose3d(
                Units.inchesToMeters(650.92),
                Units.inchesToMeters(274.47),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            15, // Tower, Red
            new Pose3d(
                Units.inchesToMeters(650.90),
                Units.inchesToMeters(170.22),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            16, // Tower, Red
            new Pose3d(
                Units.inchesToMeters(650.90),
                Units.inchesToMeters(153.22),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            17, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(183.59),
                Units.inchesToMeters(25.37),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            18, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(182.11),
                Units.inchesToMeters(135.09),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            19, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(205.87),
                Units.inchesToMeters(144.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            20, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(205.87),
                Units.inchesToMeters(158.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            21, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(182.11),
                Units.inchesToMeters(182.60),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            22, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(183.59),
                Units.inchesToMeters(292.31),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            23, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(180.64),
                Units.inchesToMeters(292.31),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            24, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(168.11),
                Units.inchesToMeters(182.60),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(90)))),
        Map.entry(
            25, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(158.34),
                Units.inchesToMeters(172.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            26, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(158.34),
                Units.inchesToMeters(158.84),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            27, // Hub, Blue
            new Pose3d(
                Units.inchesToMeters(168.11),
                Units.inchesToMeters(135.09),
                Units.inchesToMeters(44.25),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(270)))),
        Map.entry(
            28, // Trench, Blue
            new Pose3d(
                Units.inchesToMeters(180.64),
                Units.inchesToMeters(25.37),
                Units.inchesToMeters(35.00),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(180)))),
        Map.entry(
            29, // Outpost, Blue
            new Pose3d(
                Units.inchesToMeters(0.30),
                Units.inchesToMeters(26.22),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            30, // Outpost, Blue
            new Pose3d(
                Units.inchesToMeters(0.30),
                Units.inchesToMeters(43.22),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            31, // Tower, Blue
            new Pose3d(
                Units.inchesToMeters(0.32),
                Units.inchesToMeters(147.47),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))),
        Map.entry(
            32, // Tower, Blue
            new Pose3d(
                Units.inchesToMeters(0.32),
                Units.inchesToMeters(164.47),
                Units.inchesToMeters(21.75),
                new Rotation3d(0.0, 0.0, Units.degreesToRadians(0)))));
}
