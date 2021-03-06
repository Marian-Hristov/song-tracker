package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders.ObjectDownloader;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.roles.*;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

class RecordingUploader implements IDBUploader<Recording> {
    private final Connection connection;
    private Downloader dl;

    public RecordingUploader(Connection connection) throws SQLException {
        this.connection = connection;
        this.dl = Downloader.getInstance();
    }


    private void addRecording(Recording recording) throws Exception {
        if (recording.getName() == null || recording.getName().equals("") || recording.getDuration() < 0 || recording.getId() < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addRecording = this.connection.prepareCall("{call RECORDING_MGMT.ADDRECORDING(?, ?)}");
        try {
            addRecording.setString(1, recording.getName());
            addRecording.setDouble(2, recording.getDuration());
            if (addRecording.executeUpdate() != 1) {
                throw new SQLException("Couldn't add recording");
            }
            this.connection.commit();
            addRecording.close();
        } catch (Exception e) {
            this.connection.rollback();
            addRecording.close();
            throw e;
        }
    }

    private void removeRecording(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeRecording = this.connection.prepareCall("{call RECORDING_MGMT.REMOVERECORDING(?)}");
        try {
            removeRecording.setInt(1, id);
            if (removeRecording.executeUpdate() != 1) {
                throw new SQLException("Couldn't delete recording");
            }
            this.connection.commit();
            removeRecording.close();
        } catch (Exception e) {
            this.connection.rollback();
            removeRecording.close();
            throw e;
        }
    }

    private void updateRecording(int id, String newName, double newDuration) throws Exception {
        if (newName == null || newName.equals("") || newDuration < 0 || id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement updateRecording = this.connection.prepareCall("{call RECORDING_MGMT.UPDATERECORDING(?, ?, ?)}");

        try {
            updateRecording.setInt(1, id);
            updateRecording.setString(2, newName);
            updateRecording.setDouble(3, newDuration);
            if (updateRecording.executeUpdate() != 1) {
                throw new SQLException("Couldn't update recording");
            }
            this.connection.commit();
            updateRecording.close();
        } catch (Exception e) {
            this.connection.rollback();
            updateRecording.close();
            throw e;
        }
    }

    private void addContributorToRecording(Recording recording, Contributor contributor, Role role) throws Exception{
        if (recording.getName() == null || recording.getName().equals("") || recording.getDuration() < 0 || recording.getId() < 1 || contributor.getId() < 1 || contributor.getName() == null || contributor.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        if (role instanceof MusicianRole) {
            CallableStatement addMusicianToRecording = this.connection.prepareCall("{call RECORDING_MGMT.ADDCONTRIBUTORFROMRECORDING(?, ?, ?, ?)}");
            try {
                addMusicianToRecording.setInt(1, recording.getId());
                addMusicianToRecording.setInt(2, contributor.getId());
                addMusicianToRecording.setInt(3, role.getId());
                addMusicianToRecording.setString(4, "m");
                if(addMusicianToRecording.executeUpdate() != 1){
                    throw new SQLException("Couldn't not add contributor to recording");
                }
                this.connection.commit();
                addMusicianToRecording.close();
            } catch (Exception e){
                this.connection.rollback();
                addMusicianToRecording.close();
                throw e;
            }
        } else if (role instanceof ProductionRole) {
            CallableStatement addProductionToRecording = this.connection.prepareCall("{call RECORDING_MGMT.ADDCONTRIBUTORFROMRECORDING(?, ?, ?, ?)}");
            try {
                addProductionToRecording.setInt(1, recording.getId());
                addProductionToRecording.setInt(2, contributor.getId());
                addProductionToRecording.setInt(3, role.getId());
                addProductionToRecording.setString(4, "p");
                if(addProductionToRecording.executeUpdate() != 1){
                    throw new SQLException("Couldn't not add contributor to recording");
                }
                this.connection.commit();
                addProductionToRecording.close();
            } catch (Exception e){
                this.connection.rollback();
                addProductionToRecording.close();
                throw e;
            }
        } else {
            throw new Exception("Type of object role given is not specified");
        }
    }

    private void removeContributorFromRecording(Recording recording, Contributor contributor, Role role) throws Exception{
        if (recording.getName() == null || recording.getName().equals("") || recording.getDuration() < 0 || recording.getId() < 1 || contributor.getId() < 1 || contributor.getName() == null || contributor.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        if (role instanceof MusicianRole) {
            CallableStatement removeMusicianToRecording = this.connection.prepareCall("{call RECORDING_MGMT.REMOVECONTRIBUTORFROMRECORDING(?, ?, ?, ?)}");
            try {
                removeMusicianToRecording.setInt(1, recording.getId());
                removeMusicianToRecording.setInt(2, contributor.getId());
                removeMusicianToRecording.setInt(3, role.getId());
                removeMusicianToRecording.setString(4, "m");
                if(removeMusicianToRecording.executeUpdate() != 1){
                    throw new SQLException("Couldn't not add contributor to recording");
                }
                this.connection.commit();
                removeMusicianToRecording.close();
            } catch (Exception e){
                this.connection.rollback();
                removeMusicianToRecording.close();
                throw e;
            }
        } else if (role instanceof ProductionRole) {
            CallableStatement removeProductionToRecording = this.connection.prepareCall("{call RECORDING_MGMT.REMOVECONTRIBUTORFROMRECORDING(?, ?, ?, ?)}");
            try {
                removeProductionToRecording.setInt(1, recording.getId());
                removeProductionToRecording.setInt(2, contributor.getId());
                removeProductionToRecording.setInt(3, role.getId());
                removeProductionToRecording.setString(4, "p");
                if(removeProductionToRecording.executeUpdate() != 1){
                    throw new SQLException("Couldn't not add contributor to recording");
                }
                this.connection.commit();
                removeProductionToRecording.close();
            } catch (Exception e){
                this.connection.rollback();
                removeProductionToRecording.close();
                throw e;
            }
        } else {
            throw new Exception("Type of object role given is not specified");
        }
    }

    private void removeAllContributions(Recording recording) throws Exception{
        if(recording.getMusicalContributions().size() != 0){
            Map<MusicianRole, ArrayList<Contributor>> map = recording.getMusicalContributions();
            for (Map.Entry<MusicianRole, ArrayList<Contributor>> entry : map.entrySet()){
                for(Contributor contributor : entry.getValue()){
                    this.removeContributorFromRecording(recording, contributor, entry.getKey());
                }
            }
        }
        if(recording.getProductionContributions().size() != 0){
            Map<ProductionRole, ArrayList<Contributor>> map = recording.getProductionContributions();
            for (Map.Entry<ProductionRole, ArrayList<Contributor>> entry : map.entrySet()){
                for(Contributor contributor : entry.getValue()){
                    this.removeContributorFromRecording(recording, contributor, entry.getKey());
                }
            }
        }
    }

    private void addAllContributions(Recording recording) throws Exception {
        if(recording.getMusicalContributions().size() != 0){
            Map<MusicianRole, ArrayList<Contributor>> map = recording.getMusicalContributions();
            for (Map.Entry<MusicianRole, ArrayList<Contributor>> entry : map.entrySet()){
                for(Contributor contributor : entry.getValue()){
                    this.addContributorToRecording(recording, contributor, entry.getKey());
                }
            }
        }
        if(recording.getProductionContributions().size() != 0){
            Map<ProductionRole, ArrayList<Contributor>> map = recording.getProductionContributions();
            for (Map.Entry<ProductionRole, ArrayList<Contributor>> entry : map.entrySet()){
                for(Contributor contributor : entry.getValue()){
                    this.addContributorToRecording(recording, contributor, entry.getKey());
                }
            }
        }
    }

    @Override
    public void add(Recording recording) throws Exception {
        if(recording == null){
            throw new Exception("Recording is null");
        }
        this.addRecording(recording);
        this.addAllContributions(recording);
    }

    @Override
    public void remove(Recording recording) throws Exception {
        if(recording == null){
            throw new Exception("Recording is null");
        }
        this.removeRecording(recording.getId());
    }

    @Override
    public void update(Recording newRecording) throws Exception {
        if (newRecording == null) {
            throw new Exception("Recording is null");
        }
        ObjectDownloader<Recording> dl = (ObjectDownloader<Recording>) Downloader.getInstance().getLoader(Recording.class);

        Recording oldRecording = dl.load(newRecording.getId());
        this.removeAllContributions(oldRecording);
        this.updateRecording(oldRecording.getId(), newRecording.getName(), newRecording.getDuration());
        this.addAllContributions(newRecording);
    }
}