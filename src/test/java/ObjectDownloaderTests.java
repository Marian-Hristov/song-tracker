import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectDownloaderTests {
    private final String userName = "A2041723";
    private final String password = "ertatera";

    @Test
    public void recordingByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Recording> recordings = dl.loadRecordingsByName("beat it vocals");
        assertEquals("beat it vocals", recordings.get(0).getName());
        for (Recording recording :
                recordings) {
            System.out.println(recording);
        }
    }

    @Test
    public void compilationsByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Compilation> compilations = dl.loadCompilationsByName("Leave the Door Open vocals comp");
        assertEquals("Leave the Door Open vocals comp", compilations.get(0).getName());
        for (Compilation compilation :
                compilations) {
            System.out.println(compilation);
        }
    }

    @Test
    public void contributorsByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Contributor> contributors = dl.loadContributorsByName("Michael Jackson");
        assertEquals("Michael Jackson", contributors.get(0).getName());
        for (Contributor contributor :
                contributors) {
            System.out.println(contributor);
        }
    }

    @Test
    public void productionRolesByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<ProductionRole> productionRoles = dl.loadProductionRolesByName("composer");
        assertEquals("composer", productionRoles.get(0).getName());
        for (ProductionRole productionRole :
                productionRoles) {
            System.out.println(productionRole);
        }
    }

    @Test
    public void musicalRolesByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<MusicianRole> roles = dl.loadMusicianRolesByName("bongosero");
        assertEquals("bongosero", roles.get(0).getName());
        for (MusicianRole role :
                roles) {
            System.out.println(role);
        }
    }

    @Test
    public void compilationRolesByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<CompilationRole> roles = dl.loadCompilationRolesByName("recording engineer");
        assertEquals("recording engineer", roles.get(0).getName());
        for (CompilationRole role :
                roles) {
            System.out.println(role);
        }
    }

    @Test
    public void recordLabelsByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<RecordLabel> recordLabels = dl.loadRecordLabelsByName("Atlantic");
        assertEquals("Atlantic", recordLabels.get(0).getName());
        for (RecordLabel rl :
                recordLabels) {
            System.out.println(rl);
        }
    }

    @Test
    public void marketsByName() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Market> markets = dl.loadMarketsByName("North-America");
        assertEquals("North-America", markets.get(0).getName());
        for (Market market :
                markets) {
            System.out.println(market);
        }
    }

    @Test
    public void collectionsByName() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Collection> collections = dl.loadCollectionsByName("Believer");
        assertEquals("Believer", collections.get(0).getName());
        for (Collection collection :
                collections) {
            System.out.println(collection);
        }
    }

    @Test
    public void markets() throws SQLException{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Market> markets = dl.loadFirstMarkets(50);
        System.out.println(markets.size());
        for(Market market : markets){
            System.out.println(market);
        }
    }

    @Test
    public void recordLabels() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<RecordLabel> recordLabels = dl.loadFirstRecordLabels(50);
        System.out.println(recordLabels.size());
        for(RecordLabel recordLabel : recordLabels){
            System.out.println(recordLabel);
        }
    }

    @Test
    public void musicalRole() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<MusicianRole> musicianRoles = dl.loadFirstMusicianRoles(50);
        System.out.println(musicianRoles.size());
        for (MusicianRole mr :
                musicianRoles) {
            System.out.println(mr);
        }

    }

    @Test
    public void productionRole() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<ProductionRole> productionRoles = dl.loadFirstProductionRoles(50);
        System.out.println(productionRoles.size());
        for (ProductionRole productionRole :
                productionRoles) {
            System.out.println(productionRole);
        }
    }

    @Test
    public void compilationRole() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<CompilationRole> compilationRoles = dl.loadFirstCompilationRoles(50);
        System.out.println(compilationRoles.size());
        for (CompilationRole compRole :
                compilationRoles) {
            System.out.println(compRole);
        }
    }

    @Test
    public void contributor() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Contributor> contributors = dl.loadFirstContributors(50);
        System.out.println(contributors.size());
        for (Contributor contributor :
                contributors) {
            System.out.println(contributor);
        }

    }

    @Test
    public void compilation() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Compilation> compilations = dl.loadFirstCompilations(50);
        System.out.println(compilations.size());
        for (Compilation compilation :
                compilations) {
            System.out.println(compilation);
        }
    }

    @Test
    public void recordings() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Recording> recordings = dl.loadFirstRecordings(50);
        System.out.println(recordings.size());
        for (Recording recording :
                recordings) {
            System.out.println(recording);
        }
    }

    @Test
    public void collections() throws SQLException {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Collection> collections = dl.loadFirstCollections(50);
        System.out.println(collections.size());
        for (Collection collection :
                collections) {
            System.out.println(collection);
        }
    }
}
