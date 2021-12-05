package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.types.distributions.RecordLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class DistributionDownloader {
    public static Distribution loadDistribution(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        Collection collection = CollectionDownloader.loadCollection(connection, rs.getInt("collection_id"));
        RecordLabel label = loadRecordLabel(connection, rs.getInt("label_id"));
        Market market = loadMarket(connection, rs.getInt("market_id"));
        Distribution distribution = new Distribution(id, collection, rs.getDate("release_date"), label, market);
        rs.close();

        return distribution;
    }

    public static Market loadMarket(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from markets where market_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        Market market = new Market(id, rs.getString("market_name"));
        rs.close();
        return market;
    }

    public static ArrayList<Market> loadMarketsByName(Connection connection, String name) throws SQLException{
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from markets where market_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Market> markets = new ArrayList<>();
        while(rs.next()){
            Market market = loadMarket(connection, rs.getInt("market_id"));
            markets.add(market);
        }
        rs.close();
        return markets;
    }

    public static RecordLabel loadRecordLabel(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from recordlabels where label_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        RecordLabel recordLabel = new RecordLabel(id, rs.getString("label_name"));
        rs.close();
        return recordLabel;
    }

    public static ArrayList<RecordLabel> loadRecordLabelsByName(Connection connection, String name) throws SQLException{
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = connection.prepareStatement("select * from recordLabels where label_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<RecordLabel> recordLabels = new ArrayList<>();
        while(rs.next()){
            RecordLabel recordLabel = loadRecordLabel(connection, rs.getInt("label_id"));
            recordLabels.add(recordLabel);
        }
        rs.close();
        return recordLabels;
    }
}
