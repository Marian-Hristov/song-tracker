package dawson.songtracker.utils;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public abstract class Popup extends Pane {
    @FXML
    void hide() {
        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }

    public Popup() {
        this.hide();
    }

    public abstract void onAdd();


}
