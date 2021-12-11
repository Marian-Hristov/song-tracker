package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.distributions.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CollectionDownloader extends ObjectDownloader<Collection> {
    public CollectionDownloader(Connection connection) {
        super(connection);
    }

    private boolean isReleased(int collectionId) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from distributions where collection_id = ?");
        ps.setInt(1, collectionId);
        ResultSet rs = ps.executeQuery();
        boolean released = rs.next();
        ps.close();
        return released;
    }

    public int totalCollections() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from collections");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    private boolean collectionExists(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from collections where collection_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();
        ps.close();
        return exists;
    }

    private ArrayList<Compilation> loadCollectionCompilations(int collectionId) throws SQLException {
        if (!collectionExists(collectionId))
            throw new NoSuchElementException("the collection with collection_id: " + collectionId + "doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from collectionCompilations where collection_id = ?");
        ps.setInt(1, collectionId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new ArrayList<>();
        }
        ArrayList<Compilation> compilations = new ArrayList<>();
        do {
            ObjectDownloader<Compilation> dl = (ObjectDownloader<Compilation>) Downloader.getInstance().getLoader(Compilation.class);
            Compilation compilation = dl.load(rs.getInt("compilation_id"));
            compilations.add(compilation);
        } while (rs.next());
        ps.close();
        return compilations;
    }

    private ArrayList<Collection> loadCollectionsInSet(int setId) throws SQLException {
        if (!collectionExists(setId))
            throw new NoSuchElementException("the collection with collection_id: " + setId + "doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from collectionSets where set_id = ?");
        ps.setInt(1, setId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new ArrayList<>();
        }
        ArrayList<Collection> collections = new ArrayList<>();
        do {
            Collection collection = load(rs.getInt("collection_id"));
            collections.add(collection);
        } while (rs.next());
        ps.close();
        return collections;
    }

    @Override
    public Collection load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from collections where collection_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {

            ps.close();
            return null;
        }
        ArrayList<Compilation> compilations = loadCollectionCompilations(id);
        ArrayList<Collection> collectionsInSet = loadCollectionsInSet(id);
        Collection collection = new Collection(id, rs.getString("collection_name"), isReleased(id), compilations, collectionsInSet);
        ps.close();
        return collection;
    }

    @Override
    public Collection loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select collection_id from collections order by collection_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("collection_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<Collection> loadAll() throws SQLException {
        int total = totalCollections();
        return loadFirst(total);
    }

    @Override
    public ArrayList<Collection> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from collections fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<Collection> collections = new ArrayList<>();
        while(rs.next()){
            Collection collection = load(rs.getInt("collection_id"));
            collections.add(collection);
        }
        ps.close();
        return collections;
    }

    @Override
    public ArrayList<Collection> loadByName(String name) throws SQLException {
        if(name == null){
            throw new NullPointerException("the name is null");
        }
        PreparedStatement ps = this.connection.prepareStatement("select * from collections where collection_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Collection> collections = new ArrayList<>();
        while(rs.next()){
            Collection collection = load(rs.getInt("collection_id"));
            collections.add(collection);
        }
        ps.close();
        return collections;
    }
}
