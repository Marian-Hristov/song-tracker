package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.front.controllers.detail.DetailPopupController;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.front.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContributorPopupController extends DetailPopupController<Recording> {
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>(""));
    }


    @FXML
    private void handleAdd() {
        assignContributor.show();
        title.setText("Recording");
    }

    @Override
    public void show(Recording entity) {
        System.out.println(entity);
    }
}
