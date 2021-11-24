module dawson.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens dawson.songtracker to javafx.fxml;
    exports dawson.songtracker;
}