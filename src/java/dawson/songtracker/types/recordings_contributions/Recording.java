package dawson.songtracker.types.recordings_contributions;

import dawson.songtracker.types.SongComponent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class Recording extends SongComponent {
    public Recording(int id, String name, Timestamp creationTime, int duration, Map<Role, ArrayList<Contributor>> contributions){
        super(id, name, creationTime, duration, contributions);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
