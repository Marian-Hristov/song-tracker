package dawson.songtracker.controllers;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.PopupOwner;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class RecordingController extends Pane implements PopupOwner {
    @FXML
    protected SearchPanelController panel;

    @FXML
    protected RecordingPopupController details;

    public RecordingController() {
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        this.panel.setLabel("Recording");
        this.panel.addEventHandler(SearchEvent.SEARCH_EVENT, event -> {
            System.out.println(event);
        });
    }

    public void onPopupClicked() {
        this.details.show();
    }
}
