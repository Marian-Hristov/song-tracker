package dawson.songtracker.types;

import dawson.songtracker.types.recordings_contributions.Contributor;
import dawson.songtracker.types.recordings_contributions.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public abstract class SongComponent {
    protected final int id;
    protected String name;
    protected final Timestamp creationTime;
    protected int duration;
    protected Map<Role, ArrayList<Contributor>> contributions;

    public SongComponent(int id, String name, Timestamp creationTime, int duration, Map<Role, ArrayList<Contributor>> contributions) {
        if(name == null){
            throw new NullPointerException("the name is null");
        }
        if(creationTime == null){
            throw new NullPointerException("the creationTime is null");
        }
        if(contributions == null){
            throw new NullPointerException("the contributions are null");
        }
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.duration = duration;
        this.contributions = contributions;
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

    public Map<Role, ArrayList<Contributor>> getContributions() {
        return contributions;
    }

    public void setContributions(Map<Role, ArrayList<Contributor>> contributions) {
        this.contributions = contributions;
    }

    public void addContribution(Role role, Contributor contributor){
        if(role == null){
            throw new NullPointerException("the role is null");
        }
        if(contributor == null){
            throw new NullPointerException("the contributor is null");
        }
        if(this.contributions.containsKey(role)){
            this.contributions.get(role).add(contributor);
        } else {
            ArrayList<Contributor> roleContributions = new ArrayList<>();
            roleContributions.add(contributor);
            this.contributions.put(role, roleContributions);
        }
    }

    public void removeContribution(Role role, Contributor contributor){
        if(role == null){
            throw new NullPointerException("the role is null");
        }
        if(contributor == null){
            throw new NullPointerException("the contributor is null");
        }
        this.contributions.get(role).remove(contributor);
    }
}
