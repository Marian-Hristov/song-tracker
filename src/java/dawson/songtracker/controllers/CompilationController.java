package dawson.songtracker.controllers;


import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.PopupOwner;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class CompilationController extends Pane implements PopupOwner {
    @FXML
    protected SearchPanelController panel;

    @FXML
    protected RecordingPopupController details;

    public CompilationController() {
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        this.panel.setLabel("Compilation");
        this.panel.addEventHandler(SearchEvent.SEARCH_EVENT, this::onSearch);
    }

    private void onSearch(SearchEvent event) {
        // Download stuff
        System.out.println("Searched for in compilation " + event.message);
    }

    public void onPopupClicked() {
        this.details.show();
    }
}
