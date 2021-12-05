package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.Date;
import java.sql.CallableStatement;
import java.sql.SQLException;

class DistributionUploader {
    private final Connection connection;

    public DistributionUploader(Connection connection) {
        this.connection = connection;
    }

    public void addDistribution(int collectionId, Date releaseDate, int labelId, int marketId) throws Exception {
        if (collectionId < 1 || labelId < 1 || marketId < 1 || releaseDate == null) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement addDistribution = this.connection.prepareCall("{call DISTRIBUTION_MGMT.ADDDISTRIBUTION(?, ?, ?, ?)}");
            addDistribution.setInt(1, collectionId);
            addDistribution.setDate(2, releaseDate);
            addDistribution.setInt(3, labelId);
            addDistribution.setInt(4, marketId);
            if (addDistribution.executeUpdate() != 1) {
                throw new SQLException("Couldn't add distribution");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void removeDistribution(int id) throws Exception {
        if (id < 1) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement removeDistribution = this.connection.prepareCall("{call DISTRIBUTION_MGMT.REMOVEDISTRIBUTION(?)}");
            removeDistribution.setInt(1, id);
            if (removeDistribution.executeUpdate() != 1) {
                throw new SQLException("Couldn't remove distribution");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

    public void updateDistribution(int distributionId, int collectionId, Date releaseDate, int labelId, int marketId) throws Exception {
        if (distributionId < 1 || collectionId < 1 || labelId < 1 || marketId < 1 || releaseDate == null) {
            throw new IllegalArgumentException("One or more arguments are invalid or null");
        }
        try {
            CallableStatement updateDistribution = this.connection.prepareCall("{call DISTRIBUTION_MGMT.UPDATEDISTRIBUTION(?, ?, ?, ?, ?)}");
            updateDistribution.setInt(1, distributionId);
            updateDistribution.setInt(2, collectionId);
            updateDistribution.setDate(3, releaseDate);
            updateDistribution.setInt(4, labelId);
            updateDistribution.setInt(5, marketId);
            if (updateDistribution.executeUpdate() != 1) {
                throw new SQLException("Couldn't update distribution");
            }
            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            throw e;
        }
    }

}
