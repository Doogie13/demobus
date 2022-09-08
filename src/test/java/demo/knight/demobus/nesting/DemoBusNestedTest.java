package demo.knight.demobus.nesting;

import demo.knight.demobus.DemoBus;
import demo.knight.demobus.event.DemoListen;
import demo.knight.demobus.event.DemoVent;
import org.junit.jupiter.api.Test;

/**
 * @author Doogie13
 * @since 08/09/2022
 */
public class DemoBusNestedTest {

    @Test
    public void test() {

        DemoBus bus = new DemoBus();
        bus.register(this);

        bus.call(new NestedEventHolder.Pre());

    }

    @DemoListen
    public void listen(DemoVent vent) {
        System.out.println("DemoVent received!");
    }

    @DemoListen
    public void all(NestedEventHolder vent) {
        System.out.println("Holder received!");
    }

    @DemoListen
    public void pre(NestedEventHolder.Pre pre) {
        System.out.println("Pre received!");
    }

    @DemoListen
    public void post(NestedEventHolder.Post post) {
        System.out.println("Post received!");
    }

}
