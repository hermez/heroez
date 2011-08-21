package net.hermesprime.rpg.heroez.model.player;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 2:31 AM
 */
public class Health {

    private int hitPoints;

    public void wounded(final int hitPoints) {
        this.hitPoints -= hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
