package dawson.songtracker.front.controllers.detail;

import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.ProductionRole;
import dawson.songtracker.front.controllers.paneControllers.RecordingController;
import dawson.songtracker.front.utils.IDetailedInfo;
import dawson.songtracker.front.utils.Loader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecordingDetailController extends DetailPopupController<Recording>{
    @FXML
    Label name;

    @FXML
    Label duration;

    @FXML
    Label creation;

    @FXML CheckBox musicianCheck;
    @FXML CheckBox producerCheck;

    @FXML TableView<ContributorRole> table;
    @FXML TableColumn<ContributorRole, String> roleName;
    @FXML TableColumn<ContributorRole, String> contributorName;

    ObservableList<ContributorRole> observableList = FXCollections.observableArrayList();
    ContributorRole selectedRow;

    public void initialize() {
        this.setCols();
        musicianCheck.setSelected(true);
        producerCheck.setSelected(true);

        musicianCheck.setOnMouseClicked(ignore -> this.set());
        producerCheck.setOnMouseClicked(ignore -> this.set());
    }

    private void setCols() {
        roleName.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().role().getName()));
        contributorName.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().contributor().getName()));
        table.setRowFactory(tv -> {
            TableRow<ContributorRole> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                ContributorRole clickedRow = row.getItem();
                selectedRow = clickedRow;
            });

            return row;
        });
    }


    private void populateProductionTable() {
        if (!producerCheck.isSelected()) return;
        var contributions = this.entity.getProductionContributions();

        contributions.forEach((role, contributors) -> {
            contributors.forEach(contributor -> {

                observableList.add(new ContributorRole(contributor, role));
            });
        });

    }
    private void populateMusicianTable() {
        if (!musicianCheck.isSelected()) return;
        var contributions = this.entity.getMusicalContributions();

        contributions.forEach((role, contributors) -> {
            contributors.forEach(contributor -> {

                observableList.add(new ContributorRole(contributor, role));
            });
        });

    }

    @Override
    public void show(Recording entity) {
        this.entity = entity;
        this.set();
        this.setVisible(true);
        this.name.setText(this.entity.getName());
        this.duration.setText(entity.getDurationString());
        this.creation.setText(entity.getCreationTime().toString());
    }

    private void set() {
        this.observableList.clear();
        this.populateProductionTable();
        this.populateMusicianTable();
        table.setItems(observableList);

    }

    public RecordingDetailController() {
        Loader.LoadAndSet(this);
    }

    public void onAdd() {
        var parent = (RecordingController) this.getParent();
        parent.onAddContributor();
        this.hide();
    }

    public void onRemove() {
        var parent = ((RecordingController) this.getParent());
        parent.onRemoveContributor(selectedRow);

    }
}
