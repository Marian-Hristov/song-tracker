package dawson.songtracker.front.controllers.searchPanel;

import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.back.types.roles.ERoleCategory;
import dawson.songtracker.back.types.roles.Role;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class RoleSearchController extends SimpleSearchController<Role> {
    TableColumn<? extends Role, String> roleName;

    TableColumn<? extends Role, ERoleCategory> roleCategory;

    public RoleSearchController() {
        super();
    }

    @Override
    void onEnter(String text) {
        fireEvent(new SearchEvent( textField.getText() ));
    }

    @Override
    void setCols() {
        createColumns();
        roleName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        roleCategory.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoleCategory()));
    }

    @Override
    public void filterAndDisplay(ArrayList<? extends Role> list) {
        displayedData.setAll(list);
        tbData.setItems(displayedData);
    }

    @Override
    void createColumns() {
        roleName = new TableColumn<>("Name");
        roleCategory = new TableColumn<>("Category");

        tbData.getColumns().add((TableColumn<Role, ?>) roleName);
        tbData.getColumns().add((TableColumn<Role, ?>) roleCategory);
    }
}
