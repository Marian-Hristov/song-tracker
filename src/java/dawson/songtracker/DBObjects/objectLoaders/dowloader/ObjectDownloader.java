package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.*;

import java.sql.*;
import java.util.ArrayList;


public class ObjectDownloader {
    private Connection connection;
    private static ObjectDownloader instance = null;
    private final int ACTIONS_BEFORE_RESET = 1000;
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

    private void resetConnection() throws SQLException {
        nbActions = 0;
        this.connection.close();
        this.connection = DBConnection.getConnection();
    }

    private void updateNbActions() throws SQLException {
        nbActions++;
        if(nbActions == ACTIONS_BEFORE_RESET){
            resetConnection();
        }
    }

    // Roles
    public CompilationRole loadCompilationRole(int id) throws SQLException {
        updateNbActions();
        return RoleDownloader.loadCompilationRole(this.connection, id);
    }

    public ArrayList<CompilationRole> loadCompilationRolesByName(String name) throws SQLException{
        resetConnection();
        return RoleDownloader.loadCompilationRoleByName(this.connection, name);
    }

    public ArrayList<CompilationRole> loadFirstCompilationRoles(int nbRows) throws SQLException{
        ArrayList<CompilationRole> roles = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            CompilationRole compilationRole = loadCompilationRole(i);
            if (compilationRole == null) continue;
            roles.add(compilationRole);
        }
        return roles;
    }

    public ProductionRole loadProductionRole(int id) throws SQLException {
        updateNbActions();
        return RoleDownloader.loadProductionRole(this.connection, id);
    }

    public ArrayList<ProductionRole> loadProductionRolesByName(String name) throws SQLException {
        resetConnection();
        return RoleDownloader.loadProductionRolesByName(this.connection, name);
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

    public ArrayList<MusicianRole> loadMusicianRolesByName(String name) throws SQLException{
        resetConnection();
        return RoleDownloader.loadMusicianRolesByName(this.connection, name);
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

    public ArrayList<Contributor> loadContributorsByName(String name) throws SQLException{
        resetConnection();
        return RoleDownloader.loadContributorsByName(this.connection, name);
    }

    public ArrayList<Contributor> loadFirstContributors(int nbRows) throws SQLException{
        ArrayList<Contributor> contributors = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Contributor contributor = loadContributor(i);
            if (contributor == null) continue;
            contributors.add(contributor);
        }
        return contributors;
    }

    // Components
    public Compilation loadCompilation(int id) throws SQLException {
        resetConnection();
        return CompilationDownloader.loadCompilation(this.connection, id);
    }

    public ArrayList<Compilation> loadCompilationsByName(String name) throws SQLException{
        resetConnection();
        return CompilationDownloader.loadCompilationsByName(this.connection, name);
    }


    public ArrayList<Compilation> loadFirstCompilations(int nbRows) throws SQLException {
        ArrayList<Compilation> compilations = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Compilation compilation = loadCompilation(i);
            if (compilation == null) continue;
            compilations.add(compilation);
        }
        return compilations;
    }

    public Recording loadRecording(int id) throws SQLException {
        updateNbActions();
        return RecordingDownloader.loadRecording(this.connection, id);
    }

    public ArrayList<Recording> loadRecordingsByName(String name) throws SQLException{
        resetConnection();
        return RecordingDownloader.loadRecordingsByName(this.connection, name);
    }

    public ArrayList<Recording> loadFirstRecordings(int nbRows) throws SQLException{
        ArrayList<Recording> recordings = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            Recording recording = loadRecording(i);
            if (recording == null) continue;
            recordings.add(recording);
        }
        return recordings;
    }

    // Collections
    public Collection loadCollection(int id) throws SQLException {
        resetConnection();
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

    public ArrayList<Collection> loadCollectionsByName(String name) throws SQLException{
        resetConnection();
        return CollectionDownloader.loadCollectionsByName(this.connection, name);
    }

    // Distributions
    public Distribution loadDistribution(int id) throws SQLException {
        resetConnection();
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

    public ArrayList<Market> loadMarketsByName(String name) throws SQLException{
        resetConnection();
        return DistributionDownloader.loadMarketsByName(this.connection, name);
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

    public RecordLabel loadRecordLabel(int id) throws SQLException {
        updateNbActions();
        return DistributionDownloader.loadRecordLabel(this.connection, id);
    }

    public ArrayList<RecordLabel> loadRecordLabelsByName(String name) throws SQLException{
        resetConnection();
        return DistributionDownloader.loadRecordLabelsByName(this.connection, name);
    }

    public ArrayList<RecordLabel> loadFirstRecordLabels(int nbRows) throws SQLException{
        ArrayList<RecordLabel> labels = new ArrayList<>();
        for (int i = 1; i < nbRows; i++) {
            RecordLabel label = loadRecordLabel(i);
            if (label == null) break;
            labels.add(label);
        }
        return labels;
    }

}
