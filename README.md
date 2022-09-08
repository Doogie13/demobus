# DemoBus
###### An event bus written in java for the JVM

# Adding to your project
to add DemoBus to your own Gradle project, add the following to your build.gradle
```groovy
repositories {
    maven { url = 'https://jitpack.io/' }
}

dependencies {
    implementation 'com.github.Doogie13:demobus:1.0'
}
```
More information for other build tools can be found [here](https://jitpack.io/).

# Usage

## Creating a DemoBus
To implement DemoBus, there are 2 constructors you can use.

This first constructor will create a new DemoBus instance with no crash handling. A RuntimeException holding any exceptions thrown will be thrown.

```java
public static final DemoBus EVENT_BUS = new DemoBus();
```

This second constructor allows you to handle exceptions without crashing.

```java
public static final DemoBus EVENT_BUS = new DemoBus(t -> SomeLogger::error);
```

## Creating an event
Events are created by extending the DemoVent class.
```java

import demo.knight.demobus.event.DemoVent;

public class ExampleEvent extends DemoVent {

    @Override
    public boolean isCancellable() {
        return false;
    }
    
}
```
Overriding isCancellable will allow you to prevent cancellation of an event. Events are cancellable by default.

## Listening to events
To listen to events, you must first register their parent object. To listen to events, create a method annotated @DemoListen with one parameter. This parameter must be for `<? extends DemoVent>`.

```java
import demo.knight.demobus.event.*;

public class Listening {

    public Listening() {
        DemoBus.register(this);
    }

    @DemoListen
    public void onDemoVent(DemoVent event) {

        // This will receive all objects extending DemoVent

    }

    @DemoListen
    public void onDemoVent(CustomEvent event) {

        // This will only receive only objects extending CustomEvent
        if (event.isCancellable())
            event.cancel();

    }

    static class CustomEvent extends DemoVent {
        
        // This is a class extending DemoVent. You will likely wish to add your own variables here which can be modified by listeners
        
    }

}
```