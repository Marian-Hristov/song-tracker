package dawson.songtracker.types;

import dawson.songtracker.types.recordings_contributions.Contributor;
import dawson.songtracker.types.recordings_contributions.MusicianRole;
import dawson.songtracker.types.recordings_contributions.ProductionRole;
import dawson.songtracker.types.recordings_contributions.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class SongComponent {
    protected final int id;
    protected String name;
    protected final Timestamp creationTime;
    protected int duration;
    protected Map<MusicianRole, ArrayList<Contributor>> musicalContributions;
    protected Map<ProductionRole, ArrayList<Contributor>> productionContributions;

    public SongComponent(int id, String name, Timestamp creationTime, int duration, Map<MusicianRole, ArrayList<Contributor>> musicalContributions, Map<ProductionRole, ArrayList<Contributor>> productionContributions) {
        if (name == null) {
            throw new NullPointerException("the name is null");
        }
        if (creationTime == null) {
            throw new NullPointerException("the creationTime is null");
        }
        if (musicalContributions == null || productionContributions == null) {
            throw new NullPointerException("the contributions are null");
        }
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.duration = duration;
        this.productionContributions = productionContributions;
        this.musicalContributions = musicalContributions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationString() {
//        Duration of 1hour 2minutes 47seconds
//        duration = 3767
        int hours = (duration - (duration % 3600)) / 3600;
        int seconds = (duration - hours * 3600) % 60;
        int minutes = (duration - hours * 3600 - seconds) / 60;
        return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + "."
                + (seconds < 10 ? "0" + seconds : seconds);
    }

    public Map<MusicianRole, ArrayList<Contributor>> getMusicalContributions() {
        return musicalContributions;
    }

    public Map<ProductionRole, ArrayList<Contributor>> getProductionContributions() {
        return productionContributions;
    }

    public void addContribution(Role role, Contributor contributor) {
        if (role == null) {
            throw new NullPointerException("the role is null");
        }
        if (contributor == null) {
            throw new NullPointerException("the contributor is null");
        }
        if (role instanceof MusicianRole musicianRole) {
            if (this.musicalContributions.containsKey(musicianRole)) {
                this.musicalContributions.get(musicianRole).add(contributor);
            } else {
                ArrayList<Contributor> roleContributions = new ArrayList<>();
                roleContributions.add(contributor);
                this.musicalContributions.put(musicianRole, roleContributions);
            }
        } else if (role instanceof ProductionRole productionRole) {
            if (this.productionContributions.containsKey(productionRole)) {
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

    public void removeContribution(Role role, Contributor contributor) {
        if (role == null) {
            throw new NullPointerException("the role is null");
        }
        if (contributor == null) {
            throw new NullPointerException("the contributor is null");
        }
        if (role instanceof ProductionRole) {
            if (!this.productionContributions.get(role).remove(contributor))
                throw new NoSuchElementException("this contributor is not present in the contributions");
        } else if (role instanceof MusicianRole) {
            if (!this.musicalContributions.get(role).remove(contributor))
                throw new NoSuchElementException("this contributor is not present in the contributions");
        } else {
            throw new UnsupportedOperationException("This type or role is not yet implemented");
        }
    }
}
