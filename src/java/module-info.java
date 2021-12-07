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
    opens dawson.songtracker.DBObjects.objectLoaders.dowloader to javafx.fxml;

    exports dawson.songtracker;
    exports dawson.songtracker.types.roles;
    exports dawson.songtracker.types.distributions;
    exports dawson.songtracker.types.components;
    exports dawson.songtracker.utils;
    exports dawson.songtracker.DBObjects;
    exports dawson.songtracker.DBObjects.objectLoaders.dowloader;
    exports dawson.songtracker.DBObjects.objectLoaders.uploader;
    opens dawson.songtracker.DBObjects.objectLoaders.uploader to javafx.fxml;
    opens dawson.songtracker.controllers.searchPanel to javafx.fxml;
    opens dawson.songtracker.controllers.add to javafx.fxml;
    opens dawson.songtracker.controllers.paneControllers to javafx.fxml;
    opens dawson.songtracker.controllers.assign to javafx.fxml;
}
