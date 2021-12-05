package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class RecordingUploader {
    private final Connection connection;

    public RecordingUploader(Connection connection) {
        this.connection = connection;
    }


    public void addRecording(String name, double duration) throws Exception {
        if (name == null || name.equals("") || duration < 0) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement addRecording = this.connection.prepareCall("{call RECORDING_MGMT.ADDRECORDING(?, ?)}");
            addRecording.setString(1, name);
            addRecording.setDouble(2, duration);
            if (addRecording.executeUpdate() != 1) {
                throw new SQLException("Couldn't add recording");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void removesRecording(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement removeRecording = this.connection.prepareCall("{call RECORDING_MGMT.REMOVERECORDING(?, ?)}");
            removeRecording.setInt(1, id);
            if (removeRecording.executeUpdate() != 1) {
                throw new SQLException("Couldn't delete recording");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void updateRecording(int id, String newName, double newDuration) throws Exception {
        if (newName == null || newName.equals("") || newDuration < 0 || id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement updateRecording = this.connection.prepareCall("{call RECORDING_MGMT.UPDATERECORDING(?, ?, ?)}");
            updateRecording.setInt(1, id);
            updateRecording.setString(2, newName);
            updateRecording.setDouble(3, newDuration);
            if (updateRecording.executeUpdate() != 1) {
                throw new SQLException("Couldn't update recording");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }
}
