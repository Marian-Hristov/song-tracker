package dawson.songtracker.front.controllers.searchPanel;

import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.front.utils.Loader;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.MalformedURLException;

public abstract class SimpleSearchController<T extends DatabaseObject> extends SearchPanelController<T>{
    public SimpleSearchController() {
        try {
            FXMLLoader fxmlLoader = Loader.Load("SimpleSearchController");
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void createColumns();
}
