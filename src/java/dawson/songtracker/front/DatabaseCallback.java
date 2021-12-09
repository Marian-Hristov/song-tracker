package dawson.songtracker.front;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseCallback<T> {
    ArrayList<T> run() throws SQLException;
}
