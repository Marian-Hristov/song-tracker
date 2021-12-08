package dawson.songtracker.dbObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class LabelUploader {
    private final Connection connection;

    public LabelUploader(Connection connection) {
        this.connection = connection;
    }

    public void addLabel(String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addLabel = this.connection.prepareCall("{call LABEL_MGMT.ADDLABEL(?)}");
        try {
            addLabel.setString(1, name);
            if (addLabel.executeUpdate() != 1) {
                throw new SQLException("Couldn't add label");
            }
            this.connection.commit();
            addLabel.close();
        } catch (Exception e) {
            this.connection.rollback();
            addLabel.close();
            throw e;
        }
    }

    public void removeLabel(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeLabel = this.connection.prepareCall("{call LABEL_MGMT.REMOVELABEL(?)}");

        try {
            removeLabel.setInt(1, id);
            if (removeLabel.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove label");
            }
            this.connection.commit();
            removeLabel.close();
        } catch (Exception e) {
            this.connection.rollback();
            removeLabel.close();
            throw e;
        }
    }

    public void updateLabel(String oldName, String newName) throws Exception {
        if (oldName == null || oldName.equals("") || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement updateLabel = this.connection.prepareCall("{call LABEL_MGMT.UPDATELABEL(?, ?)}");
        try {
            updateLabel.setString(1, oldName);
            updateLabel.setString(2, newName);
            if (updateLabel.executeUpdate() != 1) {
                throw new SQLException("Couldn't update label");
            }
            this.connection.commit();
            updateLabel.close();
        } catch (Exception e) {
            this.connection.rollback();
            updateLabel.close();
            throw e;
        }
    }
}
