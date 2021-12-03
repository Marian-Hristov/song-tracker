module dawson.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens dawson.songtracker to javafx.fxml;
    opens dawson.songtracker.controllers to javafx.fxml;
    opens dawson.songtracker.utils to javafx.fxml;
    opens dawson.songtracker.event to javafx.fxml;

    exports dawson.songtracker;
    exports dawson.songtracker.types.Roles;
    exports dawson.songtracker.types.Distributions;
    exports dawson.songtracker.types.Components;
    exports dawson.songtracker.utils;
}