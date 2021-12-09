package dawson.songtracker.front.controllers.searchPanel;

import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;

import java.sql.Date;
import java.util.ArrayList;

public class DistributionSearchController extends SimpleSearchController<Distribution> {
    TableColumn<Distribution, Date> releaseDateCol;
    TableColumn<Distribution, RecordLabel> recordLabelCol;
    TableColumn<Distribution, Market> marketCol;
    TableColumn<Distribution, Collection> collectionCol;

    @Override
    void onEnter(String text) {
        fireEvent(new SearchEvent(text));
    }

    @Override
    public void initialize() {
        super.initialize();
        this.textField.setDisable(true);
    }

    @Override
    void setCols() {
        this.createColumns();
        releaseDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReleaseDate()));
        recordLabelCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLabel()));
        marketCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMarket()));
        collectionCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCollection()));
    }

    @Override
    public void filterAndDisplay(ArrayList<? extends Distribution> list) {
        displayedData.setAll(list);
        tbData.setItems(displayedData);
    }

    @Override
    void createColumns() {
        releaseDateCol = new TableColumn<>("Release Date");
        recordLabelCol = new TableColumn<>("Record Label");
        marketCol = new TableColumn<>("Market");
        collectionCol = new TableColumn<>("Collection");

        tbData.getColumns().addAll(releaseDateCol, recordLabelCol, marketCol, collectionCol);
    }
}
