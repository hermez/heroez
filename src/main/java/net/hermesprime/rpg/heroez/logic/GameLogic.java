package net.hermesprime.rpg.heroez.logic;

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


    public void initPlayers() {
        players.add(new Player());
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }
}
