package dawson.songtracker.controllers.searchPanel;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Market;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class MarketSearchController extends SimpleSearchController<Market> {
    TableColumn<Market, String> nameCol;

    @Override
    public void initialize() {
        super.initialize();
        this.label.setText("Market");
    }

    @Override
    void onEnter(String text) {
        fireEvent(new SearchEvent(this.textField.getText()));
    }

    @Override
    void setCols() {
        this.createColumns();
        nameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
    }

    @Override
    public void filterAndDisplay(ArrayList<? extends Market> list) {
        // No filter needed
        displayedData.addAll(list);
        tbData.setItems(displayedData);
    }

    @Override
    void createColumns() {
        nameCol = new TableColumn<>("Name");
        tbData.getColumns().add(nameCol);
    }
}
