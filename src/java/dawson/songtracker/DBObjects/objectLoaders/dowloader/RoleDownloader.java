package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class can only be accessed by the package and not outside
class RoleDownloader {


    public static MusicianRole loadMusicianRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from musicianRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        MusicianRole musicianRole = new MusicianRole(id, rs.getString("role_name"));
        rs.close();
        return musicianRole;
    }

    public static ArrayList<MusicianRole> loadMusicianRolesByName(Connection connection, String name) throws SQLException{
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from musicianRoles where role_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<MusicianRole> musicianRoles = new ArrayList<>();
        while(rs.next()){
            MusicianRole musicianRole = loadMusicianRole(connection, rs.getInt("role_id"));
            musicianRoles.add(musicianRole);
        }
        rs.close();
        return musicianRoles;
    }

    public static ProductionRole loadProductionRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from productionRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        ProductionRole productionRole = new ProductionRole(id, rs.getString("role_name"));
        rs.close();
        return productionRole;
    }

    public static ArrayList<ProductionRole> loadProductionRolesByName(Connection connection, String name) throws SQLException{
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from productionRoles where role_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<ProductionRole> productionRoles = new ArrayList<>();
        while(rs.next()){
            ProductionRole productionRole = loadProductionRole(connection, rs.getInt("role_id"));
            productionRoles.add(productionRole);
        }
        rs.close();
        return productionRoles;
    }

    public static CompilationRole loadCompilationRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from compilationRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        CompilationRole compilationRole = new CompilationRole(id, rs.getString("role_name"));
        rs.close();
        return compilationRole;
    }

    public static ArrayList<CompilationRole> loadCompilationRoleByName(Connection connection, String name) throws SQLException{
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from compilationRoles where role_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<CompilationRole> compilationRoles = new ArrayList<>();
        while(rs.next()){
            CompilationRole compilationRole = loadCompilationRole(connection, rs.getInt("role_id"));
            compilationRoles.add(compilationRole);
        }
        rs.close();
        return compilationRoles;
    }

    public static Contributor loadContributor(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }

        Contributor contributor = new Contributor(id, rs.getString("contributor_name"));

        rs.close();
        return contributor;
    }

    public static ArrayList<Contributor> loadContributorsByName(Connection connection, String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from contributors where contributor_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Contributor> contributors = new ArrayList<>();
        while(rs.next()){
            Contributor contributor = loadContributor(connection, rs.getInt("contributor_id"));
            contributors.add(contributor);
        }
        rs.close();
        return contributors;
    }

    /**
     * gets all the contributions a contributor has made for a compilation
     * @param contributorId the id of the contributor
     * @return a HashMap containing the id of the compilation and an arrayList containing what compilationRoles that contributor had for that compilation
     */
    public static Map<Compilation, ArrayList<CompilationRole>> getContributorCompilationRoles(Connection connection, int contributorId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from compilationContributions where contributor_id = ?");
        ps.setInt(1, contributorId);
        ResultSet rs = ps.executeQuery();
        Map<Compilation, ArrayList<CompilationRole>> contributions = new HashMap<>();
        while(rs.next()){
            // Fake compilation equaling the compilation in that row to check if it already is in the map without loading it from the db
            Compilation compilation = new Compilation(rs.getInt("compilation_id"), "", new Timestamp(0), 0, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
            CompilationRole compilationRole = loadCompilationRole(connection, rs.getInt("role_id"));
            if(contributions.containsKey(compilation)){
                contributions.get(compilation).add(compilationRole);
            } else {
                // if the compilation is not in the map load the whole compilation from the db and add it
                compilation = CompilationDownloader.loadCompilation(connection, compilation.getId());
                ArrayList<CompilationRole> compilationRoles = new ArrayList<>();
                compilationRoles.add(compilationRole);
                contributions.put(compilation, compilationRoles);
            }
        }
        rs.close();
        return contributions;
    }

    public static Map<Recording, ArrayList<ProductionRole>> getContributorProductionRoles(Connection connection, int contributorId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from productionContributions where contributor_id = ?");
        ps.setInt(1, contributorId);
        ResultSet rs = ps.executeQuery();
        Map<Recording, ArrayList<ProductionRole>> contributions = new HashMap<>();
        while(rs.next()){
            // Fake compilation equaling the compilation in that row to check if it already is in the map without loading it from the db
            Recording recording = new Recording(rs.getInt("recording_id"), "", new Timestamp(0), 0, new HashMap<>(),new HashMap<>());
            ProductionRole productionRole = loadProductionRole(connection, rs.getInt("role_id"));
            if(contributions.containsKey(recording)){
                contributions.get(recording).add(productionRole);
            } else {
                // if the recording is not in the map load the whole recording from the db and add it
                recording = RecordingDownloader.loadRecording(connection, recording.getId());
                ArrayList<ProductionRole> productionRoles = new ArrayList<>();
                productionRoles.add(productionRole);
                contributions.put(recording, productionRoles);
            }
        }
        rs.close();
        return contributions;
    }

    public static Map<Recording, ArrayList<MusicianRole>> getContributorMusicianRoles(Connection connection, int contributorId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from productionContributions where contributor_id = ?");
        ps.setInt(1, contributorId);
        ResultSet rs = ps.executeQuery();
        Map<Recording, ArrayList<MusicianRole>> contributions = new HashMap<>();
        while(rs.next()){
            // Fake compilation equaling the compilation in that row to check if it already is in the map without loading it from the db
            Recording recording = new Recording(rs.getInt("recording_id"), "", new Timestamp(0), 0, new HashMap<>(),new HashMap<>());
            MusicianRole musicianRole = loadMusicianRole(connection, rs.getInt("role_id"));
            if(contributions.containsKey(recording)){
                contributions.get(recording).add(musicianRole);
            } else {
                // if the recording is not in the map load the whole recording from the db and add it
                recording = RecordingDownloader.loadRecording(connection, recording.getId());
                ArrayList<MusicianRole> musicianRoles = new ArrayList<>();
                musicianRoles.add(musicianRole);
                contributions.put(recording, musicianRoles);
            }
        }
        rs.close();
        return contributions;
    }
}
