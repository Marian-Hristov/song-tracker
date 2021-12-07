package dawson.songtracker.DBObjects.objectLoaders.uploader;

import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.components.Segment;
import dawson.songtracker.types.components.SongComponent;
import dawson.songtracker.types.distributions.*;
import dawson.songtracker.types.roles.*;

import java.sql.*;

public class ObjectUploader {
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
        if (connection == null) {
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
        if (instance == null) {
            instance = new ObjectUploader(DBConnection.getConnection());
        }
        return instance;
    }

    public <T extends Object> void add(T entry) {
    }

    public void addCollection(Collection collection) throws Exception {
        if (collection == null) {
            throw new Exception("Collection is null");
        }
        this.collectionUploader.addCollection(collection.getName());
    }

    public void addCompilationToCollection(Collection collection, Compilation compilation) throws Exception {
        if (collection == null || compilation == null) {
            throw new Exception("One or many given objects are null");
        }
        this.collectionUploader.addCompilationToCollection(collection.getId(), compilation.getId());
    }

    public void removeCompilationFromCollection(Collection collection, Compilation compilation) throws Exception {
        if (collection == null || compilation == null) {
            throw new Exception("One or many given objects are null");
        }
        this.collectionUploader.removeCompilationFromCollection(collection.getId(), compilation.getId());
    }

    public void updateCollection(Collection oldCollection, Collection newCollection) throws Exception {
        if (oldCollection == null || newCollection == null) {
            throw new Exception("One or many given objects are null");
        }
        this.collectionUploader.updateCollection(oldCollection.getId(), newCollection.getName());
    }

    public void addCompilation(Compilation compilation) throws Exception {
        if (compilation == null) {
            throw new Exception("Compilation is null");
        }
        this.compilationUploader.addCompilation(compilation.getName());
    }

    public void addSampleToCompilation(Segment<SongComponent> segment) throws Exception {
        if (segment == null) {
            throw new Exception("Segment is null");
        }
        if (segment.getComponentTrack() instanceof Compilation) {
            this.compilationUploader.addSampleToCompilation(segment.getMainTrackId(), segment.getMainTrackOffset(), segment.getDurationInMainTrack(), segment.getComponentTrackOffset(), segment.getDurationOfComponentUsed(), segment.getComponentTrack().getId(), 'c');
        } else if (segment.getComponentTrack() instanceof Recording) {
            this.compilationUploader.addSampleToCompilation(segment.getMainTrackId(), segment.getMainTrackOffset(), segment.getDurationInMainTrack(), segment.getComponentTrackOffset(), segment.getDurationOfComponentUsed(), segment.getComponentTrack().getId(), 'r');
        } else {
            throw new Exception("Segment has an invalid component track");
        }
    }

    public void deleteSampleFromCompilation(Segment<SongComponent> segment) throws Exception {
        if (segment == null) {
            throw new Exception("Segment is null");
        }
        if (segment.getComponentTrack() instanceof Compilation) {
            this.compilationUploader.deleteSampleFromCompilation(segment.getMainTrackId(), segment.getComponentTrack().getId(), segment.getId(), 'c');
        } else if (segment.getComponentTrack() instanceof Recording) {
            this.compilationUploader.deleteSampleFromCompilation(segment.getMainTrackId(), segment.getComponentTrack().getId(), segment.getId(), 'r');
        } else {
            throw new Exception("Segment has an invalid component track");
        }
    }

    public void deleteCompilation(int id) throws Exception {
        this.compilationUploader.deleteCompilation(id);
    }

    public void updateCompilation(Compilation oldComp, Compilation newComp) throws Exception {
        if (oldComp == null || newComp == null) {
            throw new Exception("One or many given objects are null");
        }
        this.compilationUploader.updateCompilation(oldComp.getId(), newComp.getName());
    }

    public void addContributorToCompilation(Compilation compilation, Contributor contributor, Role role) throws Exception {
        if (compilation == null || contributor == null || role == null) {
            throw new Exception("One or many given objects are null");
        }
        this.compilationUploader.addContributorToCompilation(compilation.getId(), contributor.getId(), role.getId());
    }

    public void removeContributorToCompilation(Compilation compilation, Contributor contributor, Role role) throws Exception {
        if (compilation == null || contributor == null || role == null) {
            throw new Exception("One or many given objects are null");
        }
        this.compilationUploader.removeContributorToCompilation(compilation.getId(), contributor.getId(), role.getId());
    }

    public void addContributor(Contributor contributor) throws Exception {
        if (contributor == null) {
            throw new Exception("Contributor is null");
        }
        this.contributorUploader.addContributor(contributor.getName());
    }

    public void deleteContributor(Contributor contributor) throws Exception {
        if (contributor == null) {
            throw new Exception("Contributor is null");
        }
        this.contributorUploader.deleteContributor(contributor.getName());
    }

    public void updateContributor(Contributor oldContributor, Contributor newContributor) throws Exception {
        if (oldContributor == null || newContributor == null) {
            throw new Exception("One or many given objects are null");
        }
        this.contributorUploader.updateContributor(oldContributor.getName(), newContributor.getName());
    }

    public void addDistribution(Distribution distribution) throws Exception {
        if (distribution == null) {
            throw new Exception("Distribution is null");
        }
        this.distributionUploader.addDistribution(distribution.getCollection().getId(), distribution.getReleaseDate(), distribution.getLabel().getId(), distribution.getMarket().getId());
    }

    public void removeDistribution(Distribution distribution) throws Exception {
        if (distribution == null) {
            throw new Exception("Distribution is null");
        }
        this.distributionUploader.removeDistribution(distribution.getId());
    }

    public void updateDistribution(Distribution oldDistribution, Distribution newDistribution) throws Exception {
        if (oldDistribution == null || newDistribution == null) {
            throw new Exception("One or many given objects are null");
        }
        this.distributionUploader.updateDistribution(oldDistribution.getId(), newDistribution.getCollection().getId(), newDistribution.getReleaseDate(), newDistribution.getLabel().getId(), newDistribution.getMarket().getId());
    }

    public void addLabel(RecordLabel label) throws Exception {
        if (label == null) {
            throw new Exception("Label is null");
        }
        this.labelUploader.addLabel(label.getName());
    }

    public void removeLabel(RecordLabel label) throws Exception {
        if (label == null) {
            throw new Exception("Label is null");
        }
        this.labelUploader.removeLabel(label.getId());
    }

    public void updateLabel(RecordLabel oldLabel, RecordLabel newLabel) throws Exception {
        if (oldLabel == null || newLabel == null) {
            throw new Exception("One or many given objects are null");
        }
        this.labelUploader.updateLabel(oldLabel.getName(), newLabel.getName());
    }

    public void addMarket(Market market) throws Exception {
        if (market == null) {
            throw new Exception("Market is null");
        }
        this.marketUploader.addMarket(market.getName());
    }

    public void removeMarket(Market market) throws Exception {
        if (market == null) {
            throw new Exception("Market is null");
        }
        this.marketUploader.removeMarket(market.getId());
    }

    public void updateMarket(Market oldMarket, Market newMarket) throws Exception {
        if (oldMarket == null || newMarket == null) {
            throw new Exception("One or many given objects are null");
        }
        this.marketUploader.updateMarket(oldMarket.getName(), newMarket.getName());
    }

    public void addRecording(Recording recording) throws Exception {
        if (recording == null) {
            throw new Exception("Recording is null");
        }
        this.recordingUploader.addRecording(recording);
    }

    public void removeRecording(int id) throws Exception {
        this.recordingUploader.removeRecording(id);
    }

    public void updateRecording(Recording oldRecording, Recording newRecording) throws Exception {
        if (oldRecording == null || newRecording == null) {
            throw new Exception("One or many given objects are null");
        }
        this.recordingUploader.updateRecording(oldRecording.getId(), newRecording.getName(), newRecording.getDuration());
    }

    public void addRole(Role role) throws Exception {
        if (role == null) {
            throw new Exception("Role is null");
        }
        if (role instanceof CompilationRole) {
            this.roleUploader.addRole('c', role.getName());
        } else if (role instanceof MusicianRole) {
            this.roleUploader.addRole('m', role.getName());
        } else if (role instanceof ProductionRole) {
            this.roleUploader.addRole('p', role.getName());
        } else {
            throw new Exception("Type of object role given is not specified");
        }
    }

    public void deleteRole(Role role) throws Exception {
        if (role == null) {
            throw new Exception("Role is null");
        }
        if (role instanceof CompilationRole) {
            this.roleUploader.deleteRole('c', role.getName());
        } else if (role instanceof MusicianRole) {
            this.roleUploader.deleteRole('m', role.getName());
        } else if (role instanceof ProductionRole) {
            this.roleUploader.deleteRole('p', role.getName());
        } else {
            throw new Exception("Type of object role given is not specified");
        }
    }

    public void updateRole(Role oldRole, Role newRole) throws Exception {
        if (oldRole == null || newRole == null) {
            throw new Exception("One or many given objects are null");
        }
        if (oldRole instanceof CompilationRole) {
            this.roleUploader.updateRole('c', oldRole.getName(), newRole.getName());
        } else if (oldRole instanceof MusicianRole) {
            this.roleUploader.updateRole('m', oldRole.getName(), newRole.getName());
        } else if (oldRole instanceof ProductionRole) {
            this.roleUploader.updateRole('p', oldRole.getName(), newRole.getName());
        } else {
            throw new Exception("Type of object oldRole given is not specified");
        }
    }

    public void addContributorToRecording(Recording recording, Contributor contributor, Role role) throws Exception{
        if(recording == null){
            throw new Exception("One or many given objects are null");
        }
        this.recordingUploader.addContributorToRecording(recording, contributor, role);
    }

    public void removeContributorToRecording(Recording recording, Contributor contributor, Role role) throws Exception{
        if(recording == null || contributor == null || role == null){
            throw new Exception("One or many given objects are null");
        }
        this.recordingUploader.removeContributorToRecording(recording, contributor, role);
    }
}