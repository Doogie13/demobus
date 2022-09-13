package demo.knight.demobus.exception;

/**
 * @author Doogie13
 * @since 08/09/2022
 */
public class EventInvocationException extends Exception {

    final Object parent;

    public EventInvocationException(Object parent, Throwable t) {
        super(t);
        this.parent = parent;
    }

    public Object getParent() {
        return parent;
    }
}
