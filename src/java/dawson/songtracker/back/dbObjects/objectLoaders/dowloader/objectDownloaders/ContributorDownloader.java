package dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContributorDownloader extends ObjectDownloader<Contributor> {

    public ContributorDownloader(Connection connection) {
        super(connection);
    }

    private int totalContributors() throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement("select count(*) from contributors");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("count(*)");
        ps.close();
        return total;
    }
    /**
     * gets all the contributions a contributor has made for a compilation
     * @param contributorId the id of the contributor
     * @return a HashMap containing the id of the compilation and an arrayList containing what compilationRoles that contributor had for that compilation
     */
    public static Map<Compilation, ArrayList<CompilationRole>> getContributorCompilationRoles(Connection connection, int contributorId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from compilationContributions where contributor_id = ?");
        ps.setInt(1, contributorId);
        ResultSet rs = ps.executeQuery();
        Map<Compilation, ArrayList<CompilationRole>> contributions = new HashMap<>();
        while(rs.next()){
            // Fake compilation equaling the compilation in that row to check if it already is in the map without loading it from the db
            Compilation compilation = new Compilation(rs.getInt("compilation_id"), "", new Timestamp(0), 0, true, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
            ObjectDownloader<CompilationRole> compilationRoleDownloader = (ObjectDownloader<CompilationRole>) Downloader.getInstance().getLoader(CompilationRole.class);

            CompilationRole compilationRole = compilationRoleDownloader.load(rs.getInt("role_id"));
            if(contributions.containsKey(compilation)){
                contributions.get(compilation).add(compilationRole);
            } else {
                ObjectDownloader<Compilation> compilationDownloader = (ObjectDownloader<Compilation>) Downloader.getInstance().getLoader(Compilation.class);

                // if the compilation is not in the map load the whole compilation from the db and add it
                compilation = compilationDownloader.load(compilation.getId());
                ArrayList<CompilationRole> compilationRoles = new ArrayList<>();
                compilationRoles.add(compilationRole);
                contributions.put(compilation, compilationRoles);
            }
        }
        ps.close();
        return contributions;
    }

    public static Map<Recording, ArrayList<ProductionRole>> getContributorProductionRoles(Connection connection, int contributorId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from productionContributions where contributor_id = ?");
        ps.setInt(1, contributorId);
        ResultSet rs = ps.executeQuery();
        Map<Recording, ArrayList<ProductionRole>> contributions = new HashMap<>();
        while(rs.next()){
            // Fake compilation equaling the compilation in that row to check if it already is in the map without loading it from the db
            Recording recording = new Recording(rs.getInt("recording_id"), "", new Timestamp(0), 0, true, new HashMap<>(),new HashMap<>());

            ObjectDownloader<ProductionRole> productionRoleDownloader = (ObjectDownloader<ProductionRole>) Downloader.getInstance().getLoader(ProductionRole.class);

            ProductionRole productionRole = productionRoleDownloader.load(rs.getInt("role_id"));
            if(contributions.containsKey(recording)){
                contributions.get(recording).add(productionRole);
            } else {
                // if the recording is not in the map load the whole recording from the db and add it
                ObjectDownloader<Recording> recordingDownloader = (ObjectDownloader<Recording>) Downloader.getInstance().getLoader(Recording.class);
                recording = recordingDownloader.load(recording.getId());
                ArrayList<ProductionRole> productionRoles = new ArrayList<>();
                productionRoles.add(productionRole);
                contributions.put(recording, productionRoles);
            }
        }
        ps.close();
        return contributions;
    }

    public static Map<Recording, ArrayList<MusicianRole>> getContributorMusicianRoles(Connection connection, int contributorId) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from productionContributions where contributor_id = ?");
        ps.setInt(1, contributorId);
        ResultSet rs = ps.executeQuery();
        Map<Recording, ArrayList<MusicianRole>> contributions = new HashMap<>();
        while(rs.next()){
            // Fake compilation equaling the compilation in that row to check if it already is in the map without loading it from the db
            Recording recording = new Recording(rs.getInt("recording_id"), "", new Timestamp(0), 0, true, new HashMap<>(),new HashMap<>());

            ObjectDownloader<MusicianRole> musicianRoleDownloader = (ObjectDownloader<MusicianRole>) Downloader.getInstance().getLoader(MusicianRole.class);

            MusicianRole musicianRole = musicianRoleDownloader.load(rs.getInt("role_id"));
            if(contributions.containsKey(recording)){
                contributions.get(recording).add(musicianRole);
            } else {
                // if the recording is not in the map load the whole recording from the db and add it
                ObjectDownloader<Recording> recordingDownloader = (ObjectDownloader<Recording>) Downloader.getInstance().getLoader(Recording.class);
                recording = recordingDownloader.load(recording.getId());
                ArrayList<MusicianRole> musicianRoles = new ArrayList<>();
                musicianRoles.add(musicianRole);
                contributions.put(recording, musicianRoles);
            }
        }
        ps.close();
        return contributions;
    }

    @Override
    public Contributor load(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select contributor_name from contributors where contributor_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            return null;
        }
        Contributor contributor = new Contributor(id, rs.getString("contributor_name"));
        ps.close();
        return contributor;
    }

    @Override
    public Contributor loadLast() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("select contributor_id from contributors order by contributor_id desc fetch first row only");
        ResultSet rs = ps.executeQuery();
        int lastId = rs.getInt("contributor_id");
        ps.close();
        return load(lastId);
    }

    @Override
    public ArrayList<Contributor> loadAll() throws SQLException {
        int total = totalContributors();
        return loadFirst(total);
    }

    @Override
    public ArrayList<Contributor> loadFirst(int nbObjects) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select contributor_id from contributors fetch first ? rows only");
        ps.setInt(1, nbObjects);
        ResultSet rs = ps.executeQuery();
        ArrayList<Contributor> contributors = new ArrayList<>();
        while(rs.next()){
            Contributor contributor = load(rs.getInt("contributor_id"));
            contributors.add(contributor);
        }
        ps.close();
        return contributors;
    }

    @Override
    public ArrayList<Contributor> loadByName(String name) throws SQLException {
        if(name == null) throw new NullPointerException("the name is null");
        PreparedStatement ps = this.connection.prepareStatement("select contributor_id from contributors where contributor_name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        ArrayList<Contributor> contributors = new ArrayList<>();
        while(rs.next()){
            Contributor contributor = load(rs.getInt("contributor_id"));
            contributors.add(contributor);
        }
        ps.close();
        return contributors;
    }
}
