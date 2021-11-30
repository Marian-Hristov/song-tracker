package dawson.songtracker.types.compilations_samples;

import dawson.songtracker.types.recordings_contributions.Recording;

import java.sql.Timestamp;

public class Compilation {
    private final int id;
    private String name;
    private Timestamp creationTime;
    private int duration;

    private Segment<Compilation>[] sampledCompilations;
    private Segment<Recording>[] sampledRecordings;

    public Compilation(int id, Segment<Compilation>[] sampledCompilations, Segment<Recording>[] sampledRecordings) {
        this.sampledCompilations = sampledCompilations;
        this.sampledRecordings = sampledRecordings;
        this.id = id;
    }

    public Segment<Recording>[] getSampledRecordings() {
        return sampledRecordings;
    }

    public void setSampledRecordings(Segment<Recording>[] sampledRecordings) {
        this.sampledRecordings = sampledRecordings;
    }

    public Segment<Compilation>[] getSampledCompilations() {
        return sampledCompilations;
    }

    public void setSampledCompilations(Segment<Compilation>[] sampledCompilations) {
        this.sampledCompilations = sampledCompilations;
    }

    public int getId() {
        return id;
    }
}
