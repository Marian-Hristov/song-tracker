package dawson.songtracker.types.compilations_samples;

import dawson.songtracker.types.SongComponent;
import dawson.songtracker.types.recordings_contributions.Contributor;
import dawson.songtracker.types.recordings_contributions.Recording;
import dawson.songtracker.types.recordings_contributions.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class Compilation extends SongComponent {
    private ArrayList<Segment<Compilation>> sampledCompilations;
    private ArrayList<Segment<Recording>> sampledRecordings;

    public Compilation(int id, String name, Timestamp creationTime, int duration, ArrayList<Segment<Compilation>> sampledCompilations, ArrayList<Segment<Recording>> sampledRecordings, Map<Role, ArrayList<Contributor>> contributions) {
        super(id, name, creationTime, duration, contributions);
        this.sampledCompilations = sampledCompilations;
        this.sampledRecordings = sampledRecordings;
    }

    public ArrayList<Segment<Recording>> getSampledRecordings() {
        return sampledRecordings;
    }

    public void setSampledRecordings(ArrayList<Segment<Recording>> sampledRecordings) {
        this.sampledRecordings = sampledRecordings;
    }

    public ArrayList<Segment<Compilation>> getSampledCompilations() {
        return sampledCompilations;
    }

    public void setSampledCompilations(ArrayList<Segment<Compilation>> sampledCompilations) {
        this.sampledCompilations = sampledCompilations;
    }

    public void addSampleRecoding(Segment<Recording> recordingSegment){
        this.sampledRecordings.add(recordingSegment);
    }

    public void addSampleCompilation(Segment<Compilation> sampledCompilations){
        this.sampledCompilations.add(sampledCompilations);
    }
}
