package net.hermesprime.rpg.heroez.view;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import net.hermesprime.rpg.heroez.model.player.Player;
import net.hermesprime.rpg.heroez.view.plain.PlainView;
import net.hermesprime.rpg.heroez.view.util.Axis;

import java.util.HashSet;

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
    }

    public void initGui() {
    }

    public void initObjects() {
        Player player = new Player();
        HashSet<Player> players = new HashSet<Player>();
        players.add(player);
        PlainView.addPlayers(rootNode, assetManager, players);
    }
}
