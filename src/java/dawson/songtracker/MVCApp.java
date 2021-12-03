package dawson.songtracker;


import dawson.songtracker.utils.Loader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class MVCApp extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/resources/dawson/songtracker/FXML/mainView.fxml").toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("test.");


        stage.setTitle("Test title.");
        stage.setScene(new Scene(root));
        stage.show();

    }

    private void createDBConnection() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
