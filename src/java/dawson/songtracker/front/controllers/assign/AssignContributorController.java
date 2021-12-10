package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.event.ContributorAssignedEvent;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.utils.Loader;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AssignContributorController extends AssignPopupController {
    @FXML
    ChoiceBox<Contributor> contributorBox;

    @FXML
    ChoiceBox<Role> roleBox;

    private Recording recording;
    private List<Class<? extends Role>> acceptedClasses;

    public AssignContributorController(List<Class<? extends Role>> acceptedClasses) {
        super();
        this.acceptedClasses = acceptedClasses;
        try {
            var loader = Loader.Load("assignContributorController");
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//       initializeContributorBox();
//       initializeRoleBox();
    }

    private void initializeContributorBox() {
        List<Contributor> contributors = CacheManager.getContributors().getCachedItems();
        this.contributorBox.getItems().addAll(contributors);

        this.contributorBox.setValue(contributors.get(1));
    }

    @Override
    public void show() {
        super.show();
        initializeContributorBox();
        initializeRoleBox();
    }

    private void initializeRoleBox() {
        List<Role> roles = CacheManager.getRoles().getCachedItems();

        roles = roles.stream()
                .filter(role -> acceptedClasses.contains(role.getClass()))
                .collect(Collectors.toList());

        roles.forEach(role-> this.roleBox.getItems().add(role));

        this.roleBox.setValue(roles.get(1));
    }
}
