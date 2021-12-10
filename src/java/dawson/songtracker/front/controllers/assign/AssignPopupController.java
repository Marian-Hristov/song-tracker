package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.front.utils.Loader;
import dawson.songtracker.front.utils.Popup;

public abstract class AssignPopupController extends Popup {

    public void onAdd() {
        System.out.println("onAdd hasn't been implemented yet.");
    }

    public AssignPopupController() {
        Loader.LoadAndSet(this);
    }
}
