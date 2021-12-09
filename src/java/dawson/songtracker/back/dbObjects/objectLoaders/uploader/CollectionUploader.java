package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.types.distributions.Collection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class CollectionUploader implements IDBUploader<Collection> {
    private final Connection connection;

    public CollectionUploader(Connection connection) {
        this.connection = connection;
    }

    public void addCollection(String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addCollection = this.connection.prepareCall("{call COLLECTION_MGMT.CREATECOLLECTION(?)}");
        try {
            addCollection.setString(1, name);
            if (addCollection.executeUpdate() != 1) {
                throw new SQLException("Couldn't add collection");
            }
            this.connection.commit();
            addCollection.close();
        } catch (Exception e) {
            this.connection.rollback();
            addCollection.close();
            throw e;
        }
    }

    public void removeCollection(int id) throws Exception {
        if(id < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeCollection = this.connection.prepareCall("{call COLLECTION_MGMT.REMOVECOLLECTION(?)}");
        try {
            removeCollection.setInt(1, id);
            if(removeCollection.executeUpdate() != 1){
                throw new SQLException("Couldn't remove collection");
            }
            this.connection.commit();
            removeCollection.close();
        } catch (Exception e){
            this.connection.rollback();
            removeCollection.close();
            throw e;
        }
    }

    public void addCompilationToCollection(int collectionId, int compilationId) throws Exception {
        if (collectionId < 1 || compilationId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addCompilationToCollection = this.connection.prepareCall("{call COLLECTION_MGMT.ADDCOMPILATIONTOCOLLECTION(?, ?)}");
        try {
            addCompilationToCollection.setInt(1, collectionId);
            addCompilationToCollection.setInt(2, compilationId);
            if (addCompilationToCollection.executeUpdate() != 1) {
                throw new SQLException("Couldn't add compilation to collection");
            }
            this.connection.commit();
            addCompilationToCollection.close();
        } catch (Exception e) {
            this.connection.rollback();
            addCompilationToCollection.close();
            throw e;
        }
    }

    public void removeCompilationFromCollection(int collectionId, int compilationId) throws Exception {
        if (collectionId < 1 || compilationId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeCompilationToCollection = this.connection.prepareCall("{call COLLECTION_MGMT.REMOVECOMPILATIONFROMCOLLECTION(?, ?)}");
        try {
            removeCompilationToCollection.setInt(1, collectionId);
            removeCompilationToCollection.setInt(2, compilationId);
            if (removeCompilationToCollection.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove collection");
            }
            this.connection.commit();
            removeCompilationToCollection.close();
        } catch (Exception e) {
            this.connection.rollback();
            removeCompilationToCollection.close();
            throw e;
        }
    }

    public void updateCollection(int collectionId, String newName) throws Exception {
        if (collectionId < 1 || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement updateCollection = this.connection.prepareCall("{call COLLECTION_MGMT.UPDATECOLLECTION(?, ?)}");
        try {
            updateCollection.setInt(1, collectionId);
            updateCollection.setString(2, newName);
            if (updateCollection.executeUpdate() != 1) {
                throw new SQLException("Couldn't update collection");
            }
            this.connection.commit();
            updateCollection.close();
        } catch (Exception e) {
            this.connection.rollback();
            updateCollection.close();
            throw e;
        }
    }

    public void addCollectionToSet(int collectionId, int setId) throws Exception {
        if(collectionId < 1 || setId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addCollectionToSet = this.connection.prepareCall("{call COLLECTION_MGMT.ADDCOLLECTIONTOSET(?, ?)}");
        try {
            addCollectionToSet.setInt(1, collectionId);
            addCollectionToSet.setInt(2, setId);
            if(addCollectionToSet.executeUpdate() != 1){
                throw new SQLException("Couldn't add collection to set");
            }
            this.connection.commit();
            addCollectionToSet.close();
        } catch (Exception e){
            this.connection.rollback();
            addCollectionToSet.close();
            throw e;
        }
    }

    public void removeCollectionFromSet(int collectionId, int setId) throws Exception {
        if(collectionId < 1 || setId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeCollectionFromSet = this.connection.prepareCall("{call COLLECTION_MGMT.REMOVECOLLECTIONFROMSET(?, ?)}");
        try {
            removeCollectionFromSet.setInt(1, collectionId);
            removeCollectionFromSet.setInt(2, setId);
            if(removeCollectionFromSet.executeUpdate() != 1){
                throw new SQLException("Couldn't remove collection from set");
            }
            this.connection.commit();
            removeCollectionFromSet.close();
        } catch (Exception e){
            this.connection.rollback();
            removeCollectionFromSet.close();
            throw e;
        }
    }

    @Override
    public void add(Collection collection) throws Exception {
        if(collection == null){
            throw new Exception("Collection is null");
        }

    }

    @Override
    public void remove(Collection collection) throws Exception {
        if(collection == null){
            throw new Exception("Collection is null");
        }
    }

    @Override
    public void update(Collection collection) throws Exception {
        if(collection == null){
            throw new Exception("Collection is null");
        }
    }
}
