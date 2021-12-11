import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders.ObjectDownloader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectDownloaderTests {

    @Test
    public void recordingByName() throws SQLException{
        ObjectDownloader<Recording> dl = (ObjectDownloader<Recording>) Downloader.getInstance().getLoader(Recording.class);
        ArrayList<Recording> recordings = dl.loadByName("beat it vocals");
        assertEquals("beat it vocals", recordings.get(0).getName());
        for (Recording recording :
                recordings) {
            System.out.println(recording);
        }
    }

    @Test
    public void compilationsByName() throws SQLException{
        ObjectDownloader<Compilation> dl = (ObjectDownloader<Compilation>) Downloader.getInstance().getLoader(Compilation.class);
        ArrayList<Compilation> compilations = dl.loadByName("Leave the Door Open vocals comp");
        assertEquals("Leave the Door Open vocals comp", compilations.get(0).getName());
        for (Compilation compilation :
                compilations) {
            System.out.println(compilation);
        }
    }

    @Test
    public void contributorsByName() throws SQLException{
        ObjectDownloader<Contributor> dl = (ObjectDownloader<Contributor>) Downloader.getInstance().getLoader(Contributor.class);

        ArrayList<Contributor> contributors = dl.loadByName("Michael Jackson");
        assertEquals("Michael Jackson", contributors.get(0).getName());
        for (Contributor contributor :
                contributors) {
            System.out.println(contributor);
        }
    }

    @Test
    public void productionRolesByName() throws SQLException{
        ObjectDownloader<ProductionRole> dl = (ObjectDownloader<ProductionRole>) Downloader.getInstance().getLoader(ProductionRole.class);

        ArrayList<ProductionRole> productionRoles = dl.loadByName("composer");
        assertEquals("composer", productionRoles.get(0).getName());
        for (ProductionRole productionRole :
                productionRoles) {
            System.out.println(productionRole);
        }
    }

    @Test
    public void musicalRolesByName() throws SQLException{
        ObjectDownloader<MusicianRole> dl = (ObjectDownloader<MusicianRole>) Downloader.getInstance().getLoader(MusicianRole.class);
        ArrayList<MusicianRole> roles = dl.loadByName("bongosero");
        assertEquals("bongosero", roles.get(0).getName());
        for (MusicianRole role :
                roles) {
            System.out.println(role);
        }
    }

    @Test
    public void compilationRolesByName() throws SQLException{
        ObjectDownloader<CompilationRole> dl = (ObjectDownloader<CompilationRole>) Downloader.getInstance().getLoader(CompilationRole.class);
        ArrayList<CompilationRole> roles = dl.loadByName("recording engineer");
        assertEquals("recording engineer", roles.get(0).getName());
        for (CompilationRole role :
                roles) {
            System.out.println(role);
        }
    }

    @Test
    public void recordLabelsByName() throws SQLException{
        ObjectDownloader<RecordLabel> dl = (ObjectDownloader<RecordLabel>) Downloader.getInstance().getLoader(RecordLabel.class);
        ArrayList<RecordLabel> recordLabels = dl.loadByName("Atlantic");
        assertEquals("Atlantic", recordLabels.get(0).getName());
        for (RecordLabel rl :
                recordLabels) {
            System.out.println(rl);
        }
    }

    @Test
    public void marketsByName() throws SQLException{
        ObjectDownloader<Market> dl = (ObjectDownloader<Market>) Downloader.getInstance().getLoader(Market.class);
        ArrayList<Market> markets = dl.loadByName("North-America");
        assertEquals("North-America", markets.get(0).getName());
        for (Market market :
                markets) {
            System.out.println(market);
        }
    }

    @Test
    public void collectionsByName() throws SQLException {
        ObjectDownloader<Collection> dl = (ObjectDownloader<Collection>) Downloader.getInstance().getLoader(Collection.class);
        ArrayList<Collection> collections = dl.loadByName("Believer");
        assertEquals("Believer", collections.get(0).getName());
        for (Collection collection :
                collections) {
            System.out.println(collection);
        }
    }

    @Test
    public void markets() throws SQLException{
        ObjectDownloader<Market> dl = (ObjectDownloader<Market>) Downloader.getInstance().getLoader(Market.class);
        ArrayList<Market> markets = dl.loadFirst(50);
        System.out.println(markets.size());
        for(Market market : markets){
            System.out.println(market);
        }
    }

    @Test
    public void recordLabels() throws SQLException {
        ObjectDownloader<RecordLabel> dl = (ObjectDownloader<RecordLabel>) Downloader.getInstance().getLoader(RecordLabel.class);
        ArrayList<RecordLabel> recordLabels = dl.loadFirst(50);
        System.out.println(recordLabels.size());
        for(RecordLabel recordLabel : recordLabels){
            System.out.println(recordLabel);
        }
    }

    @Test
    public void musicalRole() throws SQLException {
        ObjectDownloader<MusicianRole> dl = (ObjectDownloader<MusicianRole>) Downloader.getInstance().getLoader(MusicianRole.class);
        ArrayList<MusicianRole> musicianRoles = dl.loadFirst(50);
        System.out.println(musicianRoles.size());
        for (MusicianRole mr :
                musicianRoles) {
            System.out.println(mr);
        }

    }

    @Test
    public void productionRole() throws SQLException {
        ObjectDownloader<ProductionRole> dl = (ObjectDownloader<ProductionRole>) Downloader.getInstance().getLoader(ProductionRole.class);
        ArrayList<ProductionRole> productionRoles = dl.loadFirst(50);
        System.out.println(productionRoles.size());
        for (ProductionRole productionRole :
                productionRoles) {
            System.out.println(productionRole);
        }
    }

    @Test
    public void compilationRole() throws SQLException {
        ObjectDownloader<CompilationRole> dl = (ObjectDownloader<CompilationRole>) Downloader.getInstance().getLoader(CompilationRole.class);
        ArrayList<CompilationRole> compilationRoles = dl.loadFirst(50);
        System.out.println(compilationRoles.size());
        for (CompilationRole compRole :
                compilationRoles) {
            System.out.println(compRole);
        }
    }

    @Test
    public void contributor() throws SQLException {
        ObjectDownloader<Contributor> dl = (ObjectDownloader<Contributor>) Downloader.getInstance().getLoader(Contributor.class);
        ArrayList<Contributor> contributors = dl.loadFirst(50);
        System.out.println(contributors.size());
        for (Contributor contributor :
                contributors) {
            System.out.println(contributor);
        }

    }

    @Test
    public void compilation() throws SQLException {
        ObjectDownloader<Compilation> dl = (ObjectDownloader<Compilation>) Downloader.getInstance().getLoader(Compilation.class);
        ArrayList<Compilation> compilations = dl.loadFirst(50);
        System.out.println(compilations.size());
        for (Compilation compilation :
                compilations) {
            System.out.println(compilation);
        }
    }

    @Test
    public void recordings() throws SQLException {
        ObjectDownloader<Recording> dl = (ObjectDownloader<Recording>) Downloader.getInstance().getLoader(Recording.class);
        ArrayList<Recording> recordings = dl.loadFirst(50);
        System.out.println(recordings.size());
        for (Recording recording :
                recordings) {
            System.out.println(recording);
        }
    }

    @Test
    public void collections() throws SQLException {
        ObjectDownloader<Collection> dl = (ObjectDownloader<Collection>) Downloader.getInstance().getLoader(Collection.class);
        ArrayList<Collection> collections = dl.loadFirst(50);
        System.out.println(collections.size());
        for (Collection collection :
                collections) {
            System.out.println(collection);
        }
    }

    @Test
    public void distributions() throws SQLException{
        ObjectDownloader<Distribution> dl = (ObjectDownloader<Distribution>) Downloader.getInstance().getLoader(Distribution.class);
        ArrayList<Distribution> distributions = dl.loadAll();
        System.out.println(distributions.size());
        for(Distribution distribution : distributions){
            System.out.println(distribution);
        }
    }
}
