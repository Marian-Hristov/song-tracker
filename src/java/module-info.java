module dawson.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens dawson.songtracker to javafx.fxml;
    opens dawson.songtracker.controllers to javafx.fxml;
    opens dawson.songtracker.utils to javafx.fxml;
    opens dawson.songtracker.event to javafx.fxml;
    opens dawson.songtracker.DBObjects to javafx.fxml;
    opens dawson.songtracker.DBObjects.objectLoaders to javafx.fxml;
    opens dawson.songtracker.DBObjects.objectLoaders.dowloader to javafx.fxml;

    exports dawson.songtracker;
    exports dawson.songtracker.types.Roles;
    exports dawson.songtracker.types.Distributions;
    exports dawson.songtracker.types.Components;
    exports dawson.songtracker.utils;
    exports dawson.songtracker.DBObjects;
    exports dawson.songtracker.DBObjects.objectLoaders;
    exports dawson.songtracker.DBObjects.objectLoaders.dowloader;
    exports dawson.songtracker.DBObjects.objectLoaders.uploader;
    opens dawson.songtracker.DBObjects.objectLoaders.uploader to javafx.fxml;
}
