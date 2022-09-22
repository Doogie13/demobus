package demo.knight.demobus.ievent;

import demo.knight.demobus.event.IDemoVent;

import java.util.Random;

/**
 * @author Doogie13
 * @since 22/09/2022
 */
public class OtherVent implements IDemoVent {

    boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
