package dawson.songtracker.front.controllers.detail;

import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.controllers.paneControllers.CompilationController;
import dawson.songtracker.front.controllers.paneControllers.RecordingController;
import dawson.songtracker.front.utils.Loader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Map;

public class CompilationDetailController extends DetailPopupController<Compilation> {
    @FXML
    Label name;

    @FXML
    Label duration;

    @FXML
    Label creation;

    @FXML
    TableView rolesTable;

    @FXML
    TableView samplesTable;

    @FXML
    CheckBox recCheck;

    @FXML
    CheckBox compCheck;

    ContributorRole selectedRow;
    Segment selectedSegment;


    public CompilationDetailController() {
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        this.samplesTable.getColumns().get(0);
        recCheck.setOnMouseClicked(event -> populateSegmentsTable());
        compCheck.setOnMouseClicked(event -> populateSegmentsTable());
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setDuration(String duration) {
        this.duration.setText(duration);
    }

    public void populateRolesTable() {
        var contributions = this.entity.getContributions();
        TableColumn<ContributorRole, String> namesCol = (TableColumn) rolesTable.getColumns().get(0);
        namesCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().contributor().getName()));

        rolesTable.setRowFactory(tv -> {
            TableRow<ContributorRole> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                ContributorRole clickedRow = row.getItem();
                selectedRow = clickedRow;
            });

            return row;
        });

        TableColumn<ContributorRole, String> roleCol = (TableColumn)  rolesTable.getColumns().get(1);
        roleCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().role().getName()));

        ObservableList<ContributorRole> cr = FXCollections.observableArrayList();

        contributions.forEach((role, contributors) -> {
            contributors.forEach(contributor -> {
                cr.add(new ContributorRole(contributor, role));
            });
        });

        rolesTable.setItems(cr);
    }

    public void populateSegmentsTable() {
        TableColumn<Segment, String> nameCol = (TableColumn) samplesTable.getColumns().get(0);
        nameCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().toString()));

        TableColumn<Segment, Double> durationCol = (TableColumn) samplesTable.getColumns().get(1);
        durationCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getDurationInMainTrack()));

        TableColumn<Segment, Double> startCol = (TableColumn) samplesTable.getColumns().get(2);
        startCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getComponentTrackOffset()));

        ObservableList<Segment> segmentObservableList = FXCollections.observableArrayList();

        if (recCheck.isSelected()) {
            var sampledRecordings = this.entity.getSampledRecordings();
            segmentObservableList.addAll(sampledRecordings);
        }

        if (compCheck.isSelected()) {
            var sampledCompilations = this.entity.getSampledCompilations();
            segmentObservableList.addAll(sampledCompilations);
        }


        samplesTable.setRowFactory(tv -> {
            TableRow<Segment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Segment segment = row.getItem();
                selectedSegment = segment;
            });

            return row;
        });

        samplesTable.setItems(segmentObservableList);

    }

    @FXML
    public void onAddContributor() {
        CompilationController cc = (CompilationController) this.getParent();
        cc.onAddContributor();
    }

    public void onAddSegment() {
        CompilationController cc = (CompilationController) this.getParent();
        cc.onAddSegment();
    }

    public void onRemove() {
        var parent = ((CompilationController) this.getParent());
        parent.onRemoveContributor(selectedRow);
    }

    public void onRemoveSegment() {
        var parent = ((CompilationController) this.getParent());
        parent.onRemoveSegment(selectedSegment);
    }

    @Override
    public void show(Compilation entity) {
        this.oldEntity = entity;
        this.entity = entity;
        this.populateRolesTable();
        this.populateSegmentsTable();
        this.setVisible(true);
        this.setName(entity.getName());
        this.duration.setText(entity.getDurationString());
        this.creation.setText(entity.getCreationTime().toString());
    }


}
