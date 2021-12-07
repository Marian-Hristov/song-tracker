package dawson.songtracker.controllers.assign;

import dawson.songtracker.types.components.Segment;
import dawson.songtracker.types.roles.Role;
import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Map;

public class CompilationDetailController extends AssignPopupController {
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

    public void populateRolesTable(Map<Role, String> roleContributor) {
       roleContributor.forEach((role, contributor) -> {

       });

    }

    private void populateSamples(ArrayList<Segment> segments) {
        segments.forEach(segment -> {

        });
    }

    @Override
    public void onAdd() {

    }
}
