package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class ContributorUploader {
    private final Connection connection;

    public ContributorUploader(Connection connection) {
        this.connection = connection;
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
}
