package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.front.utils.Loader;
import dawson.songtracker.front.utils.Popup;

import java.io.IOException;
import java.net.MalformedURLException;

public abstract class AssignPopupController extends Popup {

    public void onAdd() {
        System.out.println("onAdd hasn't been implemented yet.");
    }

    public AssignPopupController() {
        try {
            var loader = Loader.Load("assignContributorController");
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
