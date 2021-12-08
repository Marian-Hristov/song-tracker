package dawson.songtracker.controllers.edit;

import dawson.songtracker.controllers.edit.DetailEditPopupController;
import dawson.songtracker.utils.Loader;

import java.io.IOException;

public abstract class DefaultDetailEditController<T> extends DetailEditPopupController<T> {
    @Override
    public void show(T entity) {
        this.entity = entity;
        this.createFields();
        this.show();
    }

    public DefaultDetailEditController() {
        try {
            var fxmlLoader = Loader.Load("detail/DefaultEditDetailController");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
