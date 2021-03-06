package dawson.songtracker.front.controllers.add;

import dawson.songtracker.front.utils.Loader;
import dawson.songtracker.front.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.MalformedURLException;

public abstract class SimpleAddPopupController extends Popup {
    @FXML
    protected Label title;

    @FXML
    protected TextField textInput;

    public void initialize() {
        this.setName();
    }

    abstract public void setName();

    public SimpleAddPopupController() {
        try {
            var loader = Loader.Load("simpleAddPopupController");
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
