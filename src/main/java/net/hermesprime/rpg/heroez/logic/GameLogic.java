package net.hermesprime.rpg.heroez.logic;

import net.hermesprime.rpg.heroez.model.map.Map;
import net.hermesprime.rpg.heroez.model.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 2:35 AM
 */
public class GameLogic {

    private final Set<Player> players = new HashSet<Player>();
    private Map map;
    private Rules rules;
    private GameObjects gameObjects = new GameObjects();

    public void setMap(final Map map) {
        this.map = map;
    }

    public void initPlayers() {
        players.add(new Player());
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }
}
