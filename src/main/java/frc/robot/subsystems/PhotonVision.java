package frc.robot.subsystems;

import java.util.OptionalDouble;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonVision extends SubsystemBase {
    private PhotonCamera camera = new PhotonCamera("Main Camera");

    public OptionalDouble getYaw() {
        var result = camera.getLatestResult();

        if (!result.hasTargets()) {
            return OptionalDouble.empty();
        }

        var bestTarget = result.getBestTarget();
        return OptionalDouble.of(bestTarget.getYaw());
    }

}
