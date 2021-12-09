package messageLogger;

import dawson.songtracker.utils.Loader;
import javafx.scene.control.Label;

public class MessageLoggerController extends Label {

    public MessageLoggerController() {
        Loader.LoadAndSet(this);
    }

}
