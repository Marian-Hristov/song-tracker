module dawson.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens dawson.songtracker to javafx.fxml;
    opens dawson.songtracker.front.controllers to javafx.fxml;
    opens dawson.songtracker.front.event to javafx.fxml;
    opens dawson.songtracker.back.dbObjects to javafx.fxml;
    opens dawson.songtracker.back.dbObjects.objectLoaders.dowloader to javafx.fxml;

    exports dawson.songtracker;
    exports dawson.songtracker.back.types;
    exports dawson.songtracker.back.types.roles;
    exports dawson.songtracker.back.types.distributions;
    exports dawson.songtracker.back.types.components;
    exports dawson.songtracker.back.dbObjects;
    exports dawson.songtracker.back.dbObjects.objectLoaders.dowloader;
    exports dawson.songtracker.back.dbObjects.objectLoaders.uploader;
    exports dawson.songtracker.back.types.logs;
    opens dawson.songtracker.back.dbObjects.objectLoaders.uploader to javafx.fxml;
    opens dawson.songtracker.front.controllers.searchPanel to javafx.fxml;
    opens dawson.songtracker.front.controllers.add to javafx.fxml;
    opens dawson.songtracker.front.controllers.paneControllers to javafx.fxml;
    opens dawson.songtracker.front.controllers.assign to javafx.fxml;
    opens dawson.songtracker.front.controllers.detail to javafx.fxml;
    opens dawson.songtracker.front.controllers.edit to javafx.fxml;
    exports dawson.songtracker.front.messageLogger;
    opens dawson.songtracker.front.messageLogger to javafx.fxml;
    exports dawson.songtracker.front.utils;
    opens dawson.songtracker.front.utils to javafx.fxml;
    exports dawson.songtracker.front;
    opens dawson.songtracker.front to javafx.fxml;
    exports dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;
    opens dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders to javafx.fxml;
}
