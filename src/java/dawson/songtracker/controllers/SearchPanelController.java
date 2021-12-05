package dawson.songtracker.controllers;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.PopupOwner;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class SearchPanelController extends Pane {
    @FXML
    protected Label label;

    @FXML
    protected TextField textField;

    @FXML
    protected CheckBox released;

    @FXML
    protected CheckBox unreleased;

    public SearchPanelController() {
        Loader.LoadAndSet(this);
    }

    public void setLabel(String text) {
        this.label.setText(text);
    }

    public void initialize() {
        this.textField.setOnKeyPressed(this::onEnter);
    }

    public void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.textField.setText(this.textField.getText().trim());
            this.fireEvent(
                    new SearchEvent(
                            this.textField.getText(),
                            this.released.isSelected(),
                            this.unreleased.isSelected()
                    )
            );
        }
    }

    public void onAdd() {
        Parent parent = this.getParent();
        if (parent instanceof PopupOwner) {
            ((PopupOwner) parent).onPopupClicked();
        } else {
            System.out.println("The parent of this node doesn't know how to deal with the add button.");
        }
    }

}
