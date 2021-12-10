package dawson.songtracker.front.controllers.add;

import dawson.songtracker.front.event.AddRoleEvent;
import dawson.songtracker.back.types.roles.ERoleCategory;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.back.types.roles.RoleBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

//public class AddRoleController extends Popup {
public class AddRoleController extends ProceduralAddPopupController<Role, RoleBuilder> {
    @FXML
    protected TextField textInput;

    @FXML
    protected ChoiceBox<ERoleCategory> choiceBox;

    public AddRoleController() {
        super(Role.class, new RoleBuilder());
    }

//    public void initialize() {
//        choiceBox.getItems().add(ERoleCategory.COMPILATION);
//        choiceBox.getItems().add(ERoleCategory.MUSICIAN);
//        choiceBox.getItems().add(ERoleCategory.PRODUCTION);
//    }
//
    @FXML
    public void onAdd() {
        this.fireEvent(
                new AddRoleEvent(
                        textInput.getText(),
                        choiceBox.getSelectionModel().getSelectedItem()
                )
        );
        System.out.println("fired event to add new role.");
    }
}
