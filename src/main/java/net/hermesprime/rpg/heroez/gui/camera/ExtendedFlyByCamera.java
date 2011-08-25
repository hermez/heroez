package net.hermesprime.rpg.heroez.gui.camera;

import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import net.hermesprime.rpg.heroez.util.HeroezSettings;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 8:31 AM
 */
public class ExtendedFlyByCamera extends FlyByCamera {

    private float zoomSpeed = 1.2f;
    private float moveSpeed = 4f;
    private float anotherZoomSpeed = .5f;


    public ExtendedFlyByCamera(final Camera cam, final HeroezSettings heroezSettings) {
        super(cam);
        zoomSpeed = heroezSettings.getPropertyFloat("camera.gameView.flyBy.zoomSpeed");
        moveSpeed = heroezSettings.getPropertyFloat("camera.gameView.flyBy.moveSpeed");
        anotherZoomSpeed = heroezSettings.getPropertyFloat("camera.gameView.flyBy.anotherZoomSpeed");
    }

    @Override
    public void registerWithInput(InputManager inputManager) {
        super.registerWithInput(inputManager);
        reverseZoom(inputManager);
        setMoveSpeed(moveSpeed);
        setDragToRotate(true);
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

        float fovY = FastMath.atan(h / near) / (FastMath.DEG_TO_RAD * anotherZoomSpeed);

        fovY += value * zoomSpeed;

        h = FastMath.tan(fovY * FastMath.DEG_TO_RAD * anotherZoomSpeed) * near;
        w = h * aspect;

        cam.setFrustumTop(h);
        cam.setFrustumBottom(-h);
        cam.setFrustumLeft(-w);
        cam.setFrustumRight(w);

    }

    public float getZoomSpeed() {
        return zoomSpeed;
    }

    public void setZoomSpeed(float zoomSpeed) {
        this.zoomSpeed = zoomSpeed;
    }

    private void reverseZoom(final InputManager inputManager) {
        inputManager.deleteMapping("FLYCAM_ZoomIn");
        inputManager.deleteMapping("FLYCAM_ZoomOut");
        // add my own - reverse
        inputManager.addMapping("FLYCAM_ZoomIn", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("FLYCAM_ZoomOut", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addListener(this, "FLYCAM_ZoomIn", "FLYCAM_ZoomOut");
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!enabled)
            return;
        if (name.equals("FLYCAM_Forward")) {
            moveCameraHorizontally(value, false);
        } else if (name.equals("FLYCAM_Backward")) {
            moveCameraHorizontally(-value, false);
        } else {
            super.onAnalog(name, value, tpf);
        }
    }

    protected void moveCameraHorizontally(float value, boolean sideways) {
        final Vector3f vel = new Vector3f();
        final Vector3f pos = cam.getLocation().clone();
        final float y = pos.getY();
        if (sideways) {
            cam.getLeft(vel);
        } else {
            cam.getDirection(vel);
        }
        vel.multLocal(value * moveSpeed);
        if (motionAllowed != null)
            motionAllowed.checkMotionAllowed(pos, vel);
        else
            pos.addLocal(vel);
        //keep Y unchanged - we're moving horizontally
        if (!sideways) {
            pos.setY(y);
        }
        cam.setLocation(pos);
    }
}
