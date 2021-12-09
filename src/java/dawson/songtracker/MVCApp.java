package dawson.songtracker;

import dawson.songtracker.dbObjects.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MVCApp extends Application {
    @FXML
    Label messageLog;

    @Override
    public void start(Stage stage) {
        createDBConnection();
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
        DBConnection.setUsername("A2035536");
        DBConnection.setPassword("Dawson123");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
