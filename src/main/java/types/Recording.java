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

    public String getDurationString(){
//        Duration of 1hour 2minutes 47seconds
//        duration = 3707
//        int hours = duration % 3600
//        int minutes = (duration - 3600*hours) % 60
//        int seconds = duration - 3600*hours - 60*minutes
//        TODO: finish getDurationString() method
        return "";
    }

    public Map<Role, ArrayList<Contributor>> getContributions() {
        return contributions;
    }

    public void setContributions(Map<Role, ArrayList<Contributor>> contributions) {
        this.contributions = contributions;
    }


}
