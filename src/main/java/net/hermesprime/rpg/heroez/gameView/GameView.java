package net.hermesprime.rpg.heroez.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

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
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }

    public void initGui() {
    }

    public void initGameObjects(final String scenarioId) {
    }
}
