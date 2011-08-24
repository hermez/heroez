package net.hermesprime.rpg.heroez.view.plain;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Quad;
import net.hermesprime.rpg.heroez.model.player.Player;
import net.hermesprime.rpg.heroez.view.util.QuaternionSheet;

import java.util.Set;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 5:23 AM
 */
public class PlainView {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    public static void fillTerrain(final Node rootNode, final AssetManager assetManager) {
        final Node ground = addGround(rootNode, assetManager);

        rootNode.attachChild(getBox(assetManager, 2.5f, 0, 0, ColorRGBA.Pink));
        rootNode.attachChild(getBox(assetManager, -2.5f, 0, 0));
        rootNode.attachChild(getBox(assetManager, 0, 0, 2.5f, ColorRGBA.Cyan));
        rootNode.attachChild(getBox(assetManager, 0, 0, -2.5f));

        rootNode.attachChild(getBox(assetManager, 5, 0, 0));
        rootNode.attachChild(getBox(assetManager, -5, 0, 0, ColorRGBA.Red));
        rootNode.attachChild(getBox(assetManager, 0, 0, 5));
        rootNode.attachChild(getBox(assetManager, 0, 0, -5, ColorRGBA.Blue));
    }

    private static Geometry getBox(final AssetManager assetManager, final float x, final float y, final float z) {
        return getBox(assetManager, x, y, z, ColorRGBA.Brown);
    }

    private static Geometry getBox(final AssetManager assetManager, final float x, final float y, final float z, final ColorRGBA brown) {
        final Box box1 = new Box(new Vector3f(x, y, z), 0.05f, 0.05f, 0.05f);
        final Geometry blue = new Geometry("Box", box1);
        final Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", brown);
        blue.setMaterial(mat1);
        return blue;
    }

    private static Node addGround(Node rootNode, AssetManager assetManager) {
        final Quad seaLevel = new Quad(2 * WIDTH, 2 * HEIGHT);

        final Material material = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        material.setColor("Color", ColorRGBA.Green);

        final Geometry geometry = new Geometry("seaLevel", seaLevel);

        final Node seaLevelNode = new Node("seaLevel");
        rootNode.attachChild(seaLevelNode);

        seaLevelNode.attachChild(geometry);
        seaLevelNode.setMaterial(material);
        seaLevelNode.setLocalRotation(QuaternionSheet.PITCH_270);
        seaLevelNode.setLocalTranslation(new Vector3f(-WIDTH, 0, HEIGHT));

        return seaLevelNode;
    }

    public static void addPlayers(final Node rootNode, final AssetManager assetManager, final Set<Player> players) {
        rootNode.attachChild(getPlayer(assetManager, 1f, 1, 0, ColorRGBA.Orange));


    }

    private static Geometry getPlayer(final AssetManager assetManager, final float posx, final float posy, final float posz, final ColorRGBA colorRGBA) {
        final Cylinder cylinder = new Cylinder(10, 10, 0.1f, 0.2f, true);
        final Geometry geometry = new Geometry("Box", cylinder);
        final Material material = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        material.setColor("Color", colorRGBA);
        geometry.setMaterial(material);
        geometry.move(new Vector3f(posx, posy, posz));
        geometry.setLocalRotation(QuaternionSheet.PITCH_90);
        return geometry;
    }
}
