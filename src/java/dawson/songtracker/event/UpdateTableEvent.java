package dawson.songtracker.event;

import javafx.event.Event;
import javafx.event.EventType;

public class UpdateTableEvent extends Event {
    public static final EventType<UpdateTableEvent> UPDATE_TABLE_EVENT = new EventType<>(Event.ANY, "UPDATE_TABLE");

    public UpdateTableEvent() {
        super(UPDATE_TABLE_EVENT);
    }
}
