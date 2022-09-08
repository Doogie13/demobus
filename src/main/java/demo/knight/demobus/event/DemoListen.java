package demo.knight.demobus.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Registers a listener to be executed if the parent object or class is registered.
 * You may wish to add an exception for methods with this annotation to be checked for whether the method is used.
 * @author Doogie13
 * @since 06/09/2022
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DemoListen {

    /**
     * The listener's priority
     * */
    Priority priority() default Priority.NORMAL;

    /**
     * Whether the listener should receive cancelled events
     * */
    boolean receiveCancelled() default false;

    /**
     * An enum representing the order in which events are executed.
     * */
    @SuppressWarnings("unused")
    enum Priority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST
    }

}
