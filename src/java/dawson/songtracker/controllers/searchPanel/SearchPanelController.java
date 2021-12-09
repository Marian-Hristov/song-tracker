package dawson.songtracker.controllers.searchPanel;

import dawson.songtracker.event.UpdateTableEvent;
import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.utils.ICrud;
import dawson.songtracker.utils.IDetailedInfo;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.PopupOwner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class SearchPanelController<T extends DatabaseObject> extends Pane {
    @FXML
    protected TextField textField;

    @FXML
    protected Label label;

    @FXML
    protected TableView<T> tbData;

    private ArrayList<? extends T> defaultData = new ArrayList<>();
    private ArrayList<? extends T> searchData = new ArrayList<>();
    protected ObservableList<T> displayedData = FXCollections.observableArrayList();

    protected T selectedRow;

    public SearchPanelController() {
//        Loader.LoadAndSet(this);
    }

    public void setLabel(String string) {
        this.label.setText(string);
    }

    public void initialize() {
        textField.setOnKeyPressed(this::formatOnEnter);
        this.setCols();
        this.setRowFactory();
    }

    public void formatOnEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.textField.setText(this.textField.getText().trim());
            this.onEnter(this.textField.getText());
        }
    }

    abstract void onEnter(String text);
    abstract void setCols();

    void setRowFactory() {
        tbData.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    T clickedRow = row.getItem();
                    var parent = this.getParent();
                    if (parent instanceof IDetailedInfo) {
                        selectedRow = clickedRow;
                        ((IDetailedInfo) parent).showDetailedInfo(clickedRow);
                    }

                } else if (event.getClickCount() == 1) {
                    T clickedRow = row.getItem();
                    selectedRow = clickedRow;
                }
            });

            return row;
        });
    }

    public void populateTable(ArrayList<? extends T> list) {
        defaultData = list;
    }

    public void displayDefault() {
        filterAndDisplay(defaultData);
    }

    public void displaySearchResult(ArrayList<? extends T> list) {
        searchData = list;
        filterAndDisplay(searchData);
    }

    abstract public void filterAndDisplay(ArrayList<? extends T> list);

    @FXML
    public void onAdd() {
        Parent parent = this.getParent();
        if (parent instanceof PopupOwner) {
            ((PopupOwner) parent).onPopupClicked();
        } else {
            System.out.println("The parent of this node doesn't know how to deal with the add button.");
        }
    }

    @FXML
    public void onDelete() {
        Parent parent = this.getParent();
        if (parent instanceof ICrud) {
            try {
                ((ICrud) parent).removeEntry(this.selectedRow);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onUpdate() {
        this.fireEvent(new UpdateTableEvent());
    }

    public T getSelectedRow() {
        return this.selectedRow;
    }
}
