package dawson.songtracker.controllers;

import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RecordingPopupController extends Popup {
    @FXML Button closeButton;
    @FXML
    Popup assignContributor;

    @FXML
    private void handleAdd() {
        assignContributor.show();
    }

    @FXML
    private void hide() {
        this.setVisible(false);
    }
}
