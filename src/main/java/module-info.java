module com.example.songtracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.songtracker to javafx.fxml;
    exports com.example.songtracker;
}