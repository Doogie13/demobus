package demo.knight.demobus.base;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.event.DemoVent;
import org.junit.jupiter.api.Test;

/**
 * @author Doogie13
 * @since 07/09/2022
 */
public class DemoBusTest {

    DemoBus demoBus;

    /**
     * Tests listener registration, event delivery, event cancellation, listener priority, and the receiveCancelled parameter
     * */
    @Test
    public void testDemoBus() {

        demoBus = new DemoBus();
        demoBus.register(this);

        TestEvent event = new TestEvent("This is a test... ");

        System.out.println("Text before calling: \"" + event.getExample() + "\"");

        boolean cancelled = demoBus.call(event);

        if (!cancelled) {
            System.out.println("Event not cancelled...");
            throw new RuntimeException("This should never happen");
        }

        System.out.println("Text after calling: \"" + event.getExample() + "\"");

        System.out.println("Calling a new event...");

        demoBus.call(new DemoVent());

        System.out.println("Unregistering this and calling a new event which should be ignored...");

        demoBus.unregister(this);

        // this should never be received
        demoBus.call(new DemoVent());

    }

    @DemoListen(priority = DemoListen.Priority.HIGH)
    public void onAnyEvent(DemoVent event) {

        System.out.println("This listener will be called for every event. You can still determine the event class with the instanceof token.");

    }

    @DemoListen
    public void onTestEvent1(TestEvent testEvent) {

        testEvent.setExample(testEvent.getExample() + "Success after posting, now cancelling... ");

        testEvent.cancel();

    }

    @DemoListen(priority = DemoListen.Priority.LOW)
    public void onTestEvent2(TestEvent testEvent) {

        // this listener should never be called as the previous listener just cancelled it
        testEvent.setExample(testEvent.getExample() + "Failure after illegally receiving a cancelled event!");
        throw new RuntimeException("This should never happen");

    }

    @DemoListen(priority = DemoListen.Priority.LOWEST, receiveCancelled = true)
    public void onTestEvent3(TestEvent testEvent) {

        // this listener should never be called as the previous listener just cancelled it
        testEvent.setExample(testEvent.getExample() + "Success after cancellation!");

    }

}