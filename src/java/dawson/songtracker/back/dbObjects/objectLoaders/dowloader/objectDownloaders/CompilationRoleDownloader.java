package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.roles.CompilationRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompilationRoleDownloader extends ObjectDownloader<CompilationRole>{
    public CompilationRoleDownloader(Connection connection) {
        super(connection);
    }

    private int totalCompilationRoles() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from compilationRoles");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    @Override
    public CompilationRole load(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_name from compilationRoles where role_id = ?");
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

    @Override
    public CompilationRole loadLast() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_id from compilationRoles order by role_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("role_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<CompilationRole> loadAll() throws SQLException {
        int total = totalCompilationRoles();
        return loadFirst(total);
    }

    @Override
    public ArrayList<CompilationRole> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_id from compilationRoles fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<CompilationRole> compilationRoles = new ArrayList<>();
        while(rs.next()){
            CompilationRole compilationRole = load(rs.getInt("role_id"));
            compilationRoles.add(compilationRole);
        }
        ps.close();
        return compilationRoles;
    }

    @Override
    public ArrayList<CompilationRole> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select role_id from compilationRoles where role_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<CompilationRole> compilationRoles = new ArrayList<>();
        while(rs.next()){
            CompilationRole compilationRole = load(rs.getInt("role_id"));
            compilationRoles.add(compilationRole);
        }
        ps.close();
        return compilationRoles;
    }
}
