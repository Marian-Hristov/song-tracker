package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.roles.MusicianRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicianRoleDownloader extends ObjectDownloader<MusicianRole>{
    public MusicianRoleDownloader(Connection connection) {
        super(connection);
    }

    private int totalMusicianRoles() throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from musicianRoles");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    @Override
    public MusicianRole load(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_name from musicianRoles where role_id = ?");
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

    @Override
    public MusicianRole loadLast() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_id from musicianRoles order by role_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("role_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<MusicianRole> loadAll() throws SQLException {
        int total = totalMusicianRoles();
        return loadFirst(total);
    }

    @Override
    public ArrayList<MusicianRole> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_id from musicianRoles fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<MusicianRole> roles = new ArrayList<>();
        while(rs.next()){
            MusicianRole role = load(rs.getInt("role_id"));
            roles.add(role);
        }
        ps.close();
        return roles;
    }

    @Override
    public ArrayList<MusicianRole> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select role_id from musicianRoles where role_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<MusicianRole> musicianRoles = new ArrayList<>();
        while(rs.next()){
            MusicianRole musicianRole = load(rs.getInt("role_id"));
            musicianRoles.add(musicianRole);
        }
        ps.close();
        return musicianRoles;
    }
}
