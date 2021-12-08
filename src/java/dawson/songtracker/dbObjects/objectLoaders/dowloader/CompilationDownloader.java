package dawson.songtracker.dbObjects.objectLoaders.dowloader;

import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.components.Segment;
import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

class CompilationDownloader {

    public static Compilation loadCompilation(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from compilations where compilation_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }

        Map<CompilationRole, ArrayList<Contributor>> compilationRoles = loadCompilationRoles(connection, id);
        ArrayList<Segment<Compilation>> sampledCompilations = loadCompilationSamples(connection, id);
        ArrayList<Segment<Recording>> sampledRecordings = loadRecordingSamples(connection, id);
        Compilation compilation = new Compilation(id, rs.getString("compilation_name"), rs.getTimestamp("creation_time"), rs.getDouble("duration"), isReleased(connection, id), sampledCompilations, sampledRecordings, compilationRoles);
        ps.close();

        return compilation;
    }

    public static boolean isReleased(Connection connection, int compilationId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from collectionCompilations where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PreparedStatement selectDistributions = connection.prepareStatement("select * from distributions where collection_id = ?");
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

    public static ArrayList<Compilation> loadFirstCompilations(Connection connection, int nbRows) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from compilations fetch first ? rows only");
        ps.setInt(1, nbRows);
        ResultSet rs = ps.executeQuery();
        ArrayList<Compilation> compilations = new ArrayList<>();
        while(rs.next()){
            Compilation compilation = loadCompilation(connection, rs.getInt("compilation_id"));
            compilations.add(compilation);
        }
        ps.close();
        return compilations;
    }

    public static int totalCompilations(Connection connection) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select count(*) from compilations");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }

    public static ArrayList<Compilation> loadCompilationsByName(Connection connection, String name) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from compilations where compilation_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Compilation> compilations = new ArrayList<>();
        while(rs.next()){
            Compilation compilation = loadCompilation(connection, rs.getInt("compilation_id"));
            compilations.add(compilation);
        }
        ps.close();
        return compilations;
    }

    private static Map<CompilationRole, ArrayList<Contributor>> loadCompilationRoles(Connection connection, int compilationId) throws SQLException {
        if (!compilationExists(connection, compilationId)) throw new NoSuchElementException("the compilation doesn't exist");
        PreparedStatement ps = connection.prepareStatement("select * from compilationContributions where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new HashMap<>();
        }
        Map<CompilationRole, ArrayList<Contributor>> compilationRoles = new HashMap<>();
        do {
            CompilationRole cRole = RoleDownloader.loadCompilationRole(connection, rs.getInt("role_id"));
            Contributor contributor = RoleDownloader.loadContributor(connection, rs.getInt("contributor_id"));
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

    private static ArrayList<Segment<Compilation>> loadCompilationSamples(Connection connection, int compilationId) throws SQLException {
        if (!compilationExists(connection, compilationId)) return null;

        PreparedStatement ps = connection.prepareStatement("select * from compilationSamples where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new ArrayList<>();
        }
        ArrayList<Segment<Compilation>> sampleCompilations = new ArrayList<>();
        do {
            Compilation compilation = loadCompilation(connection, rs.getInt("compilation_used"));
            sampleCompilations.add(loadSegment(connection, rs.getInt("segment_id"), compilationId, compilation));
        } while (rs.next());
        ps.close();
        return sampleCompilations;
    }

    private static ArrayList<Segment<Recording>> loadRecordingSamples(Connection connection, int compilationId) throws SQLException {
        if (!compilationExists(connection, compilationId)) return null;

        PreparedStatement ps = connection.prepareStatement("select * from recordingSamples where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return new ArrayList<>();
        }
        ArrayList<Segment<Recording>> sampleCompilations = new ArrayList<>();
        do {
            Recording recording = RecordingDownloader.loadRecording(connection, rs.getInt("recording_id"));
            sampleCompilations.add(loadSegment(connection, rs.getInt("segment_id"), compilationId, recording));
        } while (rs.next());
        ps.close();
        return sampleCompilations;
    }

    private static Segment<Compilation> loadSegment(Connection connection, int id, int compilationMainTrackId, Compilation compilation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from segment where segment_id = ?");
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

    private static Segment<Recording> loadSegment(Connection connection, int id, int compilationMainTrackId, Recording recording) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from segment where segment_id = ?");
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


    private static boolean compilationExists(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select compilation_id from compilations where compilation_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        ps.close();
        return exists;
    }
}
