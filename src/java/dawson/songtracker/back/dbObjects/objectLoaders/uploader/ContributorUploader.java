package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders.ObjectDownloader;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.roles.Contributor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

class ContributorUploader implements IDBUploader<Contributor>{
    private final Connection connection;

    public ContributorUploader(Connection connection) throws SQLException {
        this.connection = connection;
    }

    private void addContributor(String name) throws Exception {
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

    private void deleteContributor(int id) throws Exception {
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

    private void updateContributor(String oldName, String newName) throws Exception {
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

    @Override
    public void add(Contributor contributor) throws Exception {
        if(contributor == null){
            throw new Exception("Contributor is null");
        }
        this.addContributor(contributor.getName());
    }

    @Override
    public void remove(Contributor contributor) throws Exception {
        if(contributor == null){
            throw new Exception("Contributor is null");
        }
        this.deleteContributor(contributor.getId());
    }

    @Override
    public void update(Contributor newContributor) throws Exception {
        if(newContributor == null){
            throw new Exception("Contributor is null");
        }
        ObjectDownloader<Contributor> dl = (ObjectDownloader<Contributor>) Downloader.getInstance().getLoader(Contributor.class);

        Contributor oldContributor = dl.load(newContributor.getId());
        if(!oldContributor.getName().equals(newContributor.getName())) this.updateContributor(oldContributor.getName(), newContributor.getName());
    }
}
