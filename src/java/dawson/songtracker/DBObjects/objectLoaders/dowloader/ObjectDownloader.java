package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Distributions.Distribution;
import dawson.songtracker.types.Distributions.Market;
import dawson.songtracker.types.Distributions.RecordLabel;
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
    public CompilationRole loadCompilationRole(int id) throws SQLException {
        return RoleDownloader.loadCompilationRole(this.connection, id);
    }

    public ProductionRole loadProductionRole(int id) throws SQLException {
        return RoleDownloader.loadProductionRole(this.connection, id);
    }

    public MusicianRole loadMusicianRole(int id) throws SQLException {
        return RoleDownloader.loadMusicianRole(this.connection, id);
    }

    public Contributor loadContributor(int id) throws SQLException {
        return RoleDownloader.loadContributor(this.connection, id);
    }

    // Components
    public Compilation loadCompilation(int id) throws SQLException {
        return CompilationDownloader.loadCompilation(this.connection, id);
    }

    public Recording loadRecording(int id) throws SQLException {
        return RecordingDownloader.loadRecording(this.connection, id);
    }

    // Collections
    public Collection loadCollection(int id) throws SQLException {
        return CollectionDownloader.loadCollection(this.connection, id);
    }

    // Distributions
    public Distribution loadDistribution(int id) throws SQLException {
        return DistributionDownloader.loadDistribution(this.connection, id);
    }

    public Market loadMarket(int id) throws SQLException {
        return DistributionDownloader.loadMarket(this.connection, id);
    }

    public RecordLabel loadLabel(int id) throws SQLException {
        return DistributionDownloader.loadRecordLabel(this.connection, id);
    }

}
