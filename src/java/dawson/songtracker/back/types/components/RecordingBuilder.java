package dawson.songtracker.back.types.components;

import dawson.songtracker.back.types.Builder;

import java.sql.Timestamp;
import java.util.HashMap;

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