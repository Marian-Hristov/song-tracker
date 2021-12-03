package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class CompilationUploader {
    private final Connection connection;

    public CompilationUploader(Connection connection) {
        this.connection = connection;
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

}