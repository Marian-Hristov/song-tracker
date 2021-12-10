package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.DBConnection;
import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.distributions.*;
import dawson.songtracker.back.types.roles.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ObjectUploader {
    private final Connection connection;
    private static ObjectUploader instance = null;

    private final Map<Class<? extends DatabaseObject>, IDBUploader<? extends DatabaseObject>> classMap = new HashMap<>();

    private ObjectUploader(Connection connection) throws Exception {
        if (connection == null) {
            throw new Exception("Connection is null, cannot create uploader");
        } else {
            this.connection = connection;

            this.classMap.put(Collection.class, new CollectionUploader(this.connection));
            this.classMap.put(Compilation.class, new CompilationUploader(this.connection));
            this.classMap.put(Contributor.class, new ContributorUploader(this.connection));
            this.classMap.put(Distribution.class, new DistributionUploader(this.connection));
            this.classMap.put(RecordLabel.class, new LabelUploader(this.connection));
            this.classMap.put(Market.class, new MarketUploader(this.connection));
            this.classMap.put(Recording.class, new RecordingUploader(this.connection));
            this.classMap.put(Role.class, new RoleUploader(this.connection));
        }
    }


    public static ObjectUploader getInstance() throws Exception {
        if (instance == null) {
            instance = new ObjectUploader(DBConnection.getConnection());
        }
        return instance;
    }


    public <T extends DatabaseObject> IDBUploader<? extends DatabaseObject> getUploader(Class<? extends DatabaseObject> dClass) {
        return classMap.get(dClass);
    }



//    public void addCompilationToCollection(Collection collection, Compilation compilation) throws Exception {
//        if (collection == null || compilation == null) {
//            throw new Exception("One or many given objects are null");
//        }
//        this.collectionUploader.addCompilationToCollection(collection.getId(), compilation.getId());
//    }
//
//    public void removeCompilationFromCollection(Collection collection, Compilation compilation) throws Exception {
//        if (collection == null || compilation == null) {
//            throw new Exception("One or many given objects are null");
//        }
//        this.collectionUploader.removeCompilationFromCollection(collection.getId(), compilation.getId());
//    }
//
//
//    public void addSampleToCompilation(Segment<?> segment) throws Exception {
//        if (segment == null) {
//            throw new Exception("Segment is null");
//        }
//        if (segment.getComponentTrack() instanceof Compilation) {
//            this.compilationUploader.addSampleToCompilation(segment.getMainTrackId(), segment.getMainTrackOffset(), segment.getDurationInMainTrack(), segment.getComponentTrackOffset(), segment.getDurationOfComponentUsed(), segment.getComponentTrack().getId(), 'c');
//        } else if (segment.getComponentTrack() instanceof Recording) {
//            this.compilationUploader.addSampleToCompilation(segment.getMainTrackId(), segment.getMainTrackOffset(), segment.getDurationInMainTrack(), segment.getComponentTrackOffset(), segment.getDurationOfComponentUsed(), segment.getComponentTrack().getId(), 'r');
//        } else {
//            throw new Exception("Segment has an invalid component track");
//        }
//    }
//
//    public void deleteSampleFromCompilation(Segment<?> segment) throws Exception {
//        if (segment == null) {
//            throw new Exception("Segment is null");
//        }
//        if (segment.getComponentTrack() instanceof Compilation) {
//            this.compilationUploader.deleteSampleFromCompilation(segment.getMainTrackId(), segment.getComponentTrack().getId(), segment.getId(), 'c');
//        } else if (segment.getComponentTrack() instanceof Recording) {
//            this.compilationUploader.deleteSampleFromCompilation(segment.getMainTrackId(), segment.getComponentTrack().getId(), segment.getId(), 'r');
//        } else {
//            throw new Exception("Segment has an invalid component track");
//        }
//    }
//
//    public void addContributorToCompilation(Compilation compilation, Contributor contributor, Role role) throws Exception {
//        if (compilation == null || contributor == null || role == null) {
//            throw new Exception("One or many given objects are null");
//        }
//        this.compilationUploader.addContributorToCompilation(compilation.getId(), contributor.getId(), role.getId());
//    }
//
//    public void removeContributorToCompilation(Compilation compilation, Contributor contributor, Role role) throws Exception {
//        if (compilation == null || contributor == null || role == null) {
//            throw new Exception("One or many given objects are null");
//        }
//        this.compilationUploader.removeContributorToCompilation(compilation.getId(), contributor.getId(), role.getId());
//    }
//
//    public void addRole(Role role) throws Exception {
//        if (role == null) {
//            throw new Exception("Role is null");
//        }
//        if (role instanceof CompilationRole) {
//            this.roleUploader.addRole('c', role.getName());
//        } else if (role instanceof MusicianRole) {
//            this.roleUploader.addRole('m', role.getName());
//        } else if (role instanceof ProductionRole) {
//            this.roleUploader.addRole('p', role.getName());
//        } else {
//            throw new Exception("Type of object role given is not specified");
//        }
//    }
//
//    public void deleteRole(Role role) throws Exception {
//        if (role == null) {
//            throw new Exception("Role is null");
//        }
//        if (role instanceof CompilationRole) {
//            this.roleUploader.deleteRole('c', role.getName());
//        } else if (role instanceof MusicianRole) {
//            this.roleUploader.deleteRole('m', role.getName());
//        } else if (role instanceof ProductionRole) {
//            this.roleUploader.deleteRole('p', role.getName());
//        } else {
//            throw new Exception("Type of object role given is not specified");
//        }
//    }
//
//    public void updateRole(Role oldRole, Role newRole) throws Exception {
//        if (oldRole == null || newRole == null) {
//            throw new Exception("One or many given objects are null");
//        }
//        if (oldRole instanceof CompilationRole) {
//            this.roleUploader.updateRole('c', oldRole.getName(), newRole.getName());
//        } else if (oldRole instanceof MusicianRole) {
//            this.roleUploader.updateRole('m', oldRole.getName(), newRole.getName());
//        } else if (oldRole instanceof ProductionRole) {
//            this.roleUploader.updateRole('p', oldRole.getName(), newRole.getName());
//        } else {
//            throw new Exception("Type of object oldRole given is not specified");
//        }
//    }
//
//    public void addContributorToRecording(Recording recording, Contributor contributor, Role role) throws Exception{
//        if(recording == null){
//            throw new Exception("One or many given objects are null");
//        }
//        this.recordingUploader.addContributorToRecording(recording, contributor, role);
//    }
//
//    public void removeContributorToRecording(Recording recording, Contributor contributor, Role role) throws Exception{
//        if(recording == null || contributor == null || role == null){
//            throw new Exception("One or many given objects are null");
//        }
//        this.recordingUploader.removeContributorToRecording(recording, contributor, role);
//    }
}