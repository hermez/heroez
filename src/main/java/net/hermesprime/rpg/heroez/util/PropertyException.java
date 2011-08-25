package net.hermesprime.rpg.heroez.util;

/**
 * User: moore
 * Date: 8/25/11
 * Time: 8:26 PM
 */
public class PropertyException extends RuntimeException {
    public PropertyException() {
    }

    public PropertyException(String message) {
        super(message);
    }

    public PropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyException(Throwable cause) {
        super(cause);
    }
}
