package dawson.songtracker.front.controllers.detail;

import dawson.songtracker.front.utils.Popup;

public abstract class DetailPopupController<T> extends Popup {

    protected T entity;
    protected T oldEntity;

    public abstract void show(T entity);

}
