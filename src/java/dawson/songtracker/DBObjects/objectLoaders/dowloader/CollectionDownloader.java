package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Distributions.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class CollectionDownloader {
    public static Collection loadCollection(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        if(!rs.next()) return null;
        ArrayList<Compilation> compilations = loadCollectionCompilations(connection, id);
        return new Collection(id, rs.getString("collection_name"), compilations);
    }

    private static boolean collectionExists(Connection connection, int id) throws SQLException {
        PreparedStatement pr = connection.prepareStatement("select * from collections where collection_id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();
        return rs.next();
    }

    private static ArrayList<Compilation> loadCollectionCompilations(Connection connection, int collectionId) throws SQLException {
        if(!collectionExists(connection, collectionId)) return null;
        PreparedStatement pr = connection.prepareStatement("select * from collectionCompilations where collection_id = ?");
        pr.setInt(1, collectionId);
        ResultSet rs = pr.executeQuery();
        if(!rs.next()) return new ArrayList<>();
        ArrayList<Compilation> compilations = new ArrayList<>();
        do {
            Compilation compilation = CompilationDownloader.loadCompilation(connection, rs.getInt("compilation_id"));
            compilations.add(compilation);
        } while (rs.next());
        return compilations;
    }
}
