package dawson.songtracker.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Loader {

    static final int CHARACTERS_TO_REMOVE = "Controller".length();
    private static final String PATH_TO_RESOURCES = "src/resources/dawson/songtracker/FXML/";

    public static FXMLLoader Load(String classname) throws MalformedURLException {
        String viewName = classname.substring(0, classname.length() - CHARACTERS_TO_REMOVE);
        viewName = PATH_TO_RESOURCES + viewName.substring(0, 1).toLowerCase() + viewName.substring(1) + "View.fxml";

        System.out.println(viewName);
        URL file = new File(viewName).toURI().toURL();
        System.out.println(file);

        FXMLLoader fmxLoader = new FXMLLoader(file);

        return fmxLoader;
    }

    public static <T extends Node> void LoadAndSet(T target) {
        try {
            FXMLLoader fxmlLoader = Load(target.getClass().getSimpleName());
            fxmlLoader.setRoot(target);
            fxmlLoader.setController(target);
            fxmlLoader.load();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
