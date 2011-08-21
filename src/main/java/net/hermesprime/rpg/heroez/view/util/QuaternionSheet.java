package net.hermesprime.rpg.heroez.view.util;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 * Quaternion cheat-sheets.
 * <p/>
 * User: moore
 * Date: 8/21/11
 * Time: 6:13 AM
 */
public class QuaternionSheet {

    public final static Quaternion ROLL_90;
    public final static Quaternion ROLL_180;

    public final static Quaternion TURN_90;
    public final static Quaternion TURN_180;

    public final static Quaternion PITCH_90;
    public final static Quaternion PITCH_180;
    public final static Quaternion PITCH_270;

    static {
        ROLL_90 = new Quaternion();
        ROLL_90.fromAngleAxis(FastMath.HALF_PI, new Vector3f(0, 0, 1));
        ROLL_180 = new Quaternion();
        ROLL_180.fromAngleAxis(FastMath.PI, new Vector3f(0, 0, 1));

        TURN_90 = new Quaternion();
        TURN_90.fromAngleAxis(FastMath.HALF_PI, new Vector3f(0, 1, 0));
        TURN_180 = new Quaternion();
        TURN_180.fromAngleAxis(FastMath.PI, new Vector3f(0, 1, 0));

        PITCH_90 = new Quaternion();
        PITCH_90.fromAngleAxis(FastMath.HALF_PI, new Vector3f(1, 0, 0));
        PITCH_180 = new Quaternion();
        PITCH_180.fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0));
        PITCH_270 = new Quaternion();
        PITCH_270.fromAngleAxis(FastMath.PI + FastMath.HALF_PI, new Vector3f(1, 0, 0));
    }
}
