package dawson.songtracker.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.MalformedURLException;

public abstract class Popup extends Pane {
    @FXML
    void hide() {
        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }

    public Popup() {
        try {
            FXMLLoader fxmlLoader = Loader.Load(this.getClass().getSimpleName());
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.hide();
    }

}
