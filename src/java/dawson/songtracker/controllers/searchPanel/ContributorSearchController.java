package dawson.songtracker.controllers.searchPanel;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.roles.Contributor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class ContributorSearchController extends SimpleSearchController<Contributor>{
    TableColumn<Contributor, String> contributorName;

    @Override
    void onEnter(String text) {
        fireEvent(new SearchEvent(text));
    }

    @Override
    public void initialize() {
        super.initialize();
        this.label.setText("Contributor");
    }

    @Override
    void setCols() {
        this.createColumns();
        this.contributorName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
    }

    @Override
    public void filterAndDisplay(ArrayList<? extends Contributor> list) {
        displayedData.setAll(list);
        tbData.setItems(displayedData);
    }

    @Override
    void createColumns() {
        contributorName = new TableColumn<>("Name");
        tbData.getColumns().add(contributorName);
    }
}
