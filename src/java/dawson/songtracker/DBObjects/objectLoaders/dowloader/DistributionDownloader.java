package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Distributions.Distribution;
import dawson.songtracker.types.Distributions.Market;
import dawson.songtracker.types.Distributions.RecordLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        Market market = new Market(id, rs.getString("market_name"));
        rs.close();
        return market;
    }

    public static RecordLabel loadRecordLabel(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from labels where label_id = ?");
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        RecordLabel recordLabel = new RecordLabel(id, rs.getString("label_name"));
        rs.close();
        return recordLabel;
    }
}
