package dawson.songtracker.types.components;

import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class CompilationBuilder {
    private int id = -1;
    private String name;
    private Timestamp creationTime;
    private double duration;
    private boolean released;
    private ArrayList<Segment<Compilation>> sampledCompilations;
    private ArrayList<Segment<Recording>> sampledRecordings;
    private Map<CompilationRole, ArrayList<Contributor>> contributions;

    public CompilationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CompilationBuilder setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public CompilationBuilder setDuration(double duration) {
        this.duration = duration;
        return this;
    }

    public CompilationBuilder setReleased(boolean released) {
        this.released = released;
        return this;
    }

    public CompilationBuilder setSampledCompilations(ArrayList<Segment<Compilation>> sampledCompilations) {
        this.sampledCompilations = sampledCompilations;
        return this;
    }

    public CompilationBuilder setSampledRecordings(ArrayList<Segment<Recording>> sampledRecordings) {
        this.sampledRecordings = sampledRecordings;
        return this;
    }

    public CompilationBuilder setContributions(Map<CompilationRole, ArrayList<Contributor>> contributions) {
        this.contributions = contributions;
        return this;
    }

    public Compilation createCompilation() {
        return new Compilation(id, name, creationTime, duration, released, sampledCompilations, sampledRecordings, contributions);
    }
}