package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.distributions.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

class CollectionDownloader {
    public static Collection loadCollection(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return null;
        }
        ArrayList<Compilation> compilations = loadCollectionCompilations(connection, id);
        ArrayList<Collection> collectionsInSet = loadCollectionsInSet(connection, id);
        Collection collection = new Collection(id, rs.getString("collection_name"), compilations, collectionsInSet);
        rs.close();
        return collection;
    }

    public static ArrayList<Collection> loadCollectionsByName(Connection connection, String name) throws SQLException {
        if(name == null){
            throw new NullPointerException("the name is null");
        }
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_name = ?");
        pr.setString(1, name);
        ResultSet rs = pr.executeQuery();
        ArrayList<Collection> collections = new ArrayList<>();
        while(rs.next()){
            Collection collection = loadCollection(connection, rs.getInt("collection_id"));
            collections.add(collection);
        }
        rs.close();
        return collections;
    }

    private static boolean collectionExists(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();

        boolean exists = rs.next();
        rs.close();
        return exists;
    }

    private static ArrayList<Compilation> loadCollectionCompilations(Connection connection, int collectionId) throws SQLException {
        if (!collectionExists(connection, collectionId))
            throw new NoSuchElementException("the collection with collection_id: " + collectionId + "doesn't exist");
        PreparedStatement pr = connection.prepareStatement("select * from collectionCompilations where collection_id = ?");
        pr.setInt(1, collectionId);
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return new ArrayList<>();
        }
        ArrayList<Compilation> compilations = new ArrayList<>();
        do {
            Compilation compilation = CompilationDownloader.loadCompilation(connection, rs.getInt("compilation_id"));
            compilations.add(compilation);
        } while (rs.next());
        rs.close();
        return compilations;
    }

    private static ArrayList<Collection> loadCollectionsInSet(Connection connection, int setId) throws SQLException {
        if (!collectionExists(connection, setId))
            throw new NoSuchElementException("the collection with collection_id: " + setId + "doesn't exist");
        PreparedStatement pr = connection.prepareStatement("select * from collectionSets where set_id = ?");
        pr.setInt(1, setId);
        ResultSet rs = pr.executeQuery();
        if (!rs.next()) {
            rs.close();
            return new ArrayList<>();
        }
        ArrayList<Collection> collections = new ArrayList<>();
        do {
            Collection collection = loadCollection(connection, rs.getInt("collection_id"));
            collections.add(collection);
        } while (rs.next());
        rs.close();
        return collections;
    }
}
