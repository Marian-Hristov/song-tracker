package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class ContributorUploader {
    private final Connection connection;

    public ContributorUploader(Connection connection) {
        this.connection = connection;
    }

    public void addContributor(String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Given name is empty or null");
        }
        try {
            CallableStatement insertContributor = this.connection.prepareCall("{call CONTRIBUTOR_MGMT.ADDCONTRIBUTOR(?)}");
            insertContributor.setString(1, name);
            if (insertContributor.executeUpdate() != 1) {
                throw new SQLException("Could not add contributor");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void deleteContributor(String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Given name is empty or null");
        }
        try {
            CallableStatement deleteContributor = this.connection.prepareCall("{call CONTRIBUTOR_MGMT.REMOVECONTRIBUTOR(?)}");
            deleteContributor.setString(1, name);
            if (deleteContributor.executeUpdate() != 1) {
                throw new SQLException("Could not delete contributor");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void updateContributor(String oldName, String newName) throws Exception {
        if (oldName == null || oldName.equals("") || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more given names are empty or null");
        }
        try {
            CallableStatement updateContributor = this.connection.prepareCall("{call CONTRIBUTOR_MGMT.UPDATECONTRIBUTOR(?, ?)}");
            updateContributor.setString(1, oldName);
            updateContributor.setString(2, newName);
            if (updateContributor.executeUpdate() != 1) {
                throw new SQLException("Could not update contributor");
            }
            connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }
}
