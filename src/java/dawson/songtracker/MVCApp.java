package dawson.songtracker;

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
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("FXML/mainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        stage.setResizable(false);
        stage.setTitle("Test title.");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
