package dawson.songtracker.front.controllers.detail;

import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.ProductionRole;
import dawson.songtracker.front.controllers.paneControllers.RecordingController;
import dawson.songtracker.front.utils.Loader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RecordingDetailController extends DetailPopupController<Recording>{

    @FXML CheckBox musicianCheck;
    @FXML CheckBox producerCheck;

    @FXML TableView<ContributorRole> table;
    @FXML TableColumn<ContributorRole, String> roleName;
    @FXML TableColumn<ContributorRole, String> contributorName;

    ObservableList<ContributorRole> observableList = FXCollections.observableArrayList();

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
}
