package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.types.DatabaseObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class ObjectDownloader<T extends DatabaseObject> {
    protected final Connection connection;
    public ObjectDownloader(Connection connection) {
        if(connection == null) throw new NullPointerException("the connection is null");
        this.connection = connection;
    }

    public abstract T load(int id) throws SQLException;
    public abstract T loadLast() throws SQLException;
    public abstract ArrayList<T> loadAll() throws SQLException;
    public abstract ArrayList<T> loadFirst(int nbObjects) throws SQLException;
    public abstract ArrayList<T> loadByName(String name) throws SQLException;


}
