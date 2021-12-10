package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.types.distributions.Market;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

class MarketUploader implements IDBUploader<Market> {
    private final Connection connection;
    private final ObjectDownloader dl;

    public MarketUploader(Connection connection) throws Exception {
        this.connection = connection;
        this.dl = ObjectDownloader.getInstance();
    }

    public void addMarket(String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addMarket = this.connection.prepareCall("{call MARKET_MGMT.ADDMARKET(?)}");
        try {
            addMarket.setString(1, name);
            if (addMarket.executeUpdate() != 1) {
                throw new SQLException("Couldn't add market");
            }
            this.connection.commit();
            addMarket.close();
        } catch (Exception e) {
            this.connection.rollback();
            addMarket.close();
            throw e;
        }
    }

    public void removeMarket(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeMarket = this.connection.prepareCall("{call MARKET_MGMT.REMOVEMARKET(?)}");
        try {
            removeMarket.setInt(1, id);
            if (removeMarket.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove market");
            }
            this.connection.commit();
            removeMarket.close();
        } catch (Exception e) {
            this.connection.rollback();
            removeMarket.close();
            throw e;
        }
    }

    public void updateMarket(String oldName, String newName) throws Exception {
        if (oldName == null || oldName.equals("") || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement updateMarket = this.connection.prepareCall("{call MARKET_MGMT.UPDATEMARKET(?, ?)}");
        try {
            updateMarket.setString(1, oldName);
            updateMarket.setString(2, newName);
            if (updateMarket.executeUpdate() != 1) {
                throw new SQLException("Couldn't update market");
            }
            this.connection.commit();
            updateMarket.close();
        } catch (Exception e) {
            this.connection.rollback();
            updateMarket.close();
            throw e;
        }
    }

    @Override
    public void add(Market market) throws Exception{
        if(market == null){
            throw new Exception("Market is null");
        }
        this.addMarket(market.getName());
    }

    @Override
    public void remove(Market market) throws Exception{
        if(market == null){
            throw new Exception("Market is null");
        }
        this.removeMarket(market.getId());
    }

    @Override
    public void update(Market newMarket) throws Exception{
        if(newMarket == null){
            throw new Exception("Market is null");
        }
        Market oldMarket = this.dl.loadMarket(newMarket.getId());
        if(!oldMarket.getName().equals(newMarket.getName())) this.updateMarket(oldMarket.getName(), newMarket.getName());
    }
}
