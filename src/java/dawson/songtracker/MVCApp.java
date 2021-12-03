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
import java.util.Objects;

public class MVCApp extends Application {
    @Override
    public void start(Stage stage){
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("FXML/mainView.fxml"));
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
