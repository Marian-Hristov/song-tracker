package dawson.songtracker.event;

import javafx.event.Event;
import javafx.event.EventType;

public class UpdateEntityEvent extends Event {
    public static final EventType<UpdateEntityEvent> UPDATE_ENTITY = new EventType<>(Event.ANY, "UPDATE_ENTITY");

    public UpdateEntityEvent() {
        super(UPDATE_ENTITY);
    }
}
