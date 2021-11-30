package dawson.songtracker.types.album_distributions;

import dawson.songtracker.types.album_distributions.Collection;
import javafx.scene.control.Label;

import java.sql.Date;

public class Distribution {
    private int id;
    private Collection collection;
    private Date releaseDate;
    private Label label;
    private Market market;
}
