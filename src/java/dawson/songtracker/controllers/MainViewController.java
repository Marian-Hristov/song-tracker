package dawson.songtracker.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainViewController {
    @FXML private ChoiceBox choiceBox;
    @FXML private TableView table;

    public static class MyDataType {
        private final SimpleStringProperty time;
        private final SimpleStringProperty name;

        private MyDataType(String name, String time) {
            this.time = new SimpleStringProperty(time);
            this.name = new SimpleStringProperty(name);
        }

        public String getName() {
            return name.get();
        }

        public String getTime() {
            return time.get();
        }

    }


    @FXML private void handleChoice(Event event) {
        System.out.println("Selected: " + choiceBox.getValue());
        event.consume();
    }

    private void initializeTable() {
        ObservableList<MyDataType> data = FXCollections.observableArrayList(
                new MyDataType("Stairway to Heaven", "15:00"),
                new MyDataType("Suck me will ya", "4:20")
        );

        TableColumn<MyDataType, String> firstNameCol = new TableColumn("Name");
        firstNameCol.setMinWidth(table.getPrefWidth() / 2 - 2);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MyDataType, String> durationCol = new TableColumn("Duration");
        durationCol.setMinWidth(table.getPrefWidth() / 2 - 1);
        durationCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        table.getColumns().addAll(firstNameCol, durationCol);

        table.setItems(data);

    }

    public void initialize() {
//        this.initializeTable();
    }

}