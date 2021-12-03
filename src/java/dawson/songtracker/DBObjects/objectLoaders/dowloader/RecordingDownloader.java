package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.ProductionRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class RecordingDownloader {
    public static ArrayList<Recording> loadAllRecordings(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from recordings");
        ResultSet rs = ps.executeQuery();
        ArrayList<Recording> allRecordings = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("recording_id");
            Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(connection, id);
            Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(connection, id);
            allRecordings.add(new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"), rs.getInt("duration"), musicalContributions, productionContributions));
        }
        return allRecordings;
    }

    public static Recording loadRecording(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new NoSuchElementException("this recording with recording_id: "+id+" doesn't exist");
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(connection, id);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(connection, id);
        return new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"), rs.getInt("duration"), musicalContributions, productionContributions);
    }

    private static boolean recordingExists(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private static Map<ProductionRole, ArrayList<Contributor>> loadProductionContributions(Connection connection, int recordingId) throws SQLException {
        if(!recordingExists(connection, recordingId)) throw new NoSuchElementException("the recording with id: "+recordingId+" doesn't exist");
        PreparedStatement ps = connection.prepareStatement("select * from productionContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return new HashMap<>();

        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        do {
            ProductionRole productionRole = RoleDownloader.loadProductionRole(connection, rs.getInt("role_id"));
            Contributor contributor = RoleDownloader.loadContributor(connection, rs.getInt("contributor_id"));
            if (productionContributions.containsKey(productionRole)) {
                productionContributions.get(productionRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                productionContributions.put(productionRole, contributors);
            }
        } while (rs.next());
        return productionContributions;
    }

    private static Map<MusicianRole, ArrayList<Contributor>> loadMusicalContributions(Connection connection, int recordingId) throws SQLException {
        if(!recordingExists(connection, recordingId)) throw new NoSuchElementException("the recording with id: "+recordingId+" doesn't exist");
        PreparedStatement ps = connection.prepareStatement("select * from musicalContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return new HashMap<>();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        do {
            MusicianRole musicianRole = RoleDownloader.loadMusicianRole(connection, rs.getInt("role_id"));
            Contributor contributor = RoleDownloader.loadContributor(connection, rs.getInt("contributor_id"));
            if (musicalContributions.containsKey(musicianRole)) {
                musicalContributions.get(musicianRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                musicalContributions.put(musicianRole, contributors);
            }
        } while (rs.next());
        return musicalContributions;
    }
}