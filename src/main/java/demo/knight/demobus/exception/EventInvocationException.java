package demo.knight.demobus.exception;

/**
 * @author Doogie13
 * @since 08/09/2022
 */
public class EventInvocationException extends Exception {
    public EventInvocationException(Throwable t) {
        super(t);
    }
}
