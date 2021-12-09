package dawson.songtracker.types.components;

import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class RecordingBuilder {
    private int id = -1;
    private String name;
    private Timestamp creationTime;
    private double duration;
    private boolean released;
    private Map<MusicianRole, ArrayList<Contributor>> musicalContributions;
    private Map<ProductionRole, ArrayList<Contributor>> productionContributions;

    public RecordingBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RecordingBuilder setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public RecordingBuilder setDuration(double duration) {
        this.duration = duration;
        return this;
    }

    public RecordingBuilder setReleased(boolean released) {
        this.released = released;
        return this;
    }

    public RecordingBuilder setMusicalContributions(Map<MusicianRole, ArrayList<Contributor>> musicalContributions) {
        this.musicalContributions = musicalContributions;
        return this;
    }

    public RecordingBuilder setProductionContributions(Map<ProductionRole, ArrayList<Contributor>> productionContributions) {
        this.productionContributions = productionContributions;
        return this;
    }

    public Recording createRecording() {
        return new Recording(id, name, creationTime, duration, released, musicalContributions, productionContributions);
    }
}