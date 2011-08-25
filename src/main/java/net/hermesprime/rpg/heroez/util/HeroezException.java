package net.hermesprime.rpg.heroez.util;

/**
 * User: moore
 * Date: 8/25/11
 * Time: 7:59 PM
 */
public class HeroezException extends RuntimeException {

    public HeroezException() {
    }

    public HeroezException(String message) {
        super(message);
    }

    public HeroezException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeroezException(Throwable cause) {
        super(cause);
    }
}
