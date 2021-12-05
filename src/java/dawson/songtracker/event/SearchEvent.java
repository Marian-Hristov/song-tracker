package dawson.songtracker.event;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEvent extends Event {

    public static final EventType<SearchEvent> SEARCH_EVENT = new EventType<>(Event.ANY, "SEARCH_EVENT");
    public static final EventType<SearchCompilationEvent> SEARCH_COMPILATION_EVENT = new EventType<>(Event.ANY, "SEARCH_COMPILATION_EVENT");
    public static final EventType<SearchRecordEvent> SEARCH_RECORD_EVENT = new EventType<>(Event.ANY, "SEARCH_RECORD_EVENT");
    public final String message;
    public final boolean released;
    public final boolean unreleased;

    public class SearchCompilationEvent extends SearchEvent {
        public SearchCompilationEvent(String message, boolean released, boolean unreleased) {
            super(message, released, unreleased);
        }
    }

    public class SearchRecordEvent extends SearchEvent {
        public SearchRecordEvent(String message, boolean released, boolean unreleased) {
            super(message, released, unreleased);
        }
    }

    public SearchEvent(String message, boolean released, boolean unreleased) {
        super(SEARCH_EVENT);
        this.message = message;
        this.released = released;
        this.unreleased = unreleased;
    }
}
