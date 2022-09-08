package demo.knight.demobus;

import demo.knight.demobus.event.DemoVent;
import demo.knight.demobus.listener.ListenableObjectContainer;

import java.util.*;

/**
 * An event bus created in Java for JVM languages
 * @author Doogie13
 * @since 06/09/2022
 */
public class DemoBus {

    private final CloneableHashSet<ListenableObjectContainer> listenableMap = new CloneableHashSet<>();

    /**
     * Registers an object, allows it to listen
     * */
    public void register(Object object) {

        for (ListenableObjectContainer entry : listenableMap) {

            if (entry.getObject().equals(object)) {
                entry.listening = true;
                return;
            }

        }

        try {

            listenableMap.add(new ListenableObjectContainer(object));

        } catch (IllegalAccessException illegalAccessException) {

            throw new RuntimeException(illegalAccessException);

        }

    }

    /**
     * Unregisters an object, prevents it from listening
     * */
    public void unregister(Object object) {

        for (ListenableObjectContainer entry : listenableMap) {

            if (entry.getObject().equals(object)) {
                entry.listening = false;
                return;
            }

        }

    }

    /**
     * Calls an event to be passed to listening methods
     * @param demoVent DemoVent object to be passed
     * */
    public boolean call(DemoVent demoVent) {

        for (ListenableObjectContainer entry : listenableMap.clone())
            entry.call(demoVent);

        return demoVent.isCancelled();

    }

    /**
     * A HashSet which may be cloned
     * */
    static class CloneableHashSet<K> extends HashSet<K> {
        @Override
        @SuppressWarnings("unchecked")
        public CloneableHashSet<K> clone() {
            return (CloneableHashSet<K>) super.clone();
        }
    }

}
