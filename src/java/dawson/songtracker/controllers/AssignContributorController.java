package dawson.songtracker.controllers;

import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.Role;
import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;

public class AssignContributorController extends Popup {
    @FXML
    ChoiceBox contributorBox;

    @FXML
    ChoiceBox roleBox;

    public AssignContributorController() {
       super();
    }

    public void initialize() {
       initializeContributorBox();
       initializeRoleBox();
    }

    private void initializeContributorBox() {
        ArrayList<Contributor> contributors = new ArrayList<Contributor>(Arrays.asList(
                new Contributor(1, "Lucas"),
                new Contributor(2, "Marian"),
                new Contributor(3, "Jude")
            )
        ); //ObjectDownloader.getAllContributors();

        this.contributorBox.setValue(contributors.get(1).getName());
        contributors.forEach(contributor -> this.contributorBox.getItems().add(contributor.getName()));
    }

    private void initializeRoleBox() {
        ArrayList<Role> roles = new ArrayList<>(
                Arrays.asList(
                        new CompilationRole(1, "Compilation role example"),
                        new MusicianRole(1, "Musician role example")
                )

        );//ObjectDownloader.getAllContributors();
        roles.forEach(role-> this.roleBox.getItems().add(role.getName()));

        this.roleBox.setValue(roles.get(1).getName());
    }
}
