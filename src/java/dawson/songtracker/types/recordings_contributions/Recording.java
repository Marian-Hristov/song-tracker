package dawson.songtracker.types.recordings_contributions;

import dawson.songtracker.types.SongComponent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class Recording extends SongComponent {

    public Recording(int id, String name, Timestamp creationTime, int duration, Map<MusicianRole, ArrayList<Contributor>> musicalContributions, Map<ProductionRole, ArrayList<Contributor>> productionContributions) {
        super(id, name, creationTime, duration, musicalContributions, productionContributions);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
