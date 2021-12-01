module dawson.songtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens dawson.songtracker to javafx.fxml;
    exports dawson.songtracker;
    exports dawson.songtracker.types.recordings_contributions;
    exports dawson.songtracker.types.album_distributions;
    exports dawson.songtracker.types.compilations_samples;
}