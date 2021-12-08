package dawson.songtracker.dbObjects.objectLoaders.dowloader;

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
            ps.close();
            return null;
        }
        MusicianRole musicianRole = new MusicianRole(id, rs.getString("role_name"));
        ps.close();
        return musicianRole;
    }

    public static int totalMusicianRoles(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from musicianRoles");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static ArrayList<MusicianRole> loadFirstMusicianRoles(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from musicianRoles fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<MusicianRole> roles = new ArrayList<>();
        while(rs.next()){
            MusicianRole role = loadMusicianRole(connection, rs.getInt("role_id"));
            roles.add(role);
        }
        ps.close();
        return roles;
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
        ps.close();
        return musicianRoles;
    }

    public static ProductionRole loadProductionRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from productionRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        ProductionRole productionRole = new ProductionRole(id, rs.getString("role_name"));
        ps.close();
        return productionRole;
    }

    public static int totalProductionRoles(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from productionRoles");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static ArrayList<ProductionRole> loadFirstProductionRoles(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from productionRoles fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<ProductionRole> roles = new ArrayList<>();
        while(rs.next()){
            ProductionRole role = loadProductionRole(connection, rs.getInt("role_id"));
            roles.add(role);
        }
        ps.close();
        return roles;
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
        ps.close();
        return productionRoles;
    }

    public static CompilationRole loadCompilationRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from compilationRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        CompilationRole compilationRole = new CompilationRole(id, rs.getString("role_name"));
        ps.close();
        return compilationRole;
    }

    public static int totalCompilationRoles(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from compilationRoles");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static ArrayList<CompilationRole> loadFirstCompilationRoles(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from compilationRoles fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<CompilationRole> compilationRoles = new ArrayList<>();
        while(rs.next()){
            CompilationRole compilationRole = loadCompilationRole(connection, rs.getInt("role_id"));
            compilationRoles.add(compilationRole);
        }
        ps.close();
        return compilationRoles;
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
        ps.close();
        return compilationRoles;
    }

    public static Contributor loadContributor(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Contributor contributor = new Contributor(id, rs.getString("contributor_name"));
        ps.close();
        return contributor;
    }

    public static int totalContributors(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from contributors");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static ArrayList<Contributor> loadFirstContributors(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from contributors fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<Contributor> contributors = new ArrayList<>();
        while(rs.next()){
            Contributor contributor = loadContributor(connection, rs.getInt("contributor_id"));
            contributors.add(contributor);
        }
        ps.close();
        return contributors;
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
        ps.close();
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
        ps.close();
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
        ps.close();
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
        ps.close();
        return contributions;
    }
}
