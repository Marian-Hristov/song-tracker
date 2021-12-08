package dawson.songtracker.controllers.add;

import dawson.songtracker.utils.ICrud;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

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
