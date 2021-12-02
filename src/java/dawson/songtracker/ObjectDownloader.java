package dawson.songtracker;

import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class ObjectDownloader {
    private final Connection connection;
    private static ObjectDownloader instance = null;

    private ObjectDownloader(Connection connection) {
        this.connection = connection;
    }

    public static ObjectDownloader getInstance() throws SQLException {
        if(instance == null){
            instance = new ObjectDownloader(DBConnection.getConnection());
            return instance;
        }
        return instance;
    }

    // Roles
    public MusicianRole loadMusicianRole(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from musicianRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the musician role with id: " + id + " doesn't exist");
        return new MusicianRole(id, rs.getString("role_name"));
    }

    public ProductionRole loadProductionRole(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from productionRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the production role with id: " + id + " doesn't exist");
        return new ProductionRole(id, rs.getString("role_name"));
    }

    public CompilationRole loadCompilationRole(int id) throws  SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select * from compilationRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the compilation role with id: " + id + " doesn't exist");
        return new CompilationRole(id, rs.getString("role_name"));
    }

    public Contributor loadContributor(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the contributor with id: " + id + " doesn't exist");
        return new Contributor(id, rs.getString("contributor_name"));
    }

    // Components
    public ArrayList<Recording> loadAllRecordings() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from recordings");
        ResultSet rs = ps.executeQuery();
        ArrayList<Recording> allRecordings = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("recording_id");
            Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(id) == null ? new HashMap<>() : loadProductionContributions(id);
            Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(id) == null ? new HashMap<>() : loadMusicalContributions(id);
            allRecordings.add(new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"), rs.getInt("duration"), musicalContributions, productionContributions));
        }
        return allRecordings;
    }

    public Recording loadRecording(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(id) == null ? new HashMap<>() : loadProductionContributions(id);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(id) == null ? new HashMap<>() : loadMusicalContributions(id);
        return new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"), rs.getInt("duration"), musicalContributions, productionContributions);
    }

    private boolean recordingExists(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private Map<ProductionRole, ArrayList<Contributor>> loadProductionContributions(int recordingId) throws SQLException {
        if(!recordingExists(recordingId)) throw new NoSuchElementException("the recording with id: "+recordingId+" doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from productionContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        do {
            ProductionRole productionRole = loadProductionRole(rs.getInt("role_id"));
            Contributor contributor = loadContributor(rs.getInt("contributor_id"));
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

    private Map<MusicianRole, ArrayList<Contributor>> loadMusicalContributions(int recordingId) throws SQLException {
        if(!recordingExists(recordingId)) throw new NoSuchElementException("the recording with id: "+recordingId+" doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from musicalContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        do {
            MusicianRole musicianRole = loadMusicianRole(rs.getInt("role_id"));
            Contributor contributor = loadContributor(rs.getInt("contributor_id"));
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
