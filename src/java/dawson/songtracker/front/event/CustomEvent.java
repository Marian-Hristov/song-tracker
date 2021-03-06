package dawson.songtracker.front.event;

import dawson.songtracker.front.eventhandler.CustomEventHandler;
import javafx.event.Event;
import javafx.event.EventType;

public abstract class CustomEvent extends Event {
    public static final EventType<CustomEvent> CUSTOM_EVENT_TYPE = new EventType<>(ANY);

    public CustomEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(CustomEventHandler handler);

}
