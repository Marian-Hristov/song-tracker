package types;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class Recording {
    private String id;
    private String name;
    private Timestamp creation_time;
    private int duration;
    private Map<Role, ArrayList<Contributor>> contributions;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Timestamp getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Timestamp creation_time) {
        this.creation_time = creation_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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


}
