package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Distributions.Distribution;
import dawson.songtracker.types.Distributions.Market;
import dawson.songtracker.types.Distributions.RecordLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

class DistributionDownloader {
    public static Distribution loadDistribution(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        if(!rs.next()) throw new NoSuchElementException("the distribution with id: "+id+" doesn't exist");
        Collection collection = CollectionDownloader.loadCollection(connection, rs.getInt("collection_id"));
        RecordLabel label = loadRecordLabel(connection, rs.getInt("label_id"));
        Market market = loadMarket(connection, rs.getInt("market_id"));
        return new Distribution(id, collection, rs.getDate("release_date"), label, market);
    }

    public static Market loadMarket(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from markets where market_id = ?");
        ResultSet rs = pr.executeQuery();
        if(!rs.next()) throw new NoSuchElementException("the market with id: "+id+" doesn't exist");
        return new Market(id, rs.getString("market_name"));
    }

    public static RecordLabel loadRecordLabel(Connection connection, int id) throws SQLException{
        PreparedStatement pr = connection.prepareStatement("select * from labels where label_id = ?");
        ResultSet rs = pr.executeQuery();
        if(!rs.next()) throw new NoSuchElementException("the label with id: "+id+" doesn't exist");
        return new RecordLabel(id, rs.getString("label_name"));
    }
}
