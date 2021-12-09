package dawson.songtracker.types.components;

import dawson.songtracker.types.roles.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class Recording extends SongComponent {
    private Map<MusicianRole, ArrayList<Contributor>> musicalContributions;
    private Map<ProductionRole, ArrayList<Contributor>> productionContributions;

    public Recording(int id, String name, Timestamp creationTime, double duration, boolean released, Map<MusicianRole, ArrayList<Contributor>> musicalContributions, Map<ProductionRole, ArrayList<Contributor>> productionContributions) {
        super(id, name, creationTime, duration, released);
        if (productionContributions == null) throw new NullPointerException("the productionContributions is null");
        if (musicalContributions == null) throw new NullPointerException("the musicalContributions is null");
        this.productionContributions = productionContributions;
        this.musicalContributions = musicalContributions;
    }

    public void setMusicalContributions(Map<MusicianRole, ArrayList<Contributor>> musicalContributions){
        this.musicalContributions = musicalContributions;
    }

    public void setProductionContributions(Map<ProductionRole, ArrayList<Contributor>> productionContributions) {
        this.productionContributions = productionContributions;
    }

    public Map<ProductionRole, ArrayList<Contributor>> getProductionContributions() {
        return productionContributions;
    }

    @Override
    public ArrayList<Contributor> getContributorsInRole(Role role) {
        if (role == null) {
            throw new NullPointerException("the role is null");
        }
        if (role instanceof ProductionRole productionRole) {
            if (this.productionContributions.get(productionRole) == null)
                throw new NoSuchElementException("this production role is not yet used in this recording");
            return this.productionContributions.get(productionRole);
        } else if (role instanceof MusicianRole musicianRole) {
            if (this.musicalContributions.get(musicianRole) == null)
                throw new NoSuchElementException("this musician role is not yet used in this recording ");
            return this.musicalContributions.get(musicianRole);
        } else {
            throw new UnsupportedOperationException("this type of role is not yet supported in recordings");
        }
    }

    public Map<MusicianRole, ArrayList<Contributor>> getMusicalContributions() { return musicalContributions; }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recording recording)) return false;

        return id == recording.id;
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
