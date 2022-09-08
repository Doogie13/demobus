package demo.knight.demobus.event;

/**
 * A class for an event. You may extend this to create your own events.
 * @author Doogie13
 * @since 06/09/2022
 */
public class DemoVent {

    private boolean isCancelled = false;

    /**
     * Check if an event is cancelled
     * */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Set an event to be cancelled
     * @param cancelled whether to cancel the event
     * */
    public void setCancelled(boolean cancelled) {

        if (!isCancellable())
            throw new RuntimeException("Attempted to cancel an uncancellable event!");

        isCancelled = cancelled;

    }

    /**
     * Shorthand way to cancel a method
     * */
    public void cancel() {
        setCancelled(true);
    }

    public boolean isCancellable() {
        return true;
    }

}
