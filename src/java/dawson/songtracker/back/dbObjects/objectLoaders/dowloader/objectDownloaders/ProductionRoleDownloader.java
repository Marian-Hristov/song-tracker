package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.roles.ProductionRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionRoleDownloader extends ObjectDownloader<ProductionRole> {
    public ProductionRoleDownloader(Connection connection) {
        super(connection);
    }

    private int totalProductionRoles() throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from productionRoles");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    @Override
    public ProductionRole load(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_name from productionRoles where role_id = ?");
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

    @Override
    public ProductionRole loadLast() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_id from productionRoles order by role_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("role_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<ProductionRole> loadAll() throws SQLException {
        int total = totalProductionRoles();
        return loadFirst(total);
    }

    @Override
    public ArrayList<ProductionRole> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select role_id from productionRoles fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<ProductionRole> roles = new ArrayList<>();
        while(rs.next()){
            ProductionRole role = load(rs.getInt("role_id"));
            roles.add(role);
        }
        ps.close();
        return roles;
    }

    @Override
    public ArrayList<ProductionRole> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select role_id from productionRoles where role_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<ProductionRole> productionRoles = new ArrayList<>();
        while(rs.next()){
            ProductionRole productionRole = load(rs.getInt("role_id"));
            productionRoles.add(productionRole);
        }
        ps.close();
        return productionRoles;
    }
}
