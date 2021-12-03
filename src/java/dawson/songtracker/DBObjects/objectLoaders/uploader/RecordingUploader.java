package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class RecordingUploader {
    private final Connection connection;

    public RecordingUploader(Connection connection) {
        this.connection = connection;
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
            PreparedStatement removeRecording  = this.connection.prepareStatement("EXECUTE RECORDING_MGMT.REMOVERECORDING(?, ?)");
            removeRecording.setInt(1, id);
            if(removeRecording.executeUpdate() != 1){
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
            PreparedStatement updateRecording  = this.connection.prepareStatement("EXECUTE RECORDING_MGMT.UPDATERECORDING(?, ?, ?)");
            updateRecording.setInt(1, id);
            updateRecording.setString(2, newName);
            updateRecording.setDouble(3, newDuration);
            if(updateRecording.executeUpdate() != 1){
                throw new SQLException("Couldn't update recording");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update recording");
        }
    }
}
