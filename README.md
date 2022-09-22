# DemoBus
###### An event bus written in java for the JVM

## Adding to your project
to add DemoBus to your own Gradle project, add the following to your build.gradle
```groovy
repositories {
    maven { url = 'https://jitpack.io/' }
}

dependencies {
    implementation 'com.github.doogie13:demobus:-SNAPSHOT'
}
```
More information for other build tools can be found [here](https://jitpack.io/).

## Usage

### Creating a DemoBus
To implement DemoBus, there are 2 constructors you may use.

This first constructor will create a new DemoBus instance with no crash handling. A RuntimeException will be thrown for any exceptions and errors.

```java
public static final DemoBus EVENT_BUS = new DemoBus();
```

This second constructor allows you to handle exceptions without crashing.

```java
public static final DemoBus EVENT_BUS = new DemoBus(t -> SomeLogger::error);
```

### Creating an event
To create an event, create a class which implements IDemoVent.
```java

import demo.knight.demobus.event.IDemoVent;

public class Event implements IDemoVent {

    @Override
    public boolean isCancelled() {
        return false;
    }

}
```

### Listening to events
To listen to events, you must first register their parent object. To listen to events, create a method annotated @DemoListen with only one parameter. This parameter must be for a valid event.

```java
import demo.knight.demobus.event.*;

public class Listening {

    public Listening() {
        DemoBus.register(this);
    }

    @DemoListen
    public void onDemoVent(IDemoVent event) {

        // This will receive all events

    }

    @DemoListen
    public void onDemoVent(CustomEvent event) {

        // This will only receive only CustomEvents
        if (event.isCancellable())
            event.cancel();

    }

    // this is the format one would expect a typical base event to follow
    static class CustomEvent implements IDemoVent {

        private boolean isCancelled = false;

        @Override
        public void isCancelled() {
            return cancellable;
        }

        public void setCancelled(boolean cancelled) {
            isCancelled = cancelled;
        }
        
    }

}
```

It is recommended that you create a base event and have other events extending that, rather than each event implementing IDemoVent
