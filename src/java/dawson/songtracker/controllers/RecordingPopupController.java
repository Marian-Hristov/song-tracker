package dawson.songtracker.controllers;

import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RecordingPopupController extends Popup {
    @FXML
    Button closeButton;
    @FXML
    Popup assignContributor;

    @FXML
    Label title;

    @FXML
    private void handleAdd() {
        assignContributor.show();
        title.setText("Recording");
    }

    @FXML
    private void hide() {
        this.setVisible(false);
    }
}
