package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class RecordingDownloader extends ObjectDownloader<Recording> {

    public RecordingDownloader(Connection connection) {
        super(connection);
    }

    private boolean isReleased(int recordingId) throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select * from recordingSamples where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            CompilationDownloader compilationDownloader = (CompilationDownloader) Downloader.getInstance().getLoader(Compilation.class);

            if(compilationDownloader.isReleased(rs.getInt("compilation_id"))){
                return true;
            }
        }
        ps.close();
        return false;
    }

    private int totalRecordings() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from recordings");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    private boolean recordingExists(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        ps.close();
        return exists;
    }

    private Map<ProductionRole, ArrayList<Contributor>> loadProductionContributions(int recordingId) throws SQLException {
        if (!recordingExists(recordingId))
            throw new NoSuchElementException("the recording with id: " + recordingId + " doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from productionContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new HashMap<>();
        }
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        do {
            ObjectDownloader<ProductionRole> productionRoleDownloader = (ObjectDownloader<ProductionRole>) Downloader.getInstance().getLoader(ProductionRole.class);
            ObjectDownloader<Contributor> contributorDownloader = (ObjectDownloader<Contributor>) Downloader.getInstance().getLoader(Contributor.class);

            ProductionRole productionRole = productionRoleDownloader.load(rs.getInt("role_id"));
            Contributor contributor = contributorDownloader.load(rs.getInt("contributor_id"));
            if (productionContributions.containsKey(productionRole)) {
                productionContributions.get(productionRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                productionContributions.put(productionRole, contributors);
            }
        } while (rs.next());

        ps.close();
        return productionContributions;
    }

    private Map<MusicianRole, ArrayList<Contributor>> loadMusicalContributions(int recordingId) throws SQLException {
        if (!recordingExists(recordingId))
            throw new NoSuchElementException("the recording with id: " + recordingId + " doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from musicalContributions where recording_id = ?");
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new HashMap<>();
        }


        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        do {
            ObjectDownloader<MusicianRole> musicianRoleDownloader = (ObjectDownloader<MusicianRole>) Downloader.getInstance().getLoader(MusicianRole.class);
            ObjectDownloader<Contributor> contributorDownloader = (ObjectDownloader<Contributor>) Downloader.getInstance().getLoader(Contributor.class);

            MusicianRole musicianRole = musicianRoleDownloader.load(rs.getInt("role_id"));
            Contributor contributor = contributorDownloader.load(rs.getInt("contributor_id"));
            if (musicalContributions.containsKey(musicianRole)) {
                musicalContributions.get(musicianRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                musicalContributions.put(musicianRole, contributors);
            }
        } while (rs.next());

        ps.close();

        return musicalContributions;
    }

    @Override
    public Recording load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from recordings where recording_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = loadProductionContributions(id);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = loadMusicalContributions(id);
        Recording recording = new Recording(id, rs.getString("recording_name"), rs.getTimestamp("creation_time"), rs.getInt("duration"), isReleased(id), musicalContributions, productionContributions);
        ps.close();
        return recording;
    }

    @Override
    public Recording loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select recording_id from recordings order by recording_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("recording_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<Recording> loadAll() throws SQLException {
        int total = totalRecordings();
        return loadFirst(total);
    }

    @Override
    public ArrayList<Recording> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select recording_id from recordings fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<Recording> recordings = new ArrayList<>();
        while(rs.next()){
            Recording recording = load(rs.getInt("recording_id"));
            recordings.add(recording);
        }
        ps.close();
        return recordings;
    }

    @Override
    public ArrayList<Recording> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = this.connection.prepareStatement("select recording_id from recordings where recording_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Recording> recordings = new ArrayList<>();
        while(rs.next()){
            Recording recording = load(rs.getInt("recording_id"));
            recordings.add(recording);
        }
        ps.close();
        return recordings;
    }
}
