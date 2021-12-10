package dawson.songtracker.front.controllers.detail;

import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.utils.Loader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    public CompilationDetailController() {
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        this.samplesTable.getColumns().get(0);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setDuration(String duration) {
        this.duration.setText(duration);
    }

    public void setCreation(String creation) {
        this.creation.setText(creation);
    }

    public void populateTable(Map<Role, String> roleContributor, ArrayList<Segment> segments) {
        //...
    }

    public void populateRolesTable() {
        var contributions = this.entity.getContributions();
        TableColumn<ContributorRole, String> namesCol = (TableColumn) rolesTable.getColumns().get(0);
        namesCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().contributor.getName()));

        TableColumn<ContributorRole, String> roleCol = (TableColumn)  rolesTable.getColumns().get(1);
        roleCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().role.getName()));

        ObservableList<ContributorRole> cr = FXCollections.observableArrayList();

        contributions.forEach((role, contributors) -> {
            contributors.forEach(contributor -> {
                cr.add(new ContributorRole(contributor, role));
            });
        });

        System.out.println(cr);
        rolesTable.setItems(cr);
    }

    public void populateSegmentsTable() {
        var sampledCompilations = this.entity.getSampledCompilations();
        var sampledRecordings = this.entity.getSampledRecordings();

        TableColumn<Segment, String> nameCol = (TableColumn) samplesTable.getColumns().get(0);
        nameCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().toString()));

        TableColumn<Segment, Double> durationCol = (TableColumn) samplesTable.getColumns().get(1);
        durationCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getDurationInMainTrack()));

        TableColumn<Segment, Double> startCol = (TableColumn) samplesTable.getColumns().get(2);
        startCol.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getComponentTrackOffset()));

        ObservableList<Segment> segmentObservableList = FXCollections.observableArrayList();

        segmentObservableList.addAll(sampledCompilations);
        segmentObservableList.addAll(sampledRecordings);

        samplesTable.setItems(segmentObservableList);

    }

    @Override
    public void show(Compilation entity) {
        this.oldEntity = entity;
        this.entity = entity;
        this.populateRolesTable();
        this.populateSegmentsTable();
        this.setVisible(true);
    }

    private record ContributorRole(Contributor contributor, Role role){};

}
