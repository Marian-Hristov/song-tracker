package dawson.songtracker.DBObjects.objectLoaders.dowloader;

import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Components.Segment;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class CompilationDownloader {

    public static Compilation loadCompilation(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from compilations where compilation_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next())return null;

        Map<CompilationRole, ArrayList<Contributor>> compilationRoles = loadCompilationRoles(connection, id);
        ArrayList<Segment<Compilation>> sampledCompilations = loadCompilationSamples(connection, id);
        ArrayList<Segment<Recording>> sampledRecordings = loadRecordingSamples(connection, id);
        return new Compilation(id, rs.getString("compilation_name"), rs.getTimestamp("creation_time"), rs.getDouble("duration"), sampledCompilations, sampledRecordings, compilationRoles);
    }

    private static Map<CompilationRole, ArrayList<Contributor>> loadCompilationRoles(Connection connection, int compilationId) throws SQLException {
        if(!compilationExists(connection, compilationId)) return null;

        PreparedStatement ps = connection.prepareStatement("select * from compilationContributions where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return new HashMap<>();

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
        return compilationRoles;
    }

    private static ArrayList<Segment<Compilation>> loadCompilationSamples(Connection connection, int compilationId) throws SQLException {
        if(!compilationExists(connection, compilationId)) return null;

        PreparedStatement ps = connection.prepareStatement("select * from compilationSamples where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return new ArrayList<>();
        Compilation compilationMainTrack = loadCompilation(connection, compilationId);
        ArrayList<Segment<Compilation>> sampleCompilations = new ArrayList<>();
        do {
            Compilation compilation = loadCompilation(connection, rs.getInt("compilation_used"));
            sampleCompilations.add(loadSegment(connection, rs.getInt("segment_id"), compilationMainTrack, compilation));
        } while (rs.next());
        return sampleCompilations;
    }

    private static ArrayList<Segment<Recording>> loadRecordingSamples(Connection connection, int compilationId) throws SQLException {
        if(!compilationExists(connection, compilationId)) return null;

        PreparedStatement ps = connection.prepareStatement("select * from recordingSamples where compilation_id = ?");
        ps.setInt(1, compilationId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return new ArrayList<>();
        Compilation compilationMainTrack = loadCompilation(connection, compilationId);
        ArrayList<Segment<Recording>> sampleCompilations = new ArrayList<>();
        do {
            Recording recording = RecordingDownloader.loadRecording(connection, rs.getInt("compilation_used"));
            sampleCompilations.add(loadSegment(connection, rs.getInt("segment_id"), compilationMainTrack, recording));
        } while (rs.next());
        return sampleCompilations;
    }

    private static Segment<Compilation> loadSegment(Connection connection, int id, Compilation compilationMainTrack, Compilation compilation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from segments where segment_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) return null;
        return new Segment<>(id, compilationMainTrack, compilation, rs.getDouble("main_track_offset"), rs.getDouble("duration_in_main_track"), rs.getDouble("component_track_offset"), rs.getDouble("duration_of_component_used"));
    }

    private static Segment<Recording> loadSegment(Connection connection, int id, Compilation compilationMainTrack, Recording recording) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from segments where segment_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) return null;
        return new Segment<>(id, compilationMainTrack, recording, rs.getDouble("main_track_offset"), rs.getDouble("duration_in_main_track"), rs.getDouble("component_track_offset"), rs.getDouble("duration_of_component_used"));
    }



    private static boolean compilationExists(Connection connection, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select id from compilations where compilation_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
