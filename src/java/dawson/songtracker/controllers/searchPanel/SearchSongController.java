package dawson.songtracker.controllers.searchPanel;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.components.SongComponent;
import dawson.songtracker.utils.ICrud;
import dawson.songtracker.utils.IDetailedInfo;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.PopupOwner;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SearchSongController extends SearchPanelController<SongComponent> {
    @FXML
    protected CheckBox released;

    @FXML
    protected CheckBox unreleased;

    @FXML
    protected TableColumn<? extends SongComponent, String> nameCol;

    @FXML
    protected TableColumn<? extends SongComponent, Timestamp> creationTimeCol;

    @FXML
    protected TableColumn<? extends SongComponent, Integer> durationCol;

    public SearchSongController() {
        Loader.LoadAndSet(this);
    }

    @Override
    void setCols() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        creationTimeCol.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    @Override
    void onEnter(String text) {
        this.fireEvent(
                new SearchEvent(
                        text
                )
        );
    }

    public void filterAndDisplay(ArrayList<? extends SongComponent> list) {
        if (released.isSelected() || unreleased.isSelected()) {
            List<SongComponent> filtered = new ArrayList<>();
            if (released.isSelected()) {
                list.stream()
                        .filter(c -> c.isReleased())
                        .forEach(filtered::add);
            }

            if (unreleased.isSelected()) {
                list.stream().
                        filter(c -> c.isUnreleased())
                        .forEach(filtered::add);
            }

            filtered = filtered.stream().distinct().toList();
            displayedData.setAll(filtered);
        } else {
            displayedData.setAll(list);
        }
        tbData.setItems(displayedData);
    }

}