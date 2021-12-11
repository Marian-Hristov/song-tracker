package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.logs.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogDownloader{
    public static ArrayList<Log> loadLastLogs(Connection connection, int nbLogs) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from stLogs order by lod_id desc fetch first ? rows only");
        ps.setInt(1, nbLogs);
        ResultSet rs = ps.executeQuery();
        ArrayList<Log> logs = new ArrayList<>();
        while(rs.next()){
            Log log = new Log(rs.getInt("log_id"), rs.getString("log_message"), rs.getTimestamp("log_time"));
            logs.add(log);
        }
        return logs;
    }
}
