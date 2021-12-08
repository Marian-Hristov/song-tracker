package dawson.songtracker.utils;

import dawson.songtracker.customNodes.ComboBoxItemWrap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxCheckBuilder<T> {
    public static <T> ComboBox<ComboBoxItemWrap<T>> ComboBox(List<T> checked, List<T> unchecked) {
        ComboBox<ComboBoxItemWrap<T>> cb = new ComboBox<>();
        ObservableList<ComboBoxItemWrap<T>> options = FXCollections.observableArrayList();

        checked.forEach(obj -> options.add(new ComboBoxItemWrap<>(obj, true)));
        unchecked.forEach(obj -> options.add(new ComboBoxItemWrap<>(obj)));

        cb.setCellFactory( c -> {
            ListCell<ComboBoxItemWrap<T>> cell = new ListCell<>() {
                @Override
                protected void updateItem(ComboBoxItemWrap<T> item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        final CheckBox cb = new CheckBox(item.toString());
                        cb.selectedProperty().bind(item.checkProperty());
                        setGraphic(cb);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                cell.getItem().checkProperty().set(!cell.getItem().checkProperty().get());
                int totalItems = (int) cb.getItems().filtered(f-> f!=null).filtered(f-> f.getCheck()).stream().count();
                cb.setPromptText(totalItems + " items");
            });

            return cell;
        });

        cb.setItems(options);

        return cb;
    }

    public static <T> ComboBox<T> ComboBox(List<T> options) {
        ComboBox<T> comboBox = new ComboBox<>();
        ObservableList<T> observableList = FXCollections.observableList(options);
        comboBox.setItems(observableList);
        return comboBox;
    }
}
