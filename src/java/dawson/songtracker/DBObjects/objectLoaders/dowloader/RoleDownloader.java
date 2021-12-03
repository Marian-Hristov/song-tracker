package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.ProductionRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Class can only be accessed by the package and not outside
class RoleDownloader {

    public static MusicianRole loadMusicianRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from musicianRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the musician role with id: " + id + " doesn't exist");
        return new MusicianRole(id, rs.getString("role_name"));
    }

    public static ProductionRole loadProductionRole(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from productionRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the production role with id: " + id + " doesn't exist");
        return new ProductionRole(id, rs.getString("role_name"));
    }

    public static CompilationRole loadCompilationRole(Connection connection, int id) throws  SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from compilationRoles where role_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the compilation role with id: " + id + " doesn't exist");
        return new CompilationRole(id, rs.getString("role_name"));
    }

    public static Contributor loadContributor(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new IllegalArgumentException("the contributor with id: " + id + " doesn't exist");
        return new Contributor(id, rs.getString("contributor_name"));
    }
}