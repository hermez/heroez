package net.hermesprime.rpg.heroez.model.player;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 2:30 AM
 */
public class Player {

    private final Health health;

    public Player() {
        this.health = new Health();
    }

    public Health getHealth() {
        return health;
    }
}
