package dawson.songtracker.back.types.components;

import dawson.songtracker.back.types.roles.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void addContribution(Role role, Contributor contributor) {
        if (role == null) throw new NullPointerException("the compilationRole is null");
        if (contributor == null) throw new NullPointerException("the contributor is null");
        if(role instanceof ProductionRole){
            if (this.productionContributions.containsKey((ProductionRole) role)) {
                this.productionContributions.get((ProductionRole) role).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                this.productionContributions.put((ProductionRole) role, contributors);
            }
        } else if (role instanceof MusicianRole) {
            if (this.musicalContributions.containsKey((MusicianRole) role)) {
                this.musicalContributions.get((MusicianRole) role).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                this.musicalContributions.put((MusicianRole) role, contributors);
            }
        } else {
            throw new UnsupportedOperationException("Role not accepted");
        }
    }

    public void removeContribution(Role role, Contributor contributor) {
        if (role == null) throw new NullPointerException("the compilationRole is null");
        if (contributor == null) throw new NullPointerException("the contributor is null");
        if(role instanceof ProductionRole){
            if (this.productionContributions.containsKey((ProductionRole) role)) {
                if (!this.productionContributions.get((ProductionRole) role).remove(contributor)) {
                    throw new NoSuchElementException("this contributor cannot be removed from this role because they are not assigned this role");
                }
            } else {
                throw new NoSuchElementException("this role hasn't been added to this compilation");
            }
        } else if (role instanceof MusicianRole) {
            if (this.musicalContributions.containsKey((MusicianRole) role)) {
                if (!this.musicalContributions.get((MusicianRole) role).remove(contributor)) {
                    throw new NoSuchElementException("this contributor cannot be removed from this role because they are not assigned this role");
                }
            } else {
                throw new NoSuchElementException("this role hasn't been added to this compilation");
            }
        } else {
            throw new UnsupportedOperationException("Role not accepted");
        }
    }

    public void setContributions(Map<Role, ArrayList<Contributor>> map) {
        musicalContributions.clear();
        productionContributions.clear();

        map.forEach(((role, contributors) -> {
            if (role instanceof MusicianRole) musicalContributions.put((MusicianRole) role, contributors);
            else if (role instanceof ProductionRole) productionContributions.put((ProductionRole) role, contributors);
        })
        );
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
    public HashMap<Role, ArrayList<Contributor>> getContributorsRoleMap() {
        HashMap<Role, ArrayList<Contributor>> map = new HashMap<>();

        map.putAll(this.musicalContributions);
        map.putAll(this.productionContributions);

        return map;
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
