package dawson.songtracker.back.dbObjects.objectLoaders.dowloader;

import dawson.songtracker.back.dbObjects.DBConnection;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders.*;
import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class Downloader {
    private final Connection connection;
    private static Downloader instance = null;
    private final Map<Class<? extends DatabaseObject>, ObjectDownloader<? extends DatabaseObject>> loaders;

    private Downloader(Connection connection) {
        if(connection == null) throw new NullPointerException("the connection is null");
        this.connection = connection;
        loaders = new HashMap<>();
        loaders.put(Collection.class, new CollectionDownloader(this.connection));
        loaders.put(Compilation.class, new CompilationDownloader(this.connection));
        loaders.put(CompilationRole.class, new CompilationRoleDownloader(this.connection));
        loaders.put(Contributor.class, new ContributorDownloader(this.connection));
        loaders.put(Distribution.class, new DistributionDownloader(this.connection));
        loaders.put(Market.class, new MarketDownloader(this.connection));
        loaders.put(MusicianRole.class, new MusicianRoleDownloader(this.connection));
        loaders.put(ProductionRole.class, new ProductionRoleDownloader(this.connection));
        loaders.put(Recording.class, new RecordingDownloader(this.connection));
        loaders.put(RecordLabel.class, new RecordLabelDownloader(this.connection));
    }

    public ObjectDownloader<? extends DatabaseObject> getLoader(Class<? extends DatabaseObject> obClass){
        return loaders.get(obClass);
    }

    public static Downloader getInstance() throws SQLException {
        if (instance == null) {
            instance = new Downloader(DBConnection.getConnection());
        }
        return instance;
    }
}
