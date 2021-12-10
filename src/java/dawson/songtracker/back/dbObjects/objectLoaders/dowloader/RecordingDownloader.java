package dawson.songtracker.back.dbObjects.objectLoaders.dowloader;

import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

class RecordingDownloader {

    public static Recording loadRecording(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(connection, id);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(connection, id);
        Recording recording = new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"), rs.getInt("duration"), isReleased(connection, id), musicalContributions, productionContributions);
        ps.close();
        return recording;
    }

    public static ArrayList<Recording> loadFirstRecordings(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from recordings fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<Recording> recordings = new ArrayList<>();
        while(rs.next()){
            Recording recording = loadRecording(connection, rs.getInt("recording_id"));
            recordings.add(recording);
        }
        ps.close();
        return recordings;
    }

    public static boolean isReleased(Connection connection, int recordingId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from recordingSamples where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            if(CompilationDownloader.isReleased(connection, rs.getInt("compilation_id"))){
                return true;
            }
        }
        ps.close();
        return false;
    }

    public static int totalRecordings(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from recordings");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static ArrayList<Recording> loadRecordingsByName(Connection connection, String name) throws SQLException{
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from recordings where recording_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Recording> recordings = new ArrayList<>();
        while(rs.next()){
            Recording recording = loadRecording(connection, rs.getInt("recording_id"));
            recordings.add(recording);
        }
        ps.close();
        return recordings;
    }

    private static boolean recordingExists(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        ps.close();
        return exists;
    }

    private static Map<ProductionRole, ArrayList<Contributor>> loadProductionContributions(Connection connection, int recordingId) throws SQLException {
        if (!recordingExists(connection, recordingId))
            throw new NoSuchElementException("the recording with id: " + recordingId + " doesn't exist");
        PreparedStatement ps = connection.prepareStatement("select * from productionContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new HashMap<>();
        }
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

        ps.close();
        return productionContributions;
    }

    private static Map<MusicianRole, ArrayList<Contributor>> loadMusicalContributions(Connection connection, int recordingId) throws SQLException {
        if (!recordingExists(connection, recordingId))
            throw new NoSuchElementException("the recording with id: " + recordingId + " doesn't exist");
        PreparedStatement ps = connection.prepareStatement("select * from musicalContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new HashMap<>();
        }


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

        ps.close();

        return musicalContributions;
    }
}
