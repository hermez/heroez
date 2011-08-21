package net.hermesprime.rpg.heroez.view;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import net.hermesprime.rpg.heroez.view.plain.PlainView;
import net.hermesprime.rpg.heroez.view.util.Axis;

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
        PlainView.fillTerrain(rootNode, assetManager);
        Axis.fillView(rootNode, assetManager);


////        // create a blue box at coordinates (1,-1,1)
//        Box box1 = new Box(new Vector3f(0, 0, 0), 0.5f, 0.5f, 0.5f);
//        Geometry blue = new Geometry("Box", box1);
//        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/WireColor.j3md");
//        mat1.setColor("Color", ColorRGBA.Brown);
//        blue.setMaterial(mat1);
////        // create a red box straight above the blue one at (1,3,1)
////        Box box2 = new Box(new Vector3f(1, 3, 1), 1, 1, 1);
////        Geometry red = new Geometry("Box", box2);
////        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
////        mat2.setColor("Color", ColorRGBA.Red);
////        red.setMaterial(mat2);
////        // create a pivot node at (0,0,0) and attach it to root
//        Node pivot = new Node("pivot");
//        rootNode.attachChild(pivot);
//        // attach the two boxes to the *pivot* node!
//        pivot.attachChild(blue);
////        pivot.attachChild(red);
////        // rotate pivot node: Both boxes have rotated!
////        pivot.rotate(0.4f, 0.4f, 0.0f);


        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    }

    public void initGui() {
    }

    public void initGameObjects(final String scenarioId) {
    }
}
