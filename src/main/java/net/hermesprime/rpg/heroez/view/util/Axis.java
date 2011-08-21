package net.hermesprime.rpg.heroez.view.util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 5:28 AM
 */
public class Axis {

    public static void fillView(final Node rootNode, final AssetManager assetManager) {
        Vector3f origin = Vector3f.ZERO;

        Material matx = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        matx.setColor("Color", ColorRGBA.Pink);
        Material maty = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        maty.setColor("Color", ColorRGBA.Yellow);
        Material matz = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matz.setColor("Color", ColorRGBA.Cyan);

        Material matx_ = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        matx_.setColor("Color", ColorRGBA.Red);
        Material maty_ = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        maty_.setColor("Color", ColorRGBA.Green);
        Material matz_ = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matz_.setColor("Color", ColorRGBA.Blue);

        Geometry geomX = new Geometry("x", new Line(origin, new Vector3f(5, 0, 0)));
        Geometry geomY = new Geometry("y", new Line(origin, new Vector3f(0, 5, 0)));
        //Geometry geomZ = new Geometry("z", new Line(new Vector3f(1, 0, 0), new Vector3f(0, 0, 5)));
        Geometry geomZ = new Geometry("z", new Line(origin, new Vector3f(0, 0, 5)));
        Geometry geomX_ = new Geometry("-x", new Line(origin, new Vector3f(-5, 0, 0)));
        Geometry geomY_ = new Geometry("-y", new Line(origin, new Vector3f(0, -5, 0)));
        //Geometry geomZ_ = new Geometry("-z", new Line(new Vector3f(-1, 0, 0), new Vector3f(0, 0, -5)));
        Geometry geomZ_ = new Geometry("-z", new Line(origin, new Vector3f(0, 0, -5)));

        geomX.setMaterial(matx);
        geomY.setMaterial(maty);
        geomZ.setMaterial(matz);
        geomX_.setMaterial(matx_);
        geomY_.setMaterial(maty_);
        geomZ_.setMaterial(matz_);

        rootNode.attachChild(geomX);
        rootNode.attachChild(geomY);
        rootNode.attachChild(geomZ);
        rootNode.attachChild(geomX_);
        rootNode.attachChild(geomY_);
        rootNode.attachChild(geomZ_);
    }
}
