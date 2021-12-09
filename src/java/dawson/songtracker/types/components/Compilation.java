package dawson.songtracker.types.components;

import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class Compilation extends SongComponent {
    private ArrayList<Segment<Compilation>> sampledCompilations;
    private ArrayList<Segment<Recording>> sampledRecordings;
    private Map<CompilationRole, ArrayList<Contributor>> contributions;

    public Compilation(int id, String name, Timestamp creationTime, double duration, boolean released, ArrayList<Segment<Compilation>> sampledCompilations, ArrayList<Segment<Recording>> sampledRecordings, Map<CompilationRole, ArrayList<Contributor>> contributions) {
        super(id, name, creationTime, duration, released);
        if (sampledCompilations == null) throw new NullPointerException("the sampledCompilations is null");
        if (sampledRecordings == null) throw new NullPointerException("the sampledRecordings is null");
        if (contributions == null) throw new NullPointerException("the contributions is null");
        this.sampledCompilations = sampledCompilations;
        this.sampledRecordings = sampledRecordings;
        this.contributions = contributions;
    }

    @Override
    public ArrayList<Contributor> getContributorsInRole(Role role) {
        if (role == null) throw new NullPointerException("the role is null");
        if (!(role instanceof CompilationRole))
            throw new UnsupportedOperationException("this role type is not supported in a compilation");
        if (this.contributions.get(role) == null)
            throw new NoSuchElementException("this role hasn't been assigned in this contribution");
        return this.contributions.get(role);
    }

    public ArrayList<Segment<Recording>> getSampledRecordings() {
        return sampledRecordings;
    }

    public void setSampledRecordings(ArrayList<Segment<Recording>> sampledRecordings){this.sampledRecordings = sampledRecordings;}

    public ArrayList<Segment<Compilation>> getSampledCompilations() {
        return sampledCompilations;
    }

    public void setSampledCompilations(ArrayList<Segment<Compilation>> sampledCompilations) {this.sampledCompilations = sampledCompilations;}

    public Map<CompilationRole, ArrayList<Contributor>> getContributions() {
        return this.contributions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compilation compilation)) return false;

        return id == compilation.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
