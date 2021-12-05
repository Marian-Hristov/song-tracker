package dawson.songtracker.DBObjects.objectLoaders.uploader;

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
        try {
            CallableStatement addLabel = this.connection.prepareCall("{call LABEL_MGMT.ADDLABEL(?)}");
            addLabel.setString(1, name);
            if (addLabel.executeUpdate() != 1) {
                throw new SQLException("Couldn't add label");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void removeLabel(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement removeLabel = this.connection.prepareCall("{call LABEL_MGMT.REMOVELABEL(?)}");
            removeLabel.setInt(1, id);
            if (removeLabel.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove label");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void updateLabel(String oldName, String newName) throws Exception {
        if (oldName == null || oldName.equals("") || newName == null || newName.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement updateLabel = this.connection.prepareCall("{call LABEL_MGMT.UPDATELABEL(?, ?)}");
            updateLabel.setString(1, oldName);
            updateLabel.setString(2, newName);
            if (updateLabel.executeUpdate() != 1) {
                throw new SQLException("Couldn't update label");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }
}
