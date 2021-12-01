module dawson.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens dawson.songtracker to javafx.fxml;
    exports dawson.songtracker;
    exports dawson.songtracker.types.Roles;
    exports dawson.songtracker.types.Distributions;
    exports dawson.songtracker.types.Components;
}