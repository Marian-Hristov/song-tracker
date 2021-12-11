package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders.ObjectDownloader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

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

    private void addSampleToCompilation(int compilationId, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponent, int sampleId, char sampleType) throws SQLException {
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

    private void removeSampleFromCompilation(int compilationId, int sampleId, char sampleType) throws SQLException {
        if (compilationId < 1 || sampleId < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        } else if (sampleType == 'c' || sampleType == 'r') {
            CallableStatement deleteSampleFromCompilation = this.connection.prepareCall("{call COMPILATION_MGMT.DELETESAMPLEFROMCOMPILATION(?, ?, ?)}");
            try {
                deleteSampleFromCompilation.setInt(1, compilationId);
                deleteSampleFromCompilation.setInt(2, sampleId);
                deleteSampleFromCompilation.setString(3, Character.toString(sampleType));
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

    private void updateSample(double id, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponentUsed) throws Exception{
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

    private void removeCompilation(int id) throws SQLException {
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

    private void updateCompilation(int id, String name) throws SQLException {
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

    private void addContributorToCompilation(int compilationId, int contributorId, int roleId) throws SQLException {
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

    private void removeContributorFromCompilation(int compilationId, int contributorId, int roleId) throws SQLException {
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

    private void addAllContributions(Compilation compilation) throws SQLException {
        if(compilation == null) throw new NullPointerException("the compilation is null");
        for(Map.Entry<CompilationRole, ArrayList<Contributor>> entry : compilation.getContributions().entrySet()){
            for(Contributor contributor : entry.getValue()){
                this.addContributorToCompilation(compilation.getId(), contributor.getId(), entry.getKey().getId());
            }
        }
    }

    private void removeAllContributions(Compilation compilation) throws SQLException {
        if(compilation == null) throw new NullPointerException("the compilation is null");
        for(Segment<Compilation> compilationSegment : compilation.getSampledCompilations()){
            this.removeSampleFromCompilation(compilation.getId(), compilationSegment.getMainTrackId(), 'c');
        }
    }

    private void addAllSegments(Compilation compilation) throws SQLException {
        for(Segment<Compilation> compilationSegment : compilation.getSampledCompilations()){
            this.addSampleToCompilation(compilationSegment.getId(), compilationSegment.getMainTrackOffset(), compilationSegment.getDurationInMainTrack(), compilationSegment.getComponentTrackOffset(), compilationSegment.getDurationOfComponentUsed(), compilationSegment.getComponentTrack().getId(), 'c');
        }
        for(Segment<Recording> segment : compilation.getSampledRecordings()){
            this.addSampleToCompilation(segment.getId(), segment.getMainTrackOffset(), segment.getDurationInMainTrack(), segment.getComponentTrackOffset(), segment.getDurationOfComponentUsed(), segment.getComponentTrack().getId(), 'r');
        }
    }

    private void removeAllSegments(Compilation compilation) throws SQLException {
        for(Segment<Recording> segment : compilation.getSampledRecordings()){
            this.removeSampleFromCompilation(compilation.getId(), segment.getMainTrackId(), 'r');
        }
        for(Map.Entry<CompilationRole, ArrayList<Contributor>> entry : compilation.getContributions().entrySet()){
            for(Contributor contributor : entry.getValue()){
                this.removeContributorFromCompilation(compilation.getId(), contributor.getId(), entry.getKey().getId());
            }
        }
    }

    @Override
    public void add(Compilation compilation) throws SQLException {
        if(compilation == null) throw new NullPointerException("the compilation is null");
        this.addCompilation(compilation.getName());
        ObjectDownloader<Compilation> dl = (ObjectDownloader<Compilation>) Downloader.getInstance().getLoader(Compilation.class);
        compilation.setId(dl.loadLast().getId());
        addAllContributions(compilation);
        addAllSegments(compilation);
    }

    @Override
    public void update(Compilation compilation) throws SQLException {
        if(compilation == null) throw new NullPointerException("the compilation is null");
        removeAllSegments(compilation);
        removeAllContributions(compilation);
        addAllContributions(compilation);
        addAllSegments(compilation);
        updateCompilation(compilation.getId(), compilation.getName());
    }

    @Override
    public void remove(Compilation compilation) throws SQLException {
        if(compilation == null) throw new NullPointerException("the compilation is null");
        removeAllSegments(compilation);
        removeAllContributions(compilation);
        removeCompilation(compilation.getId());
    }
}
