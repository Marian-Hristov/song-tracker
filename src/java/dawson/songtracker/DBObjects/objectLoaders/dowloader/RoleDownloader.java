package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.ProductionRole;

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
<<<<<<< HEAD
        if (!rs.next()) {
=======
        

        if (!rs.next()){
>>>>>>> d99cd5f4f4a7dfa247f2744ba45d0bd4551650c0
            rs.close();
            return null;
        }

        MusicianRole musicianRole = new MusicianRole(id, rs.getString("role_name"));

        rs.close();
        return musicianRole;
    }

    public static ProductionRole loadProductionRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from productionRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
<<<<<<< HEAD
        if (!rs.next()) {
=======
        

        if (!rs.next()){
>>>>>>> d99cd5f4f4a7dfa247f2744ba45d0bd4551650c0
            rs.close();
            return null;
        }

        ProductionRole productionRole = new ProductionRole(id, rs.getString("role_name"));

        rs.close();
        return productionRole;
    }

    public static CompilationRole loadCompilationRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from compilationRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
<<<<<<< HEAD
        if (!rs.next()) {
=======
        

        if (!rs.next()){
>>>>>>> d99cd5f4f4a7dfa247f2744ba45d0bd4551650c0
            rs.close();
            return null;
        }

        CompilationRole compilationRole = new CompilationRole(id, rs.getString("role_name"));

        rs.close();
        return compilationRole;
    }

    public static Contributor loadContributor(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
<<<<<<< HEAD
        if (!rs.next()) {
=======
        

        if (!rs.next()){
>>>>>>> d99cd5f4f4a7dfa247f2744ba45d0bd4551650c0
            rs.close();
            return null;
        }

        Contributor contributor = new Contributor(id, rs.getString("contributor_name"));

        rs.close();
        return contributor;
    }
}
