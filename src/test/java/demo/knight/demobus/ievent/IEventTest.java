package demo.knight.demobus.ievent;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.DemoVent;
import demo.knight.demobus.event.IDemoVent;
import org.junit.jupiter.api.Test;

/**
 * @author Doogie13
 * @since 22/09/2022
 */
public class IEventTest {

    @Test
    public void run() {

        DemoBus bus = new DemoBus();

        bus.register(this);

        bus.call(new OtherVent());

    }

    @DemoListen
    public void onOtherVent(OtherVent otherVent) {

        System.out.println("OtherVent received");

    }

    @DemoListen
    public void onIDemoVent(IDemoVent demoVent) {

        System.out.println("IDemoVent Received");

    }

    @DemoListen
    public void onDemoVent(DemoVent demoVent) {

        System.out.println("DemoVent Received");

    }

}
