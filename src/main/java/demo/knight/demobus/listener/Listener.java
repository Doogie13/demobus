package demo.knight.demobus.listener;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.event.IDemoVent;
import demo.knight.demobus.exception.EventInvocationException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

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

        boolean hasIDemoVent = IDemoVent.class.isAssignableFrom(methodType);

        // check to see that this is listening for DemoVents
        if (!hasIDemoVent)
            throw new RuntimeException(String.format("Subscribed method not assignable from an IDemoVent. Stop subscribing method in %s to %s.", method.getDeclaringClass().getName(), parameterTypes[0].getName()));

    }

    /**
     * Calls the event, only if the event we are calling corresponds to what we are listening for
     * */
    public void handleCall(IDemoVent event) {

        if (!(event.isCancelled() && !listenToCancelled) && methodType.isAssignableFrom(event.getClass()) && checkPrimitive(event))
            call(event);

    }

    private boolean checkPrimitive(IDemoVent event) {
        // check we have matching type params

        TypeVariable<? extends Class<?>>[] typeParameters = methodType.getTypeParameters();
        TypeVariable<? extends Class<? extends IDemoVent>>[] typeParameters1 = event.getClass().getTypeParameters();

        if (typeParameters.length != typeParameters1.length)
            return false;
        else {
            for (int i = 0; i < typeParameters.length; i++) {
                if (!typeParameters[i].getClass().equals(typeParameters1[i].getClass()))
                    return false;
            }
        }
        return true;

    }

    /**
     * Invokes the listener
     * */
    void call(IDemoVent event) {

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

    @Override
    public final String toString() {
        return "Listener{" +
                "priority=" + priority.name() +
                ", listenToCancelled=" + listenToCancelled +
                ", method=" + method +
                ", methodType=" + methodType +
                ", parent=" + parent.getClass().getSimpleName() +
                '}';
    }
}
