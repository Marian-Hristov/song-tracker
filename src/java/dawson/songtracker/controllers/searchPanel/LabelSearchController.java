package dawson.songtracker.controllers.searchPanel;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.types.roles.Contributor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class LabelSearchController extends SimpleSearchController<RecordLabel>{
    TableColumn<RecordLabel, String> contributorName;

    @Override
    void onEnter(String text) {
        System.out.println("enter.");
        fireEvent(new SearchEvent(text));
    }

    @Override
    void setCols() {
        this.createColumns();
        this.contributorName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
    }

    @Override
    public void filterAndDisplay(ArrayList<? extends RecordLabel> list) {
        displayedData.setAll(list);
        tbData.setItems(displayedData);
    }

    @Override
    void createColumns() {
        contributorName = new TableColumn<>("Name");
        tbData.getColumns().add(contributorName);
    }
}
