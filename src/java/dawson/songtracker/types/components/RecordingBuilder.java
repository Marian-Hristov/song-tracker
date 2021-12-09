package dawson.songtracker.types.components;

import dawson.songtracker.types.Builder;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordingBuilder extends Builder<Recording> {
    private String name;
    private double duration;
    public RecordingBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RecordingBuilder setDuration(double duration) {
        this.duration = duration;
        return this;
    }

    public Recording build() {
        return new Recording(-1, name, new Timestamp(0), duration, false, new HashMap<>(), new HashMap<>());
    }
}