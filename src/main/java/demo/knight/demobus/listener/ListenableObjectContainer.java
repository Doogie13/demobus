package demo.knight.demobus.listener;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.IDemoVent;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.exception.InvalidListenerException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * An entry containing a list of all listeners in a class or object along with whether the listener is currently listening.
 * @author Doogie13
 * @since 06/09/2022
 */
public class ListenableObjectContainer {

    public boolean listening = true;
    private final Object object;
    private final List<Listener> listeners = new ArrayList<>();

    /**
     * @param object the object or class to call listeners within.
     * */
    public ListenableObjectContainer(Object object) throws IllegalAccessException {
        this.object = object;

        for (Method method : object.getClass().getMethods()) {

            if (method.isAnnotationPresent(DemoListen.class)) {

                if (method.getParameterTypes().length == 1) {

                    DemoListen annotation = method.getAnnotation(DemoListen.class);
                    listeners.add(new Listener(object, method, annotation.receiveCancelled(), annotation.priority()));

                } else
                    DemoBus.crash(new InvalidListenerException("Invalid annotated Listener!"));

            }

        }

        listeners.sort(Comparator.comparingInt(o -> o.getPriority().ordinal()));

    }

    /**
     * Returns the object or class we are listening within.
     * */
    public Object getObject() {
        return object;
    }

    /**
     * Calls an event to all listeners if we are listening.
     * */
    public void call(IDemoVent object) {

        if (listening)
            for (Listener l : listeners)
                l.handleCall(object);

    }

    @Override
    public String toString() {
        return "ListenableObjectContainer{" +
                "listening=" + listening +
                ", object=" + object.getClass().getSimpleName() +
                ", listeners=" + listeners +
                '}';
    }
}
