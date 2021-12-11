package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CompilationDownloader extends ObjectDownloader<Compilation>{

    public CompilationDownloader(Connection connection) {
        super(connection);
    }

    public boolean isReleased(int compilationId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from collectionCompilations where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PreparedStatement selectDistributions = this.connection.prepareStatement("select * from distributions where collection_id = ?");
            selectDistributions.setInt(1, rs.getInt("collection_id"));
            ResultSet allDistributions = selectDistributions.executeQuery();
            while(allDistributions.next()){
                Date releaseDate = allDistributions.getDate("release_date");
                if(releaseDate.before(new Date(new java.util.Date().getTime()))){
                    selectDistributions.close();
                    ps.close();
                    return true;
                }
            }
            selectDistributions.close();
        }
        ps.close();
        return false;
    }

    private int totalCompilations() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from compilations");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    private Map<CompilationRole, ArrayList<Contributor>> loadCompilationRoles(int compilationId) throws SQLException {
        if (!compilationExists(compilationId)) throw new NoSuchElementException("the compilation doesn't exist");
        PreparedStatement ps = this.connection.prepareStatement("select * from compilationContributions where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new HashMap<>();
        }
        Map<CompilationRole, ArrayList<Contributor>> compilationRoles = new HashMap<>();
        do {
            ObjectDownloader<CompilationRole> compilationRoleObjectDownloader = (ObjectDownloader<CompilationRole>) Downloader.getInstance().getLoader(CompilationRole.class);
            ObjectDownloader<Contributor> contributorObjectDownloader = (ObjectDownloader<Contributor>) Downloader.getInstance().getLoader(Contributor.class);
            CompilationRole cRole = compilationRoleObjectDownloader.load(rs.getInt("role_id"));
            Contributor contributor = contributorObjectDownloader.load(rs.getInt("contributor_id"));
            if (compilationRoles.containsKey(cRole)) {
                compilationRoles.get(cRole).add(contributor);
            } else {
                ArrayList<Contributor> contributors = new ArrayList<>();
                contributors.add(contributor);
                compilationRoles.put(cRole, contributors);
            }
        } while (rs.next());
        ps.close();
        return compilationRoles;
    }

    private ArrayList<Segment<Compilation>> loadCompilationSamples(int compilationId) throws SQLException {
        if (!compilationExists(compilationId)) return null;

        PreparedStatement ps = this.connection.prepareStatement("select * from compilationSamples where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new ArrayList<>();
        }
        ArrayList<Segment<Compilation>> sampleCompilations = new ArrayList<>();
        do {
            Compilation compilation = load(rs.getInt("compilation_used"));
            sampleCompilations.add(loadSegment(rs.getInt("segment_id"), compilationId, compilation));
        } while (rs.next());
        ps.close();
        return sampleCompilations;
    }

    private ArrayList<Segment<Recording>> loadRecordingSamples(int compilationId) throws SQLException {
        if (!compilationExists(compilationId)) return null;

        PreparedStatement ps = this.connection.prepareStatement("select * from recordingSamples where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new ArrayList<>();
        }
        ArrayList<Segment<Recording>> sampleCompilations = new ArrayList<>();
        do {
            ObjectDownloader<Recording> recordingDownloader = (ObjectDownloader<Recording>) Downloader.getInstance().getLoader(Recording.class);
            Recording recording = recordingDownloader.load(rs.getInt("recording_id"));
            sampleCompilations.add(loadSegment(rs.getInt("segment_id"), compilationId, recording));
        } while (rs.next());
        ps.close();
        return sampleCompilations;
    }

    private Segment<Compilation> loadSegment(int id, int compilationMainTrackId, Compilation compilation) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from segment where segment_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Segment<Compilation> segment = new Segment<>(id, compilationMainTrackId, compilation, rs.getDouble("main_track_offset"), rs.getDouble("duration_in_main_track"), rs.getDouble("component_track_offset"), rs.getDouble("duration_of_component_used"));
        ps.close();
        return segment;
    }

    private Segment<Recording> loadSegment(int id, int compilationMainTrackId, Recording recording) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from segment where segment_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Segment<Recording> segment = new Segment<>(id, compilationMainTrackId, recording, rs.getDouble("main_track_offset"), rs.getDouble("duration_in_main_track"), rs.getDouble("component_track_offset"), rs.getDouble("duration_of_component_used"));
        ps.close();
        return segment;
    }


    private boolean compilationExists(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select compilation_id from compilations where compilation_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        ps.close();
        return exists;
    }

    @Override
    public Compilation load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select * from compilations where compilation_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }

        Map<CompilationRole, ArrayList<Contributor>> compilationRoles = loadCompilationRoles(id);
        ArrayList<Segment<Compilation>> sampledCompilations = loadCompilationSamples(id);
        ArrayList<Segment<Recording>> sampledRecordings = loadRecordingSamples(id);
        Compilation compilation = new Compilation(id, rs.getString("compilation_name"), rs.getTimestamp("creation_time"), rs.getDouble("duration"), isReleased(id), sampledCompilations, sampledRecordings, compilationRoles);
        ps.close();

        return compilation;
    }

    @Override
    public Compilation loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select compilation_id from compilations order by compilation_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int lastId = rs.getInt("compilation_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<Compilation> loadAll() throws SQLException {
        int total = totalCompilations();
        return loadFirst(total);
    }

    @Override
    public ArrayList<Compilation> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select compilation_id from compilations fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<Compilation> compilations = new ArrayList<>();
        while(rs.next()){
            Compilation compilation = load(rs.getInt("compilation_id"));
            compilations.add(compilation);
        }
        ps.close();
        return compilations;
    }

    @Override
    public ArrayList<Compilation> loadByName(String name) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select compilation_id from compilations where compilation_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Compilation> compilations = new ArrayList<>();
        while(rs.next()){
            Compilation compilation = load(rs.getInt("compilation_id"));
            compilations.add(compilation);
        }
        ps.close();
        return compilations;
    }
}
