package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
