package dawson.songtracker.DBObjects.objectLoaders.uploader;

import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.types.Roles.Role;

import java.sql.*;

public class ObjectUploader{
    private final Connection connection;
    private static ObjectUploader instance = null;
    private final CollectionUploader collectionUploader;
    private final CompilationUploader compilationUploader;
    private final ContributorUploader contributorUploader;
    private final DistributionUploader distributionUploader;
    private final LabelUploader labelUploader;
    private final MarketUploader marketUploader;
    private final RecordingUploader recordingUploader;
    private final RoleUploader roleUploader;

    private ObjectUploader(Connection connection) throws Exception {
        if(connection == null){
            throw new Exception("Connection is null, cannot create uploader");
        } else {
            this.connection = connection;
            this.collectionUploader = new CollectionUploader(this.connection);
            this.compilationUploader = new CompilationUploader(this.connection);
            this.contributorUploader = new ContributorUploader(this.connection);
            this.distributionUploader = new DistributionUploader(this.connection);
            this.labelUploader = new LabelUploader(this.connection);
            this.marketUploader = new MarketUploader(this.connection);
            this.recordingUploader = new RecordingUploader(this.connection);
            this.roleUploader = new RoleUploader(this.connection);
        }
    }

    public static ObjectUploader getInstance() throws Exception {
        if(instance == null){
            instance = new ObjectUploader(DBConnection.getConnection());
        }
        return instance;
    }

    public void addCollection(String name) throws Exception {
        this.collectionUploader.addCollection(name);
    }

    public void addCompilationToCollection(int collectionId, int compilationId) throws Exception{
        this.collectionUploader.addCompilationToCollection(collectionId, compilationId);
    }

    public void removeCompilationToCollection(int collectionId, int compilationId) throws Exception {
        this.collectionUploader.removeCompilationToCollection(collectionId, compilationId);
    }

    public void updateCollection(int collectionId, String newName) throws Exception {
        this.collectionUploader.updateCollection(collectionId, newName);
    }

    public void addCompilation(String name) throws Exception {
        this.compilationUploader.addCompilation(name);
    }

    public void addSampleToCompilation(String compilationName, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponent, int sampleId, char sampleType) throws Exception {
        this.compilationUploader.addSampleToCompilation(compilationName, mainTrackOffset, durationInMainTrack, componentTrackOffset, durationOfComponent, sampleId, sampleType);
    }

    public void deleteSampleFromCompilation(int compilationId, int sampleId, int segmentId, char sampleType) throws Exception {
        this.compilationUploader.deleteSampleFromCompilation(compilationId, sampleId, segmentId, sampleType);
    }

    public void deleteCompilation(int id) throws Exception{
        this.compilationUploader.deleteCompilation(id);
    }

    public void updateCompilation(int id, String name) throws Exception {
        this.compilationUploader.updateCompilation(id, name);
    }

    public void addContributorToCompilation(int compilationId, int contributorId, int roleId) throws Exception{
        this.compilationUploader.addContributorToCompilation(compilationId, contributorId, roleId);
    }

    public void removeContributorToCompilation(int compilationId, int contributorId, int roleId) throws Exception{
        this.compilationUploader.removeContributorToCompilation(compilationId, contributorId, roleId);
    }

    public void addContributor(String name) throws Exception{
        this.contributorUploader.addContributor(name);
    }

    public void deleteContributor(String name) throws Exception {
        this.contributorUploader.deleteContributor(name);
    }

    public void updateContributor(String oldName, String newName) throws Exception{
        this.contributorUploader.updateContributor(oldName, newName);
    }

    public void addDistribution(int collectionId, Date releaseDate, int labelId, int marketId) throws Exception {
        this.distributionUploader.addDistribution(collectionId, releaseDate, labelId, marketId);
    }

    public void removeDistribution(int id) throws Exception {
        this.distributionUploader.removeDistribution(id);
    }

    public void updateDistribution(int distributionId, int collectionId, Date releaseDate, int labelId, int marketId) throws Exception {
        this.distributionUploader.updateDistribution(distributionId, collectionId, releaseDate, labelId, marketId);
    }

    public void addLabel(String name) throws Exception {
        this.labelUploader.addLabel(name);
    }

    public void removeLabel(int id) throws Exception {
        this.labelUploader.removeLabel(id);
    }

    public void updateLabel(String oldName, String newName) throws Exception {
        this.labelUploader.updateLabel(oldName, newName);
    }

    public void addMarket(String name) throws Exception {
        this.marketUploader.addMarket(name);
    }

    public void removeMarket(int id) throws Exception {
        this.marketUploader.removeMarket(id);
    }

    public void updateMarket(String oldName, String newName) throws Exception {
        this.marketUploader.updateMarket(oldName, newName);
    }

    public void addRecording(String name, double duration) throws Exception{
        this.recordingUploader.addRecording(name, duration);
    }

    public void removesRecording(int id) throws Exception{
        this.recordingUploader.removesRecording(id);
    }

    public void updateRecording(int id, String newName, double newDuration) throws Exception{
        this.recordingUploader.updateRecording(id, newName, newDuration);
    }

    public void addRole(char category, String name) throws Exception{
        this.roleUploader.addRole(category, name);
    }

    public void deleteRole(char category, String name) throws Exception {
        this.roleUploader.deleteRole(category, name);
    }

    public void updateRole(char category, String oldName, String newName) throws Exception{
        this.roleUploader.updateRole(category, oldName, newName);
    }

}