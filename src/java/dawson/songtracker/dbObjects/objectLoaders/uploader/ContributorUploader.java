package dawson.songtracker.dbObjects.objectLoaders.uploader;

import java.sql.CallableStatement;
import java.sql.Connection;
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
        CallableStatement insertContributor = this.connection.prepareCall("{call CONTRIBUTOR_MGMT.ADDCONTRIBUTOR(?)}");
        try {
            insertContributor.setString(1, name);
            if (insertContributor.executeUpdate() != 1) {
                throw new SQLException("Could not add contributor");
            }
            this.connection.commit();
            insertContributor.close();
        } catch (Exception e) {
            this.connection.rollback();
            insertContributor.close();
            throw e;
        }
    }

    public void deleteContributor(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("Given name is empty or null");
        }
        CallableStatement deleteContributor = this.connection.prepareCall("{call CONTRIBUTOR_MGMT.REMOVECONTRIBUTOR(?)}");
        try {
            deleteContributor.setInt(1, id);
            if (deleteContributor.executeUpdate() != 1) {
                throw new SQLException("Could not delete contributor");
            }
            this.connection.commit();
            deleteContributor.close();
        } catch (Exception e) {
            this.connection.rollback();
            deleteContributor.close();
            throw e;
        }
    }

    public void updateContributor(String oldName, String newName) throws Exception {
        if (oldName == null || oldName.equals("") || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more given names are empty or null");
        }
        CallableStatement updateContributor = this.connection.prepareCall("{call CONTRIBUTOR_MGMT.UPDATECONTRIBUTOR(?, ?)}");
        try {
            updateContributor.setString(1, oldName);
            updateContributor.setString(2, newName);
            if (updateContributor.executeUpdate() != 1) {
                throw new SQLException("Could not update contributor");
            }
            connection.commit();
            updateContributor.close();
        } catch (Exception e) {
            this.connection.rollback();
            updateContributor.close();
            throw e;
        }
    }
}
