package messageLogger;

import dawson.songtracker.utils.Loader;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class MessageLoggerController extends Label {

    private ArrayList<Message> messages = new ArrayList<>();

    static MessageLoggerController instance;
    public static MessageLoggerController getInstance() {
            return instance;
    }

    public MessageLoggerController() {
        MessageLoggerController.instance = this;
        Loader.LoadAndSet(this);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

}
