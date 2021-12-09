package dawson.songtracker.back.dbObjects.objectLoaders.dowloader;

import dawson.songtracker.back.dbObjects.DBConnection;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.back.types.logs.Log;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;


public class ObjectDownloader {
    private final Connection connection;
    private static ObjectDownloader instance = null;

    private ObjectDownloader(Connection connection) {
        this.connection = connection;
    }

    public static ObjectDownloader getInstance() throws SQLException {
        if (instance == null) {
            instance = new ObjectDownloader(DBConnection.getConnection());
        }
        return instance;
    }

    // Roles
    public CompilationRole loadCompilationRole(int id) throws SQLException {
        return RoleDownloader.loadCompilationRole(this.connection, id);
    }

    public ArrayList<CompilationRole> loadCompilationRolesByName(String name) throws SQLException{
        return RoleDownloader.loadCompilationRoleByName(this.connection, name);
    }

    public ArrayList<CompilationRole> loadFirstCompilationRoles(int nbRows) throws SQLException{
        return RoleDownloader.loadFirstCompilationRoles(this.connection, nbRows);
    }

    public ArrayList<CompilationRole> loadAllCompilationRoles() throws SQLException{
        int total = RoleDownloader.totalCompilationRoles(this.connection);
        return RoleDownloader.loadFirstCompilationRoles(this.connection, total);
    }

    public ProductionRole loadProductionRole(int id) throws SQLException {
        return RoleDownloader.loadProductionRole(this.connection, id);
    }

    public ArrayList<ProductionRole> loadProductionRolesByName(String name) throws SQLException {
        return RoleDownloader.loadProductionRolesByName(this.connection, name);
    }

    public ArrayList<ProductionRole> loadFirstProductionRoles(int nbRows) throws SQLException{
        return RoleDownloader.loadFirstProductionRoles(this.connection, nbRows);
    }

    public ArrayList<ProductionRole> loadAllProductionRoles() throws SQLException{
        int total = RoleDownloader.totalProductionRoles(this.connection);
        return RoleDownloader.loadFirstProductionRoles(this.connection, total);
    }

    public MusicianRole loadMusicianRole(int id) throws SQLException {
        return RoleDownloader.loadMusicianRole(this.connection, id);
    }

    public ArrayList<MusicianRole> loadMusicianRolesByName(String name) throws SQLException{
        return RoleDownloader.loadMusicianRolesByName(this.connection, name);
    }

    public ArrayList<MusicianRole> loadFirstMusicianRoles(int nbRows) throws SQLException{
        return RoleDownloader.loadFirstMusicianRoles(this.connection, nbRows);
    }

    public ArrayList<MusicianRole> loadAllMusicianRoles() throws SQLException{
        int total = RoleDownloader.totalMusicianRoles(this.connection);
        return RoleDownloader.loadFirstMusicianRoles(this.connection, total);
    }

    public Contributor loadContributor(int id) throws SQLException {
        return RoleDownloader.loadContributor(this.connection, id);
    }

    public ArrayList<Contributor> loadContributorsByName(String name) throws SQLException{
        return RoleDownloader.loadContributorsByName(this.connection, name);
    }

    public ArrayList<Contributor> loadFirstContributors(int nbRows) throws SQLException{
        return RoleDownloader.loadFirstContributors(this.connection, nbRows);
    }

    public ArrayList<Contributor> loadAllContributors() throws SQLException{
        int total = RoleDownloader.totalContributors(this.connection);
        return RoleDownloader.loadFirstContributors(this.connection, total);
    }

    // Components
    public Compilation loadCompilation(int id) throws SQLException {
        return CompilationDownloader.loadCompilation(this.connection, id);
    }

    public ArrayList<Compilation> loadCompilationsByName(String name) throws SQLException{
        return CompilationDownloader.loadCompilationsByName(this.connection, name);
    }


    public ArrayList<Compilation> loadFirstCompilations(int nbRows) throws SQLException {
        return CompilationDownloader.loadFirstCompilations(this.connection, nbRows);
    }

    public ArrayList<Compilation> loadAllCompilations() throws SQLException{
        int total = CompilationDownloader.totalCompilations(this.connection);
        return CompilationDownloader.loadFirstCompilations(this.connection, total);
    }

    public Recording loadRecording(int id) throws SQLException {
        return RecordingDownloader.loadRecording(this.connection, id);
    }

    public ArrayList<Recording> loadRecordingsByName(String name) throws SQLException{
        return RecordingDownloader.loadRecordingsByName(this.connection, name);
    }

    public ArrayList<Recording> loadFirstRecordings(int nbRows) throws SQLException{
        return RecordingDownloader.loadFirstRecordings(this.connection, nbRows);
    }

    public ArrayList<Recording> loadAllRecordings() throws SQLException{
        int total = RecordingDownloader.totalRecordings(this.connection);
        return RecordingDownloader.loadFirstRecordings(this.connection, total);
    }

    // Collections
    public Collection loadCollection(int id) throws SQLException {
        return CollectionDownloader.loadCollection(this.connection, id);
    }

    public ArrayList<Collection> loadFirstCollections(int nbRows) throws SQLException{
        return CollectionDownloader.loadFirstCollections(this.connection, nbRows);
    }

    public ArrayList<Collection> loadAllCollections() throws SQLException{
        int total = CollectionDownloader.totalCollections(this.connection);
        return CollectionDownloader.loadFirstCollections(this.connection, total);
    }

    public ArrayList<Collection> loadCollectionsByName(String name) throws SQLException{
        return CollectionDownloader.loadCollectionsByName(this.connection, name);
    }

    // Distributions
    public Distribution loadDistribution(int id) throws SQLException {
        return DistributionDownloader.loadDistribution(this.connection, id);
    }

    public ArrayList<Distribution> loadFirstDistributions(int nbRows) throws SQLException{
        return DistributionDownloader.loadFirstDistributions(this.connection, nbRows);
    }

    public ArrayList<Distribution> loadAllDistributions() throws SQLException{
        int total = DistributionDownloader.totalDistributions(this.connection);
        return DistributionDownloader.loadFirstDistributions(this.connection, total);
    }

    public Market loadMarket(int id) throws SQLException {
        return DistributionDownloader.loadMarket(this.connection, id);
    }

    public ArrayList<Market> loadMarketsByName(String name) throws SQLException{
        return DistributionDownloader.loadMarketsByName(this.connection, name);
    }

    public ArrayList<Market> loadFirstMarkets(int nbRows) throws SQLException{
        return DistributionDownloader.loadFirstMarkets(this.connection, nbRows);
    }

    public ArrayList<Market> loadAllMarkets() throws SQLException{
        int total = DistributionDownloader.totalMarkets(this.connection);
        return DistributionDownloader.loadFirstMarkets(this.connection, total);
    }

    public RecordLabel loadRecordLabel(int id) throws SQLException {
        return DistributionDownloader.loadRecordLabel(this.connection, id);
    }

    public ArrayList<RecordLabel> loadRecordLabelsByName(String name) throws SQLException{
        return DistributionDownloader.loadRecordLabelsByName(this.connection, name);
    }

    public ArrayList<RecordLabel> loadFirstRecordLabels(int nbRows) throws SQLException{
        return DistributionDownloader.loadFirstRecordLabels(this.connection, nbRows);
    }

    public ArrayList<RecordLabel> loadAllRecordLabels() throws SQLException{
        int total = DistributionDownloader.totalRecordLabels(this.connection);
        return DistributionDownloader.loadFirstRecordLabels(this.connection, total);
    }

    // util loaders

    public Map<Compilation, ArrayList<CompilationRole>> getContributorCompilationRoles(Contributor contributor) throws SQLException{
        return RoleDownloader.getContributorCompilationRoles(this.connection, contributor.getId());
    }

    public Map<Recording, ArrayList<MusicianRole>> getContributorMusicianRoles(Contributor contributor) throws SQLException{
        return RoleDownloader.getContributorMusicianRoles(this.connection, contributor.getId());
    }

    public Map<Recording, ArrayList<ProductionRole>> getContributorProductionRoles(Contributor contributor) throws SQLException{
        return RoleDownloader.getContributorProductionRoles(this.connection, contributor.getId());
    }

    public ArrayList<Log> getLastLogs(int nbLogs) throws SQLException {
        return LogDownloader.loadLastLogs(this.connection, nbLogs);
    }

}
