package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.front.controllers.detail.DetailPopupController;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.front.controllers.edit.DefaultDetailEditController;
import dawson.songtracker.front.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContributorPopupController extends DefaultDetailEditController<Recording> {
    @FXML
    Button closeButton;
    @FXML
    Popup assignContributor;
    @FXML
    private Label title;

    @FXML
    private TableView<Contributor> table;

    @FXML
    private TableColumn<Contributor, String> nameCol;

    @FXML
    private TableColumn<Contributor, String> roleCol;

    public void initialize() {
//        nameCol.setCellValueFactory(new PropertyValueFactory<>(""));
    }

//    pub

    @FXML
    private void handleAdd() {
        assignContributor.show();
        title.setText("Recording");
    }
}
