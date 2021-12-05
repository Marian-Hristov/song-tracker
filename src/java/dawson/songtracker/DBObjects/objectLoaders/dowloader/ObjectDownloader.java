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


public class ObjectDownloader {
    private Connection connection;
    private static ObjectDownloader instance = null;
    private final int ACTIONS_BEFORE_RESET = 5;
    private int nbActions = 0;

    private ObjectDownloader(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public static ObjectDownloader getInstance() throws SQLException {
        if (instance == null) {
            instance = new ObjectDownloader(DBConnection.getConnection());
        }
        return instance;
    }

    private void updateNbActions() throws SQLException {
        nbActions++;
        if(nbActions == ACTIONS_BEFORE_RESET){
            nbActions = 0;
            resetConnection();
        }
    }

    // Roles
    public CompilationRole loadCompilationRole(int id) throws SQLException {
        updateNbActions();
        return RoleDownloader.loadCompilationRole(this.connection, id);
    }

    public ArrayList<CompilationRole> loadFirstCompilationRoles(int nbRows) throws SQLException{
        ArrayList<CompilationRole> roles = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            CompilationRole compilationRole = loadCompilationRole(i);
            if (compilationRole == null) break;
            roles.add(compilationRole);
        }
        return roles;
    }

    public ProductionRole loadProductionRole(int id) throws SQLException {
        updateNbActions();
        return RoleDownloader.loadProductionRole(this.connection, id);
    }

    public ArrayList<ProductionRole> loadFirstProductionRoles(int nbRows) throws SQLException{
        ArrayList<ProductionRole> roles = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            ProductionRole productionRole = loadProductionRole(i);
            if (productionRole == null) break;
            roles.add(productionRole);
        }
        return roles;
    }

    public MusicianRole loadMusicianRole(int id) throws SQLException {
        updateNbActions();
        return RoleDownloader.loadMusicianRole(this.connection, id);
    }

    public ArrayList<MusicianRole> loadFirstMusicianRoles(int nbRows) throws SQLException{
        ArrayList<MusicianRole> roles = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            MusicianRole compilationRole = loadMusicianRole(i);
            if (compilationRole == null) break;
            roles.add(compilationRole);
        }
        return roles;
    }

    public Contributor loadContributor(int id) throws SQLException {
        updateNbActions();
        return RoleDownloader.loadContributor(this.connection, id);
    }

    public ArrayList<Contributor> loadFirstContributors(int nbRows) throws SQLException{
        ArrayList<Contributor> contributors = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Contributor contributor = loadContributor(i);
            if (contributor == null) break;
            contributors.add(contributor);
        }
        return contributors;
    }

    // Components
    public Compilation loadCompilation(int id) throws SQLException {
        updateNbActions();
        return CompilationDownloader.loadCompilation(this.connection, id);
    }

    private void resetConnection() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<Compilation> loadFirstCompilations(int nbRows) throws SQLException {
        ArrayList<Compilation> compilations = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Compilation compilation = loadCompilation(i);
            if (compilation == null) break;
            compilations.add(compilation);
        }
        return compilations;
    }

    public Recording loadRecording(int id) throws SQLException {
        updateNbActions();
        return RecordingDownloader.loadRecording(this.connection, id);
    }

    public ArrayList<Recording> loadFirstRecordings(int nbRows) throws SQLException{
        ArrayList<Recording> recordings = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Recording recording = loadRecording(i);
            if (recording == null) break;
            recordings.add(recording);
        }
        return recordings;
    }

    // Collections
    public Collection loadCollection(int id) throws SQLException {
        updateNbActions();
        return CollectionDownloader.loadCollection(this.connection, id);
    }

    public ArrayList<Collection> loadFirstCollections(int nbRows) throws SQLException{
        ArrayList<Collection> collections = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Collection collection = loadCollection(i);
            if (collection == null) break;
            collections.add(collection);
        }
        return collections;
    }

    // Distributions
    public Distribution loadDistribution(int id) throws SQLException {
        updateNbActions();
        return DistributionDownloader.loadDistribution(this.connection, id);
    }

    public ArrayList<Distribution> loadFirstDistributions(int nbRows) throws SQLException{
        ArrayList<Distribution> distributions = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Distribution distribution = loadDistribution(i);
            if (distribution == null) break;
            distributions.add(distribution);
        }
        return distributions;
    }

    public Market loadMarket(int id) throws SQLException {
        updateNbActions();
        return DistributionDownloader.loadMarket(this.connection, id);
    }

    public ArrayList<Market> loadFirstMarkets(int nbRows) throws SQLException{
        ArrayList<Market> markets = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Market market = loadMarket(i);
            if (market == null) break;
            markets.add(market);
        }
        return markets;
    }

    public RecordLabel loadLabel(int id) throws SQLException {
        updateNbActions();
        return DistributionDownloader.loadRecordLabel(this.connection, id);
    }

    public ArrayList<RecordLabel> loadFirstRecordLabels(int nbRows) throws SQLException{
        ArrayList<RecordLabel> labels = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            RecordLabel label = loadLabel(i);
            if (label == null) break;
            labels.add(label);
        }
        return labels;
    }

}
