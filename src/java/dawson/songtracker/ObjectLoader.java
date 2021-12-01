package dawson.songtracker;
import dawson.songtracker.types.compilations_samples.CompilationRole;
import dawson.songtracker.types.recordings_contributions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ObjectLoader {
    private final Connection connection;
    public ObjectLoader(Connection connection){
        this.connection = connection;
    }
    public MusicianRole loadMusicianRole(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from musicianRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the musician role with id: "+id+" doesn't exist");
        return new MusicianRole(id, rs.getString("role_name"));
    }
    public ProductionRole loadProductionRole(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from productionRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the production role with id: "+id+" doesn't exist");
        return new ProductionRole(id, rs.getString("role_name"));
    }
    public CompilationRole loadCompilationRole(int id) throws  SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select * from compilationRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the compilation role with id: "+id+" doesn't exist");
        return new CompilationRole(id, rs.getString("role_name"));
    }
    public Contributor loadContributor(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the contributor with id: "+id+" doesn't exist");
        return new Contributor(id, rs.getString("contributor_name"));
    }
    public Recording loadRecording(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the recording with id: "+id+" doesn't exist");
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(id);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(id);
        return new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"),rs.getInt("duration"), musicalContributions, productionContributions);
    }

    private Map<ProductionRole, ArrayList<Contributor>> loadProductionContributions(int recordingId) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from productionContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the recording with id: "+recordingId+" doesn't exist or doesn't have a production contribution");
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        do{
            ProductionRole productionRole = loadProductionRole(rs.getInt("role_id"));
            Contributor contributor = loadContributor(rs.getInt("contributor_id"));
            if(productionContributions.containsKey(productionRole)){
                productionContributions.get(productionRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                productionContributions.put(productionRole, contributors);
            }
        } while(rs.next());
        return productionContributions;
    }
    private Map<MusicianRole, ArrayList<Contributor>> loadMusicalContributions(int recordingId) throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select * from musicalContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("the recording with id: "+recordingId+" doesn't exist or doesn't have a musical contribution");
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        do{
            MusicianRole musicianRole = loadMusicianRole(rs.getInt("role_id"));
            Contributor contributor = loadContributor(rs.getInt("contributor_id"));
            if(musicalContributions.containsKey(musicianRole)){
                musicalContributions.get(musicianRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                musicalContributions.put(musicianRole, contributors);
            }
        } while(rs.next());
        return musicalContributions;
    }
}
