package net.hermesprime.rpg.heroez.view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 12:21 AM
 */
public class GameView {

    private final Node rootNode;
    private final AssetManager assetManager;

    public GameView(final Node rootNode, final AssetManager assetManager) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }

    public void initTerrain() {
        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        // create a blue box at coordinates (1,-1,1)
        Box box1 = new Box(new Vector3f(1, -1, 1), 1, 1, 1);
        Geometry blue = new Geometry("Box", box1);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat1);
        // create a red box straight above the blue one at (1,3,1)
        Box box2 = new Box(new Vector3f(1, 3, 1), 1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);
        // create a pivot node at (0,0,0) and attach it to root
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot);
        // attach the two boxes to the *pivot* node!
        pivot.attachChild(blue);
        pivot.attachChild(red);
        // rotate pivot node: Both boxes have rotated!
        pivot.rotate(0.4f, 0.4f, 0.0f);

        Vector3f origin = Vector3f.ZERO;
        Vector3f x10 = new Vector3f(10, 0, 0);
        Vector3f y10 = new Vector3f(0, 10, 0);
        Vector3f z10 = new Vector3f(0, 0, 10);
        Vector3f x_10 = new Vector3f(-10, 0, 0);
        Vector3f y_10 = new Vector3f(0, -10, 0);
        Vector3f z_10 = new Vector3f(0, 0, -10);

        Line x = new Line(origin, x10);
        Line y = new Line(origin, y10);
        Line z = new Line(origin, z10);
        Line x_ = new Line(origin, x_10);
        Line y_ = new Line(origin, y_10);
        Line z_ = new Line(origin, z_10);

        Material matx = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matx.setColor("Color", ColorRGBA.Pink);
        Material maty = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        maty.setColor("Color", ColorRGBA.Yellow);
        Material matz = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matz.setColor("Color", ColorRGBA.Cyan);

        Material matx_ = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matx.setColor("Color", ColorRGBA.Red);
        Material maty_ = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        maty.setColor("Color", ColorRGBA.Green);
        Material matz_ = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matz.setColor("Color", ColorRGBA.Blue);

        Geometry geomX = new Geometry("x", x);
        geomX.setMaterial(matx);
        Geometry geomY = new Geometry("y", y);
        geomY.setMaterial(maty);
        Geometry geomZ = new Geometry("z", z);
        geomZ.setMaterial(matz);
        Geometry geomX_ = new Geometry("-x", x);
        geomX_.setMaterial(matx_);
        Geometry geomY_ = new Geometry("-y", y);
        geomY_.setMaterial(maty_);
        Geometry geomZ_ = new Geometry("-z", z);
        geomZ_.setMaterial(matz_);

        rootNode.attachChild(geomX);
        rootNode.attachChild(geomY);
        rootNode.attachChild(geomZ);
        rootNode.attachChild(geomX_);
        rootNode.attachChild(geomY_);
        rootNode.attachChild(geomZ_);
    }

    public void initGui() {
    }

    public void initGameObjects(final String scenarioId) {
    }
}
