package dawson.songtracker.controllers.detail;

import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;

public abstract class DetailPopupController<T> extends Popup {

    protected T entity;
    protected T oldEntity;

    public abstract void show(T entity);

}
