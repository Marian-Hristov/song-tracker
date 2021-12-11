package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.distributions.RecordLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecordLabelDownloader extends ObjectDownloader<RecordLabel>{
    public RecordLabelDownloader(Connection connection) {
        super(connection);
    }

    private int totalRecordLabels() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from recordLabels");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    @Override
    public RecordLabel load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select label_name from recordLabels where label_id = ?");
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

    @Override
    public RecordLabel loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select label_id from recordLabels order by collection_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("label_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<RecordLabel> loadAll() throws SQLException {
        int total = totalRecordLabels();
        return loadFirst(total);
    }

    @Override
    public ArrayList<RecordLabel> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select label_id from recordLabels fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<RecordLabel> labels = new ArrayList<>();
        while(rs.next()){
            RecordLabel label = load(rs.getInt("label_id"));
            labels.add(label);
        }
        ps.close();
        return labels;
    }

    @Override
    public ArrayList<RecordLabel> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = this.connection.prepareStatement("select label_id from recordLabels where label_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<RecordLabel> recordLabels = new ArrayList<>();
        while(rs.next()){
            RecordLabel recordLabel = load(rs.getInt("label_id"));
            recordLabels.add(recordLabel);
        }
        ps.close();
        return recordLabels;
    }
}
