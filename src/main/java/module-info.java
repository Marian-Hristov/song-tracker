module com.example.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.songtracker to javafx.fxml;
    exports com.example.songtracker;
}