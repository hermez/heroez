package net.hermesprime.rpg.heroez.gui.camera;

import com.jme3.input.FlyByCamera;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 8:31 AM
 */
public class ExtendedFlyByCamera extends FlyByCamera {

    float aaa = 1.2f;
    float bbb = .5f;

    public ExtendedFlyByCamera(final Camera cam) {
        super(cam);
    }

    /**
     * Copied from super + added configurability.
     *
     * @param value
     */
    @Override
    protected void zoomCamera(float value) {

        // derive fovY value
        float h = cam.getFrustumTop();
        float w = cam.getFrustumRight();
        float aspect = w / h;

        float near = cam.getFrustumNear();

        float fovY = FastMath.atan(h / near)
                / (FastMath.DEG_TO_RAD * bbb);

        fovY += value * aaa;

        h = FastMath.tan(fovY * FastMath.DEG_TO_RAD * bbb) * near;
        w = h * aspect;

        cam.setFrustumTop(h);
        cam.setFrustumBottom(-h);
        cam.setFrustumLeft(-w);
        cam.setFrustumRight(w);

    }
}
