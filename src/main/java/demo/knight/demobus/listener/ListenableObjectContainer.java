package demo.knight.demobus.listener;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.DemoVent;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.exception.InvalidListenerException;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * An entry containing a list of all listeners in a class or object along with whether the listener is currently listening.
 * @author Doogie13
 * @since 06/09/2022
 */
public class ListenableObjectContainer {

    public boolean listening = true;
    private final Object object;
    private final TreeSet<Listener> listeners = new TreeSet<>(Comparator.comparingInt(o -> o.getPriority().ordinal()));

    /**
     * @param object the object or class to call listeners within.
     * */
    public ListenableObjectContainer(Object object) throws IllegalAccessException {
        this.object = object;

        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(DemoListen.class))
                if (method.getParameterTypes().length == 1) {

                    DemoListen annotation = method.getAnnotation(DemoListen.class);
                    listeners.add(new Listener(object, method, annotation.receiveCancelled(), annotation.priority()));

                } else
                    DemoBus.crash(new InvalidListenerException("Invalid annotated Listener!"));

        }

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
    public void call(DemoVent object) {

        if (listening)
            for (Listener l : listeners)
                l.handleCall(object);

    }

}
