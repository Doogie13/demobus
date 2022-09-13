package demo.knight.demobus.listener;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.event.DemoVent;
import demo.knight.demobus.exception.EventInvocationException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * An interface for listeners to use. This allows the event bus to work regardless of listener type.
 * @author Doogie13
 * @since 06/09/2022
 */
public class Listener {

    public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final DemoListen.Priority priority;
    private final boolean listenToCancelled;

    final MethodHandle method;
    final Class<?> methodType;
    final Object parent;

    public Listener(Object parent, Method method, boolean listenToCancelled, DemoListen.Priority priority) throws IllegalAccessException {

        this.parent = parent;
        this.listenToCancelled = listenToCancelled;
        this.priority = priority;

        this.method = LOOKUP.unreflect(method);

        Class<?>[] parameterTypes = method.getParameterTypes();
        methodType = parameterTypes[0];

        // if there is not 1 parameter, we must have an invalid listener
        if (parameterTypes.length != 1)
            throw new RuntimeException("Parameter type length is not 1");

        Set<Class<?>> superTypes = new HashSet<>();
        Class<?> cls = parameterTypes[0];

        // get all superclasses
        superTypes.add(cls);
        while (cls != DemoVent.class) {
            try {
                cls = cls.getSuperclass();
                superTypes.add(cls);
            } catch (NullPointerException e) {
                break;
            }
        }

        // check to see that this is listening for DemoVents
        if (!superTypes.contains(DemoVent.class))
            throw new RuntimeException(String.format("Subscribed method not assignable from a DemoVent. Stop subscribing method in %s to %s.", method.getDeclaringClass().getName(), parameterTypes[0].getName()));

    }

    /**
     * Calls the event, only if the event we are calling corresponds to what we are listening for
     * */
    public void handleCall(DemoVent event) {

        if (!(event.isCancelled() && !listenToCancelled) && methodType.isAssignableFrom(event.getClass()))
            call(event);

    }

    /**
     * Invokes the listener
     * */
    void call(DemoVent event) {

        try {

            method.invoke(parent, event);

        } catch (Throwable t) {

            DemoBus.crash(new EventInvocationException(parent, t));

        }

    }

    /**
     * Returns this listener's priority.
     * */
    public DemoListen.Priority getPriority() {
        return priority;
    }

}
