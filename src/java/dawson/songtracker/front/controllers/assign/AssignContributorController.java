package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.front.event.ContributorAssignedEvent;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;

public class AssignContributorController extends AssignPopupController {
    @FXML
    ChoiceBox<Contributor> contributorBox;

    @FXML
    ChoiceBox<Role> roleBox;

    private Recording recording;

    public AssignContributorController() {
        super();
    }

    @Override
    public void onAdd() {
        fireEvent(
                new ContributorAssignedEvent(roleBox.getSelectionModel().getSelectedItem(),
                        contributorBox.getSelectionModel().getSelectedItem())
        );
    }

    public void setRecording(Recording recording) {
       this.recording = recording;
    }

    public void initialize() {
       initializeContributorBox();
       initializeRoleBox();
    }

    private void initializeContributorBox() {
//        ObjectDownloader.getInstance().ge

//        this.contributorBox.setValue(contributors.get(1));
//        contributors.forEach(contributor -> this.contributorBox.getItems().add(contributor));
    }

    private void initializeRoleBox() {
        ArrayList<Role> roles = new ArrayList<>(
                Arrays.asList(
                        new CompilationRole(1, "Compilation role example"),
                        new MusicianRole(1, "Musician role example")
                )

        );//ObjectDownloader.getAllContributors();
        roles.forEach(role-> this.roleBox.getItems().add(role));

        this.roleBox.setValue(roles.get(1));
    }
}