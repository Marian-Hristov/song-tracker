package dawson.songtracker.types.components;

import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class Compilation extends SongComponent {
    private final ArrayList<Segment<Compilation>> sampledCompilations;
    private final ArrayList<Segment<Recording>> sampledRecordings;
    private final Map<CompilationRole, ArrayList<Contributor>> contributions;

    public Compilation(int id, String name, Timestamp creationTime, double duration, ArrayList<Segment<Compilation>> sampledCompilations, ArrayList<Segment<Recording>> sampledRecordings, Map<CompilationRole, ArrayList<Contributor>> contributions) {
        super(id, name, creationTime, duration);
        if (sampledCompilations == null) throw new NullPointerException("the sampledCompilations is null");
        if (sampledRecordings == null) throw new NullPointerException("the sampledRecordings is null");
        if (contributions == null) throw new NullPointerException("the contributions is null");
        this.sampledCompilations = sampledCompilations;
        this.sampledRecordings = sampledRecordings;
        this.contributions = contributions;
    }

    public void addContribution(CompilationRole compilationRole, Contributor contributor) {
        if (compilationRole == null) throw new NullPointerException("the compilationRole is null");
        if (contributor == null) throw new NullPointerException("the contributor is null");
        if (this.contributions.containsKey(compilationRole)) {
            this.contributions.get(compilationRole).add(contributor);
        } else {
            ArrayList<Contributor> contributors = new ArrayList<>();
            contributors.add(contributor);
            this.contributions.put(compilationRole, contributors);
        }
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

    public void removeContribution(CompilationRole compilationRole, Contributor contributor) {
        if (this.contributions.get(compilationRole) == null)
            throw new NoSuchElementException("this role hasn't been added to this compilation");
        if (!this.contributions.get(compilationRole).remove(contributor))
            throw new NoSuchElementException("this contributor cannot be removed from this role because they are not assigned this role");
    }

    public ArrayList<Segment<Recording>> getSampledRecordings() {
        return sampledRecordings;
    }

    public ArrayList<Segment<Compilation>> getSampledCompilations() {
        return sampledCompilations;
    }

    public void addSampleRecoding(Segment<Recording> recordingSegment) {
        this.sampledRecordings.add(recordingSegment);
    }

    public void addSampleCompilation(Segment<Compilation> sampledCompilations) {
        this.sampledCompilations.add(sampledCompilations);
    }

    public void removeSampleRecording(Segment<Recording> recordingSegment) {
        if (!this.sampledRecordings.remove(recordingSegment))
            throw new NoSuchElementException("this segment cannot be removed because it isn't in this compilation yet");
    }

    public void removeSampleCompilation(Segment<Compilation> compilationSegment) {
        if (!this.sampledCompilations.remove(compilationSegment))
            throw new NoSuchElementException("this segment cannot be removed because it isn't in this compilation yet");
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
}
