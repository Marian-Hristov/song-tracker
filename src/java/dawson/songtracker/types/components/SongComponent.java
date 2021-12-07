package dawson.songtracker.types.components;

import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.Role;

import java.sql.Timestamp;
import java.util.ArrayList;

public abstract class SongComponent {
    protected final int id;
    protected String name;
    protected final Timestamp creationTime;
    protected double duration;

    public SongComponent(int id, String name, Timestamp creationTime, double duration) {
        if (name == null) {
            throw new NullPointerException("the name is null");
        }
        if (creationTime == null) {
            throw new NullPointerException("the creationTime is null");
        }
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.duration = duration;

    }

    public boolean isReleased() {
        return true;
    }

    public boolean isUnreleased() {
        return true;
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

    public double getDuration() {
        return duration;
    }

    public String getDurationString() {
//        Duration of 1hour 2minutes 47seconds
//        duration = 3767
        int hours = (int) (duration - (duration % 3600)) / 3600;
        int seconds = (int) (duration - hours * 3600) % 60;
        int minutes = (int) (duration - hours * 3600 - seconds) / 60;
        return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + "."
                + (seconds < 10 ? "0" + seconds : seconds);
    }

    public abstract ArrayList<Contributor> getContributorsInRole(Role role);
}
