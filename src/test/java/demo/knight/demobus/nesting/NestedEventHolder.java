package demo.knight.demobus.nesting;

import demo.knight.demobus.DemoVent;

/**
 * @author Doogie13
 * @since 08/09/2022
 */
public class NestedEventHolder extends DemoVent {

    static class Pre extends NestedEventHolder {

    }

    static class Post extends NestedEventHolder {


    }

}
