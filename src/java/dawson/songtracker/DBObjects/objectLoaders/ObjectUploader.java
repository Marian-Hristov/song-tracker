package dawson.songtracker.DBObjects.objectLoaders;

import java.sql.*;

public class ObjectUploader{
    private final Connection connection;

    public ObjectUploader(Connection connection) throws SQLException {
        this.connection = connection;
    }

    // Production roles
    public void addRole(char category, String name) throws Exception{
        if (name == null || name.equals("")){
            throw new IllegalArgumentException("One or many given arguments are null or empty");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }

        try{
            PreparedStatement insertRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.ADDROLE(?, ?)");
            insertRole.setString(1, Character.toString(category));
            insertRole.setString(2, name);

            if(insertRole.executeUpdate() != 1){
                throw new SQLException("Couldn't create role");
            }

            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't create role");
        }
    }

    public void deleteRole(char category, String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given id is invalid or null");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }
        try{
            PreparedStatement deleteRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.REMOVEROLE(?, ?)");
            deleteRole.setString(1, Character.toString(category));
            deleteRole.setString(2, name);
            if(deleteRole.executeUpdate() != 1){
                throw new SQLException("Could not remove role");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't delete role");
        }
    }

    public void updateRole(char category, String oldName, String newName) throws Exception{
        if(oldName == null || newName == null || oldName.equals("") || newName.equals("")){
            throw new IllegalArgumentException("One or more given names are invalid or null");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }
        try{
            PreparedStatement updateRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.UPDATEROLE(?, ?, ?)");
            updateRole.setString(1, Character.toString(category));
            updateRole.setString(2, oldName);
            updateRole.setString(3, newName);
            if(updateRole.executeUpdate() != 1){
                throw new SQLException("Could not update role");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update role");
        }
    }

    public void addContributor(String name) throws Exception{
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given name is empty or null");
        }
        try{
            PreparedStatement insertContributor = this.connection.prepareStatement("EXECUTE CONTRIBUTOR_MGMT.ADDCONTRIBUTOR(?)");
            insertContributor.setString(1, name);
            if(insertContributor.executeUpdate() != 1){
                throw new SQLException("Could not add contributor");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw new SQLException("Could not add contributor");
        }
    }

    public void deleteContributor(String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given name is empty or null");
        }
        try{
            PreparedStatement deleteContributor = this.connection.prepareStatement("EXECUTE CONTRIBUTOR_MGMT.REMOVECONTRIBUTOR(?)");
            deleteContributor.setString(1, name);
            if(deleteContributor.executeUpdate() != 1){
                throw new SQLException("Could not delete contributor");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw new Exception("Could not delete contributor");
        }
    }

    public void updateContributor(String oldName, String newName) throws Exception{
        if(oldName == null || oldName.equals("") || newName == null || newName.equals("")){
            throw new IllegalArgumentException("One or more given names are empty or null");
        }
        try {
            PreparedStatement updateContributor = this.connection.prepareStatement("EXECUTE CONTRIBUTOR_MGMT.UPDATECONTRIBUTOR(?, ?)");
            updateContributor.setString(1, oldName);
            updateContributor.setString(2, newName);
            if(updateContributor.executeUpdate() != 1){
                throw  new SQLException("Could not update contributor");
            }
            connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Could not update contributor");
        }
    }

    public void addRecording(String name, double duration) throws Exception{
        if(name == null || name.equals("") || duration < 0){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addRecording  = this.connection.prepareStatement("EXECUTE RECORDING_MGMT.ADDRECORDING(?, ?)");
            addRecording.setString(1, name);
            addRecording.setDouble(2, duration);
            if(addRecording.executeUpdate() != 1){
                throw new SQLException("Couldn't add recording");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add recording");
        }
    }

    public void removesRecording(int id) throws Exception{
        if(id < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addRecording  = this.connection.prepareStatement("EXECUTE RECORDING_MGMT.REMOVERECORDING(?, ?)");
            addRecording.setInt(1, id);
            if(addRecording.executeUpdate() != 1){
                throw new SQLException("Couldn't delete recording");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't delete recording");
        }
    }

    public void updateRecording(int id, String newName, double newDuration) throws Exception{
        if(newName == null || newName.equals("") || newDuration < 0 || id < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addRecording  = this.connection.prepareStatement("EXECUTE RECORDING_MGMT.UPDATERECORDING(?, ?, ?)");
            addRecording.setInt(1, id);
            addRecording.setString(2, newName);
            addRecording.setDouble(3, newDuration);
            if(addRecording.executeUpdate() != 1){
                throw new SQLException("Couldn't update recording");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update recording");
        }
    }

    public void addMarket(String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addMarket = this.connection.prepareStatement("EXECUTE MARKET_MGMT.ADDMARKET(?)");
            addMarket.setString(1, name);
            if(addMarket.executeUpdate() != 1){
                throw new SQLException("Couldn't add market");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add market");
        }
    }

    public void removeMarket(int id) throws Exception {
        if(id < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeMarket = this.connection.prepareStatement("EXECUTE MARKET_MGMT.REMOVEMARKET(?)");
            removeMarket.setInt(1, id);
            if(removeMarket.executeUpdate() != 1){
                throw new SQLException("Couldn't remove market");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't remove market");
        }
    }

    public void updateMarket(String oldName, String newName) throws Exception {
        if(oldName == null || oldName.equals("") || newName == null || newName.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement updateMarket = this.connection.prepareStatement("EXECUTE MARKET_MGMT.UPDATEMARKET(?, ?)");
            updateMarket.setString(1, oldName);
            updateMarket.setString(2, newName);
            if(updateMarket.executeUpdate() != 1){
                throw new SQLException("Couldn't update market");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update market");
        }
    }

    public void addLabel(String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addLabel = this.connection.prepareStatement("EXECUTE LABEL_MGMT.ADDLABEL(?)");
            addLabel.setString(1, name);
            if(addLabel.executeUpdate() != 1){
                throw new SQLException("Couldn't add label");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add label");
        }
    }

    public void removeLabel(int id) throws Exception {
        if(id < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeLabel = this.connection.prepareStatement("EXECUTE LABEL_MGMT.REMOVELABEL(?)");
            removeLabel.setInt(1, id);
            if(removeLabel.executeUpdate() != 1){
                throw new SQLException("Couldn't remove label");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't remove label");
        }
    }

    public void updateLabel(String oldName, String newName) throws Exception {
        if(oldName == null || oldName.equals("") || newName == null || newName.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement updateLabel = this.connection.prepareStatement("EXECUTE LABEL_MGMT.UPDATELABEL(?, ?)");
            updateLabel.setString(1, oldName);
            updateLabel.setString(2, newName);
            if(updateLabel.executeUpdate() != 1){
                throw new SQLException("Couldn't update label");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update label");
        }
    }

    public void addDistribution(int collectionId, Date releaseDate, int labelId, int marketId) throws Exception {
        if(collectionId < 1 || labelId < 1 || marketId < 1 || releaseDate == null){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addDistribution = this.connection.prepareStatement("EXECUTE DISTRIBUTION_MGMT.ADDDISTRIBUTION(?, ?, ?, ?)");
            addDistribution.setInt(1, collectionId);
            addDistribution.setDate( 2, releaseDate);
            addDistribution.setInt(3, labelId);
            addDistribution.setInt(4, marketId);
            if(addDistribution.executeUpdate() != 1){
                throw new SQLException("Couldn't add distribution");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add distribution");
        }
    }

    public void removeDistribution(int id) throws Exception {
        if(id < 1 ){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeDistribution = this.connection.prepareStatement("EXECUTE DISTRIBUTION_MGMT.REMOVEDISTRIBUTION(?)");
            removeDistribution.setInt(1, id);
            if(removeDistribution.executeUpdate() != 1){
                throw new SQLException("Couldn't remove distribution");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't remove distribution");
        }
    }

    public void updateDistribution(int distributionId, int collectionId, Date releaseDate, int labelId, int marketId) throws Exception {
        if(distributionId < 1 || collectionId < 1 || labelId < 1 || marketId < 1 || releaseDate == null){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement updateDistribution = this.connection.prepareStatement("EXECUTE DISTRIBUTION_MGMT.UPDATEDISTRIBUTION(?, ?, ?, ?, ?)");
            updateDistribution.setInt(1, distributionId);
            updateDistribution.setInt(2, collectionId);
            updateDistribution.setDate(3, releaseDate);
            updateDistribution.setInt(4, labelId);
            updateDistribution.setInt(5, marketId);
            if(updateDistribution.executeUpdate() != 1){
                throw new SQLException("Couldn't update distribution");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update distribution");
        }
    }

    public void addCompilation(String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            // TODO Check if this will work because the procedure takes in 2 args
            PreparedStatement addCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.ADDCOMPILATION(?)");
            addCompilation.setString(1, name);
            if(addCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't add compilation");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add compilation");
        }
    }

    public void addSampleToCompilation(String compilationName, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponent, int sampleId, char sampleType) throws Exception{
        if(compilationName == null || compilationName.equals("") || mainTrackOffset < 0 || durationInMainTrack < 0 || componentTrackOffset < 0 ||durationOfComponent < 0 || sampleId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
         } else if (sampleType != 'c' || sampleType != 'r'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }
        try {
            PreparedStatement addSampleToCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.ADDSAMPLETOCOMPILATION(?, ?, ?, ?, ?, ?, ?)");
            addSampleToCompilation.setString(1, compilationName);
            addSampleToCompilation.setDouble(2, mainTrackOffset);
            addSampleToCompilation.setDouble(3, durationInMainTrack);
            addSampleToCompilation.setDouble(4, componentTrackOffset);
            addSampleToCompilation.setDouble(5, durationOfComponent);
            addSampleToCompilation.setInt(6, sampleId);
            addSampleToCompilation.setString(7, Character.toString(sampleType));
            if(addSampleToCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't add sample to compilation");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw new Exception("Couldn't add sample to compilation");
        }
    }

    public void deleteSampleFromCompilation(int compilationId, int sampleId, int segmentId, char sampleType) throws Exception{
        if(compilationId < 1 || sampleId < 1 || segmentId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        } else if (sampleType != 'c' || sampleType != 'r'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }
        try {
            PreparedStatement deleteSampleFromCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.DELETESAMPLEFROMCOMPILATION(?, ?, ?, ?)");
            deleteSampleFromCompilation.setInt(1, compilationId);
            deleteSampleFromCompilation.setInt(2, sampleId);
            deleteSampleFromCompilation.setInt(3, segmentId);
            deleteSampleFromCompilation.setString(4, Character.toString(sampleType));
            if(deleteSampleFromCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't delete sample from compilation");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't delete sample from compilation");
        }
    }

    public void deleteCompilation(int id) throws Exception{
        if(id < 0){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try{
            PreparedStatement deleteCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.DELETECOMPILATION(?)");
            deleteCompilation.setInt(1, id);
            if(deleteCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't delete compilation");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't delete compilation");
        }
    }

    public void updateCompilation(int id, String name) throws Exception {
        if(id < 0 || name == null || name.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement updateCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.UPDATECOMPILATION(?, ?)");
            updateCompilation.setInt(1, id);
            updateCompilation.setString(2, name);
            if(updateCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't update compilation");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update compilation");
        }
    }

    public void addContributorToCompilation(int compilationId, int contributorId, int roleId) throws Exception{
        if(compilationId < 1 || contributorId < 1 || roleId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addContributorToCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.ADDCONTRIBUTORTOCOMPILATION(?, ?, ?)");
            addContributorToCompilation.setInt(1, compilationId);
            addContributorToCompilation.setInt(2, contributorId);
            addContributorToCompilation.setInt(3, roleId);
            if(addContributorToCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't add contributor to compilation");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add contributor to compilation");
        }
    }

    public void removeContributorToCompilation(int compilationId, int contributorId, int roleId) throws Exception{
        if(compilationId < 1 || contributorId < 1 || roleId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeContributorToCompilation = this.connection.prepareStatement("EXECUTE COMPILATION_MGMT.REMOVECONTRIBUTORTOCOMPILATION(?, ?, ?)");
            removeContributorToCompilation.setInt(1, compilationId);
            removeContributorToCompilation.setInt(2, contributorId);
            removeContributorToCompilation.setInt(3, roleId);
            if(removeContributorToCompilation.executeUpdate() != 1){
                throw new SQLException("Couldn't remove contributor to compilation");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't remove contributor to compilation");
        }
    }

    public void addCollection(String name) throws Exception{
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addCollection = this.connection.prepareStatement("EXECUTE COLLECTION_MGMT.CREATECOLLECTION(?)");
            addCollection.setString(1, name);
            if(addCollection.executeUpdate() != 1){
                throw new SQLException("Couldn't add collection");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add collection");
        }
    }

    public void addCompilationToCollection(int collectionId, int compilationId) throws Exception{
        if(collectionId < 1 || compilationId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement addCompilationToCollection = this.connection.prepareStatement("EXECUTE COLLECTION_MGMT.ADDCOMPILATIONTOCOLLECTION(?, ?)");
            addCompilationToCollection.setInt(1, collectionId);
            addCompilationToCollection.setInt(2, compilationId);
            if(addCompilationToCollection.executeUpdate() != 1){
                throw new SQLException("Couldn't add compilation to collection");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't add compilation to collection");
        }
    }

    public void removeCompilationToCollection(int collectionId, int compilationId) throws Exception{
        if(collectionId < 1 || compilationId < 1){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeCompilationToCollection = this.connection.prepareStatement("EXECUTE COLLECTION_MGMT.REMOVECOMPILATIONTOCOLLECTION(?, ?)");
            removeCompilationToCollection.setInt(1, collectionId);
            removeCompilationToCollection.setInt(2, compilationId);
            if(removeCompilationToCollection.executeUpdate() != 1){
                throw new SQLException("Couldn't remove collection");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't remove collection");
        }
    }

    public void updateCollection(int collectionId, String newName) throws Exception{
        if(collectionId < 1 || newName == null || newName.equals("")){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            PreparedStatement removeCompilationToCollection = this.connection.prepareStatement("EXECUTE COLLECTION_MGMT.UPDATECOLLECTION(?, ?)");
            removeCompilationToCollection.setInt(1, collectionId);
            removeCompilationToCollection.setString(2, newName);
            if(removeCompilationToCollection.executeUpdate() != 1){
                throw new SQLException("Couldn't update collection");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update collection");
        }
    }
}