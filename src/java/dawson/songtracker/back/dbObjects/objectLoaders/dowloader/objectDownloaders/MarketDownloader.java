package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.distributions.Market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarketDownloader extends ObjectDownloader<Market> {

    public MarketDownloader(Connection connection) {
        super(connection);
    }

    private int totalMarkets() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from markets");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    @Override
    public Market load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select market_name from markets where market_id = ?");
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

    @Override
    public Market loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select market_id from markets order by market_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("market_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<Market> loadAll() throws SQLException {
        int total = totalMarkets();
        return loadFirst(total);
    }

    @Override
    public ArrayList<Market> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select market_id from markets fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<Market> markets = new ArrayList<>();
        while(rs.next()){
            Market market = load(rs.getInt("market_id"));
            markets.add(market);
        }
        ps.close();
        return markets;
    }

    @Override
    public ArrayList<Market> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = this.connection.prepareStatement("select market_id from markets where market_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Market> markets = new ArrayList<>();
        while(rs.next()){
            Market market = load(rs.getInt("market_id"));
            markets.add(market);
        }
        ps.close();
        return markets;
    }
}
