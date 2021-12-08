package dawson.songtracker.controllers.assign;

import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.Popup;

public abstract class AssignPopupController extends Popup {

    public void onAdd() {
        System.out.println("onAdd hasn't been implemented yet.");
    }

    public AssignPopupController() {
        Loader.LoadAndSet(this);
    }
}
