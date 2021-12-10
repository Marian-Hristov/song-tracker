package dawson.songtracker.front.controllers.searchPanel;

import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.back.types.distributions.Collection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class CollectionSearchController extends SimpleSearchController<Collection> {
    TableColumn<Collection, String> collectionName;
    TableColumn<Collection, Integer> collectionCompilationsNb;

    @Override
    void onEnter(String text) {
        fireEvent(new SearchEvent(text));
    }

    @Override
    void setCols() {
        this.createColumns();
        this.collectionName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        this.collectionCompilationsNb.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTracks()));
    }

    @Override
    public void filterAndDisplay(ArrayList<? extends Collection> list) {
        displayedData.setAll(list);
        tbData.setItems(displayedData);
    }

    @Override
    void createColumns() {
        collectionName = new TableColumn<>("Name");
        collectionCompilationsNb = new TableColumn<>("Number of compilations");
        tbData.getColumns().addAll(collectionName, collectionCompilationsNb);
    }
}
