package dawson.songtracker.back.dbObjects.objectLoaders.dowloader;

import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class DistributionDownloader {
    public static Distribution loadDistribution(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from distributions where distribution_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Collection collection = CollectionDownloader.loadCollection(connection, rs.getInt("collection_id"));
        RecordLabel label = loadRecordLabel(connection, rs.getInt("label_id"));
        Market market = loadMarket(connection, rs.getInt("market_id"));
        Distribution distribution = new Distribution(id, collection, rs.getDate("release_date"), label, market);
        ps.close();

        return distribution;
    }

    public static ArrayList<Distribution> loadFirstDistributions(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from distributions fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<Distribution> distributions = new ArrayList<>();
        while(rs.next()){
            Distribution distribution = loadDistribution(connection, rs.getInt("distribution_id"));
            distributions.add(distribution);
        }
        ps.close();
        return distributions;
    }

    public static int totalDistributions(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from distributions");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static Market loadMarket(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from markets where market_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Market market = new Market(id, rs.getString("market_name"));
        ps.close();
        return market;
    }

    public static ArrayList<Market> loadFirstMarkets(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from markets fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<Market> markets = new ArrayList<>();
        while(rs.next()){
            Market market = loadMarket(connection, rs.getInt("market_id"));
            markets.add(market);
        }
        ps.close();
        return markets;
    }

    public static int totalMarkets(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from markets");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
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
        ps.close();
        return markets;
    }

    public static RecordLabel loadRecordLabel(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from recordlabels where label_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        RecordLabel recordLabel = new RecordLabel(id, rs.getString("label_name"));
        ps.close();
        return recordLabel;
    }

    public static ArrayList<RecordLabel> loadFirstRecordLabels(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from recordLabels fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<RecordLabel> labels = new ArrayList<>();
        while(rs.next()){
            RecordLabel label = loadRecordLabel(connection, rs.getInt("label_id"));
            labels.add(label);
        }
        ps.close();
        return labels;
    }

    public static int totalRecordLabels(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from recordLabels");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
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
        ps.close();
        return recordLabels;
    }
}
