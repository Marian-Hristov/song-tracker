package dawson.songtracker.types.Components;

import dawson.songtracker.types.Roles.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class Recording extends SongComponent {
    private final Map<MusicianRole, ArrayList<Contributor>> musicalContributions;
    private final Map<ProductionRole, ArrayList<Contributor>> productionContributions;

    public Recording(int id, String name, Timestamp creationTime, int duration, Map<MusicianRole, ArrayList<Contributor>> musicalContributions, Map<ProductionRole, ArrayList<Contributor>> productionContributions) {
        super(id, name, creationTime, duration);
        if(productionContributions == null) throw new NullPointerException("the productionContributions is null");
        if(musicalContributions == null) throw new NullPointerException("the musicalContributions is null");
        this.productionContributions = productionContributions;
        this.musicalContributions = musicalContributions;
    }

    public void addContribution(Role role, Contributor contributor){
        if(role == null){
            throw new NullPointerException("the role is null");
        }
        if(contributor == null){
            throw new NullPointerException("the contributor is null");
        }
        if(role instanceof MusicianRole musicianRole){
            if(this.musicalContributions.containsKey(musicianRole)){
                this.musicalContributions.get(musicianRole).add(contributor);
            } else {
                ArrayList<Contributor> roleContributions = new ArrayList<>();
                roleContributions.add(contributor);
                this.musicalContributions.put(musicianRole, roleContributions);
            }
        } else if (role instanceof ProductionRole productionRole){
            if(this.productionContributions.containsKey(productionRole)){
                this.productionContributions.get(productionRole).add(contributor);
            } else {
                ArrayList<Contributor> roleContributions = new ArrayList<>();
                roleContributions.add(contributor);
                this.productionContributions.put(productionRole, roleContributions);
            }
        } else {
            throw new UnsupportedOperationException("This type or role is not yet implemented");
        }
    }

    public void removeContribution(Role role, Contributor contributor){
        if(role == null){
            throw new NullPointerException("the role is null");
        }
        if(contributor == null){
            throw new NullPointerException("the contributor is null");
        }
        if(role instanceof ProductionRole){
            if(this.productionContributions.get(role) == null) throw new NoSuchElementException("this role is yet added to the recording");
            if(!this.productionContributions.get(role).remove(contributor)) throw new NoSuchElementException("this contributor cannot be removed from this role because they are not assigned this role");
        } else if (role instanceof MusicianRole){
            if(this.musicalContributions.get(role) == null) throw new NoSuchElementException("this role is yet added to the recording");
            if(!this.musicalContributions.get(role).remove(contributor)) throw new NoSuchElementException("this contributor cannot be removed from this role because they are not assigned this role");
        } else {
            throw new UnsupportedOperationException("This type or role is not yet implemented");
        }
    }

    @Override
    public ArrayList<Contributor> getContributorsInRole(Role role) {
        if(role == null){
            throw new NullPointerException("the role is null");
        }
        if(role instanceof ProductionRole productionRole){
            if(this.productionContributions.get(productionRole) == null) throw new NoSuchElementException("this production role is not yet used in this recording");
            return this.productionContributions.get(productionRole);
        } else if (role instanceof MusicianRole musicianRole){
            if(this.musicalContributions.get(musicianRole) == null) throw new NoSuchElementException("this musician role is not yet used in this recording ");
            return this.musicalContributions.get(musicianRole);
        } else {
            throw new UnsupportedOperationException("this type of role is not yet supported in recordings");
        }
    }

    public void setDuration(int duration) {
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

}
