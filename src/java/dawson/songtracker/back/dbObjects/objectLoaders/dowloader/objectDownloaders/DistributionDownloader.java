package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DistributionDownloader extends ObjectDownloader<Distribution> {
    public DistributionDownloader(Connection connection) {
        super(connection);
    }

    private int totalDistributions() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from distributions");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    @Override
    public Distribution load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from distributions where distribution_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        ObjectDownloader<Collection> collectionDownloader = (ObjectDownloader<Collection>) Downloader.getInstance().getLoader(Collection.class);
        ObjectDownloader<RecordLabel> recordLabelDownloader = (ObjectDownloader<RecordLabel>) Downloader.getInstance().getLoader(RecordLabel.class);
        ObjectDownloader<Market> marketDownloader = (ObjectDownloader<Market>) Downloader.getInstance().getLoader(Market.class);

        Collection collection = collectionDownloader.load(rs.getInt("collection_id"));
        RecordLabel label = recordLabelDownloader.load(rs.getInt("label_id"));
        Market market = marketDownloader.load(rs.getInt("market_id"));
        Distribution distribution = new Distribution(id, collection, rs.getDate("release_date"), label, market);
        ps.close();

        return distribution;
    }

    @Override
    public Distribution loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select distribution_id from distributions order by distribution_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("distribution_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<Distribution> loadAll() throws SQLException {
        int total = totalDistributions();
        return loadFirst(total);
    }

    @Override
    public ArrayList<Distribution> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from distributions fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<Distribution> distributions = new ArrayList<>();
        while(rs.next()){
            Distribution distribution = load(rs.getInt("distribution_id"));
            distributions.add(distribution);
        }
        ps.close();
        return distributions;
    }

    @Override
    public ArrayList<Distribution> loadByName(String name) throws SQLException {
        return null;
    }
}
