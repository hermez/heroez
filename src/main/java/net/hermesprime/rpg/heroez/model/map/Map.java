package net.hermesprime.rpg.heroez.model.map;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 6:51 AM
 */
public class Map {

    /**
     * x
     */
    private int length;

    /**
     * z
     */
    private int width;

    public Map(int length, int width) {
        this.length = length;
        this.width = width;
    }

    /**
     * Load from source (file, net, db).
     *
     * @return
     */
    public static Map load() {
        //todo
        return new Map(5, 5);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
