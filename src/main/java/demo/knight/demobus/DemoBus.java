package demo.knight.demobus;

import demo.knight.demobus.event.IDemoVent;
import demo.knight.demobus.listener.ListenableObjectContainer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * An event bus created in Java for JVM languages
 * @author Doogie13
 * @since 06/09/2022
 */
public class DemoBus {

    // lol
    private final Set<ListenableObjectContainer> listenableMap = new CopyOnWriteArraySet<>();
    private static Consumer<Throwable> crashHandler = t -> {throw new RuntimeException(t);};

    /**
     * Creates a new DemoBus instance with no crash handling
     * */
    public DemoBus() {
    }

    /**
     * Creates a new DemoBus instance with custom crash handling
     * */
    public DemoBus(Consumer<Throwable> crashHandler) {
        DemoBus.crashHandler = crashHandler;
    }

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

            crash(illegalAccessException);

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
    public boolean call(IDemoVent demoVent) {

        for (ListenableObjectContainer entry : listenableMap)
            entry.call(demoVent);

        return demoVent.isCancelled();

    }

    public static void crash(Throwable t) {
        crashHandler.accept(t);
    }

}
