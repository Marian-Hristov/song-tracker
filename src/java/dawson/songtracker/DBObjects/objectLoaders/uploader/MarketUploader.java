package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class MarketUploader {
    private final Connection connection;

    public MarketUploader(Connection connection) {
        this.connection = connection;
    }

    public void addMarket(String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement addMarket = this.connection.prepareCall("{call MARKET_MGMT.ADDMARKET(?)}");
            addMarket.setString(1, name);
            if (addMarket.executeUpdate() != 1) {
                throw new SQLException("Couldn't add market");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void removeMarket(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement removeMarket = this.connection.prepareCall("{call MARKET_MGMT.REMOVEMARKET(?)}");
            removeMarket.setInt(1, id);
            if (removeMarket.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove market");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void updateMarket(String oldName, String newName) throws Exception {
        if (oldName == null || oldName.equals("") || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement updateMarket = this.connection.prepareCall("{call MARKET_MGMT.UPDATEMARKET(?, ?)}");
            updateMarket.setString(1, oldName);
            updateMarket.setString(2, newName);
            if (updateMarket.executeUpdate() != 1) {
                throw new SQLException("Couldn't update market");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }
}
