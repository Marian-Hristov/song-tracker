package dawson.songtracker.eventhandler;

import dawson.songtracker.event.CustomEvent;
import javafx.event.EventHandler;

public abstract class CustomEventHandler implements EventHandler<CustomEvent> {

    public abstract void onEvent(String param);

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }

}
