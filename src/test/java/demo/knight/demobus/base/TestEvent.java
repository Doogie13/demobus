package demo.knight.demobus.base;

import demo.knight.demobus.event.DemoVent;
import demo.knight.demobus.event.IDemoVent;

/**
 * A test event with a manipulatable String
 *
 * @author Doogie13
 * @since 07/09/2022
 */
public class TestEvent extends DemoVent {

    public TestEvent(String input) {

        this.example = input;

    }

    String example;

    public void setExample(String example) {
        this.example = example;
    }

    public String getExample() {
        return example;
    }

}
