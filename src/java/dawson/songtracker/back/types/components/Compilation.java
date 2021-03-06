package dawson.songtracker.back.types.components;

import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Compilation extends SongComponent {
    private ArrayList<Segment<Compilation>> sampledCompilations;
    private ArrayList<Segment<Recording>> sampledRecordings;

    public void setContributions(Map<CompilationRole, ArrayList<Contributor>> contributions) {
        this.contributions = contributions;
    }

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
    public HashMap<CompilationRole, ArrayList<Contributor>> getContributorsRoleMap() {
        return (HashMap<CompilationRole, ArrayList<Contributor>>) contributions;
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

    public void removeContribution(CompilationRole compilationRole, Contributor contributor) {
        if (compilationRole == null) throw new NullPointerException("the compilationRole is null");
        if (contributor == null) throw new NullPointerException("the contributor is null");
        if (this.contributions.get(compilationRole) == null)
            throw new NoSuchElementException("this role hasn't been added to this compilation");
        if (!this.contributions.get(compilationRole).remove(contributor))
            throw new NoSuchElementException("this contributor cannot be removed from this role because they are not assigned this role");
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
