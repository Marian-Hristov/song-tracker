package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class ObjectDownloader {
    private final Connection connection;
    private static ObjectDownloader instance = null;

    private ObjectDownloader(Connection connection) {
        this.connection = connection;
    }

    public static ObjectDownloader getInstance() throws SQLException {
        if(instance == null){
            instance = new ObjectDownloader(DBConnection.getConnection());
        }
        return instance;
    }

    // Roles

    // Components

}
