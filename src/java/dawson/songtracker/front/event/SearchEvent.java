package dawson.songtracker.front.event;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEvent extends Event {

    public static final EventType<SearchEvent> SEARCH_EVENT = new EventType<>(Event.ANY, "SEARCH_EVENT");
    public final String message;

    public SearchEvent(String message) {
        super(SEARCH_EVENT);
        this.message = message.trim();
    }
}
