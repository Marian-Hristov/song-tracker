package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.types.components.Compilation;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class CompilationUploader implements IDBUploader<Compilation> {
    private final Connection connection;

    public CompilationUploader(Connection connection) {
        this.connection = connection;
    }

    public void addCompilation(String name) throws SQLException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.createCompilation(?)}");
        try {
            addCompilation.setString(1, name);
            if (addCompilation.executeUpdate() != 1) {
                throw new SQLException("Couldn't add compilation");
            }
            this.connection.commit();
            addCompilation.close();
        } catch (Exception e) {
            this.connection.rollback();
            addCompilation.close();
            throw e;
        }
    }

    public void addSampleToCompilation(int compilationId, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponent, int sampleId, char sampleType) throws Exception {
        if (compilationId < 1 || mainTrackOffset < 0 || durationInMainTrack < 0 || componentTrackOffset < 0 || durationOfComponent < 0 || sampleId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        } else if (sampleType == 'c' || sampleType == 'r') {
            CallableStatement addSampleToCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.ADDSAMPLETOCOMPILATION(?, ?, ?, ?, ?, ?, ?)}");
            try {
                addSampleToCompilation.setInt(1, compilationId);
                addSampleToCompilation.setDouble(2, mainTrackOffset);
                addSampleToCompilation.setDouble(3, durationInMainTrack);
                addSampleToCompilation.setDouble(4, componentTrackOffset);
                addSampleToCompilation.setDouble(5, durationOfComponent);
                addSampleToCompilation.setInt(6, sampleId);
                addSampleToCompilation.setString(7, Character.toString(sampleType));
                if (addSampleToCompilation.executeUpdate() != 1) {
                    throw new SQLException("Couldn't add sample to compilation");
                }
                this.connection.commit();
                addSampleToCompilation.close();
            } catch (Exception e) {
                this.connection.rollback();
                addSampleToCompilation.close();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Given category doesn't exist");
        }
    }

    public void deleteSampleFromCompilation(int compilationId, int sampleId, int segmentId, char sampleType) throws Exception {
        if (compilationId < 1 || sampleId < 1 || segmentId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        } else if (sampleType == 'c' || sampleType == 'r') {
            CallableStatement deleteSampleFromCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.DELETESAMPLEFROMCOMPILATION(?, ?, ?, ?)}");
            try {
                deleteSampleFromCompilation.setInt(1, compilationId);
                deleteSampleFromCompilation.setInt(2, sampleId);
                deleteSampleFromCompilation.setInt(3, segmentId);
                deleteSampleFromCompilation.setString(4, Character.toString(sampleType));
                if (deleteSampleFromCompilation.executeUpdate() != 1) {
                    throw new SQLException("Couldn't delete sample from compilation");
                }
                this.connection.commit();
                deleteSampleFromCompilation.close();
            } catch (Exception e) {
                this.connection.rollback();
                deleteSampleFromCompilation.close();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Given category doesn't exist");
        }
    }

    public void updateSample(double id, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponentUsed) throws Exception{
        if(id < 1 || mainTrackOffset < 0 || durationInMainTrack < 0 || componentTrackOffset < 0 || durationOfComponentUsed < 0){
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        } else {
            CallableStatement updateSample = this.connection.prepareCall("{call COMPILATION_MGMT.UPDATESEGMENT(?, ?, ?, ?, ?)}");
            try {
                updateSample.setDouble(1, id);
                updateSample.setDouble(2, mainTrackOffset);
                updateSample.setDouble(3, durationInMainTrack);
                updateSample.setDouble(4, componentTrackOffset);
                updateSample.setDouble(5, durationOfComponentUsed);
                if(updateSample.executeUpdate() != 1){
                    throw new SQLException("Couldn't update segment");
                }
                this.connection.commit();
                updateSample.close();
            } catch (Exception e){
                this.connection.rollback();
                updateSample.close();
                throw e;
            }
        }
    }

    public void deleteCompilation(int id) throws Exception {
        if (id < 0) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement deleteCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.DELETECOMPILATION(?)}");
        try {
            deleteCompilation.setInt(1, id);
            if (deleteCompilation.executeUpdate() != 1) {
                throw new SQLException("Couldn't delete compilation");
            }
            this.connection.commit();
            deleteCompilation.close();
        } catch (Exception e) {
            this.connection.rollback();
            deleteCompilation.close();
            throw e;
        }
    }

    public void updateCompilation(int id, String name) throws Exception {
        if (id < 0 || name == null || name.equals("")) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement updateCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.UPDATECOMPILATION(?, ?)}");
        try {
            updateCompilation.setInt(1, id);
            updateCompilation.setString(2, name);
            if (updateCompilation.executeUpdate() != 1) {
                throw new SQLException("Couldn't update compilation");
            }
            this.connection.commit();
            updateCompilation.close();
        } catch (Exception e) {
            this.connection.rollback();
            updateCompilation.close();
            throw e;
        }
    }

    public void addContributorToCompilation(int compilationId, int contributorId, int roleId) throws Exception {
        if (compilationId < 1 || contributorId < 1 || roleId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement addContributorToCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.ADDCONTRIBUTORTOCOMPILATION(?, ?, ?)}");
        try {
            addContributorToCompilation.setInt(1, compilationId);
            addContributorToCompilation.setInt(2, contributorId);
            addContributorToCompilation.setInt(3, roleId);
            if (addContributorToCompilation.executeUpdate() != 1) {
                throw new SQLException("Couldn't add contributor to compilation");
            }
            this.connection.commit();
            addContributorToCompilation.close();
        } catch (Exception e) {
            this.connection.rollback();
            addContributorToCompilation.close();
            throw e;
        }
    }

    public void removeContributorToCompilation(int compilationId, int contributorId, int roleId) throws Exception {
        if (compilationId < 1 || contributorId < 1 || roleId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        CallableStatement removeContributorToCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.REMOVECONTRIBUTORFROMCOMPILATION(?, ?, ?)}");
        try {
            removeContributorToCompilation.setInt(1, compilationId);
            removeContributorToCompilation.setInt(2, contributorId);
            removeContributorToCompilation.setInt(3, roleId);
            if (removeContributorToCompilation.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove contributor to compilation");
            }
            this.connection.commit();
            removeContributorToCompilation.close();
        } catch (Exception e) {
            this.connection.rollback();
            removeContributorToCompilation.close();
            throw e;
        }
    }

    private void removeAllContributions(Compilation compilation){
//        for()
    }

    @Override
    public void add(Compilation compilation) throws SQLException {
        if(compilation == null) throw new NullPointerException("the compilation is null");
        this.addCompilation(compilation.getName());
        ObjectDownloader dl = ObjectDownloader.getInstance();
        Compilation addedCompilation = dl.loadLastCompilation();
    }

    @Override
    public void update(Compilation compilation) {

    }

    @Override
    public void remove(Compilation compilation) {

    }
}
