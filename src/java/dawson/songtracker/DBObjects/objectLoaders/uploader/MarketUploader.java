package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class MarketUploader {
    private final Connection connection;

    public MarketUploader(Connection connection) {
        this.connection = connection;
    }

    public void addMarket(String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addMarket = this.connection.prepareStatement("EXECUTE MARKET_MGMT.ADDMARKET(?)");
            addMarket.setString(1, name);
            if(addMarket.executeUpdate() != 1){
                throw new SQLException("Couldn't add market");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add market");
        }
    }

    public void removeMarket(int id) throws Exception {
        if(id < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeMarket = this.connection.prepareStatement("EXECUTE MARKET_MGMT.REMOVEMARKET(?)");
            removeMarket.setInt(1, id);
            if(removeMarket.executeUpdate() != 1){
                throw new SQLException("Couldn't remove market");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't remove market");
        }
    }

    public void updateMarket(String oldName, String newName) throws Exception {
        if(oldName == null || oldName.equals("") || newName == null || newName.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement updateMarket = this.connection.prepareStatement("EXECUTE MARKET_MGMT.UPDATEMARKET(?, ?)");
            updateMarket.setString(1, oldName);
            updateMarket.setString(2, newName);
            if(updateMarket.executeUpdate() != 1){
                throw new SQLException("Couldn't update market");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update market");
        }
    }
}
