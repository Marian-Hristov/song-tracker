import dawson.songtracker.dbObjects.DBConnection;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.components.Segment;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectUploaderTests {

    // TODO Don't forget to remove
    private final String userName = "A2033348";
    private final String password = "6019@ria_database";

    // Each of the test is run after the build script has
    // been on run on the database

    @Test
    public void TestaddCollection() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collectionsInSet = new ArrayList<>();
        Collection collection = new Collection(1, "Whatever", compilations, collectionsInSet);
        ul.addCollection(collection);
        Collection result = dl.loadCollection(1);
        assertEquals("Whatever", result.getName());
    }

    @Test
    public void TestaddCompilationToCollection() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);

        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collections = new ArrayList<>();
        Collection collection = new Collection(1, "culture II", compilations, collections);
        ul.addCollection(collection);

        ul.addCompilationToCollection(collection, compilation);
        for (Compilation cmp :
                dl.loadCollection(1).getCompilations()) {
            assertEquals("Pipe it up", cmp.getName());
        }
    }

    @Test
    public void TestremoveCompilationFromCollection() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);

        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collections = new ArrayList<>();
        Collection collection = new Collection(1, "culture II", compilations, collections);
        ul.addCollection(collection);

        ul.addCompilationToCollection(collection, compilation);
        ul.removeCompilationFromCollection(collection, compilation);
        for (Compilation ignored :
                dl.loadCollection(1).getCompilations()) {
            fail();
        }
    }


    @Test
    public void TestupdateCollection() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();

        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collections = new ArrayList<>();
        Collection collection = new Collection(1, "culture II", compilations, collections);
        ul.addCollection(collection);
        Collection collection1 = new Collection(1, "culture III", compilations, collections);
        ul.updateCollection(collection, collection1);
        Collection updatedCollection = dl.loadCollection(1);
        assertEquals("culture III", updatedCollection.getName());
    }

    @Test
    public void TestaddCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();

        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);
        assertEquals("Pipe it up", dl.loadCompilation(1).getName());

    }

    @Test
    public void TestaddSampleToCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0),  110.3, false, sampledCompilations, sampledRecordings, contributions);
        Recording recording = new Recording(1, "Random instrumental", new Timestamp(1000000), 240, false, new HashMap<>(), new HashMap<>());
        Segment<Recording> sample = new Segment<>(1, 1, recording, 0, 100, 0, 100);
        ul.addCompilation(compilation);
        ul.addRecording(recording);
        ul.addSampleToCompilation(sample);
        ArrayList<Segment<Recording>> result = dl.loadCompilation(1).getSampledRecordings();
        assertEquals(recording.getName(), result.get(0).getComponentTrack().getName());
    }

    @Test
    public void TestdeleteSampleFromCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        Recording recording = new Recording(1, "Random instrumental", new Timestamp(1000000), 240, false,  new HashMap<>(), new HashMap<>());
        Segment<Recording> sample = new Segment<>(1, 1, recording, 0, 100, 0, 100);
        ul.addCompilation(compilation);
        ul.addRecording(recording);
        ul.addSampleToCompilation(sample);
        ul.deleteSampleFromCompilation(sample);
        ArrayList<Segment<Recording>> result = dl.loadCompilation(1).getSampledRecordings();
        assertEquals(recording.getName(), result.get(0).getComponentTrack().getName());
        assertEquals(0, result.size());
    }

    @Test
    public void TestUpdateSample() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false,  sampledCompilations, sampledRecordings, contributions);
        Recording recording = new Recording(1, "Random instrumental", new Timestamp(1000000), 240, false, new HashMap<>(), new HashMap<>());
        Segment<Recording> sample = new Segment<>(1, 1, recording, 0, 100, 0, 100);
        ul.addCompilation(compilation);
        ul.addRecording(recording);
        ul.addSampleToCompilation(sample);
        Segment<Recording> newSample = new Segment<>(1, 1, recording, 10, 78, 34, 67);
        ul.updateSample(sample, newSample);
        ArrayList<Segment<Recording>> result = dl.loadCompilation(1).getSampledRecordings();
        assertEquals(newSample.getMainTrackOffset(), result.get(0).getMainTrackOffset());
        assertEquals(newSample.getDurationInMainTrack(), result.get(0).getDurationInMainTrack());
        assertEquals(newSample.getComponentTrackOffset(), result.get(0).getComponentTrackOffset());
        assertEquals(newSample.getComponentTrack().getDuration(), result.get(0).getComponentTrack().getDuration());
    }

    @Test
    public void TestdeleteCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();

        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);
        ul.deleteCompilation(compilation.getId());
        assertNull(dl.loadCompilation(1));
    }

    @Test
    public void TestupdateCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        Compilation compilation1 = new Compilation(1, "Pipe it down", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);
        ul.updateCompilation(compilation, compilation1);
        Compilation updatedCompilation = dl.loadCompilation(1);
        assertEquals("Pipe it down", updatedCompilation.getName());
    }

    @Test
    public void TestaddContributorToCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Contributor contributor = new Contributor(1, "Bob");
        CompilationRole role = new CompilationRole(2, "remixer");
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        ul.addContributor(contributor);
        ul.addCompilation(compilation);
        ul.addContributorToCompilation(compilation, contributor, role);
        ArrayList<Contributor> result = dl.loadCompilation(1).getContributorsInRole(role);
        assertEquals(contributor.getName(), result.get(0).getName());
    }

    @Test
    public void TestremoveContributorToCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Contributor contributor = new Contributor(1, "Bob");
        CompilationRole role = new CompilationRole(2, "remixer");
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        ul.addContributor(contributor);
        ul.addCompilation(compilation);
        ul.addContributorToCompilation(compilation, contributor, role);
        ul.removeContributorToCompilation(compilation, contributor, role);
        Map<CompilationRole, ArrayList<Contributor>> result = dl.loadCompilation(1).getContributions();
        assertNull(result.get(role));
    }

    @Test
    public void TestaddContributor() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Contributor contributor = new Contributor(1, "Bob");
        ul.addContributor(contributor);
        Contributor result = dl.loadContributor(1);
        assertEquals("Bob", result.getName());
    }

    @Test
    public void TestdeleteContributor() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Contributor contributor = new Contributor(1, "Bob");
        ul.addContributor(contributor);
        ul.deleteContributor(contributor);
        assertNull(dl.loadContributor(1));
    }

    @Test
    public void TestupdateContributor() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Contributor oldContributor = new Contributor(1, "Bob");
        Contributor newContributor = new Contributor(1, "Marley");
        ul.addContributor(oldContributor);
        ul.updateContributor(oldContributor, newContributor);
        Contributor result = dl.loadContributor(1);
        assertEquals("Marley", result.getName());
    }

    @Test
    public void TestAddProductionRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ProductionRole role = new ProductionRole(1, "brodda");
        ul.addRole(role);
        ProductionRole result = dl.loadProductionRole(20);
        assertEquals("brodda", result.getName());
    }

    @Test
    public void TestAddMusicianRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        MusicianRole role = new MusicianRole(1, "bass booster");
        ul.addRole(role);
        MusicianRole result = dl.loadMusicianRole(33);
        assertEquals("bass booster", result.getName());
    }

    @Test
    public void TestAddCompilationRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        CompilationRole role = new CompilationRole(1, "compiler");
        ul.addRole(role);
        CompilationRole result = dl.loadCompilationRole(20);
        assertEquals("compiler", result.getName());
    }

    @Test
    public void TestRemoveProductionRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ProductionRole role = new ProductionRole(20, "brodda");
        ul.addRole(role);
        ul.deleteRole(role);
        assertNull(dl.loadProductionRole(20));
    }

    @Test
    public void TestRemoveMusicianRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        MusicianRole role = new MusicianRole(33, "bass booster");
        ul.addRole(role);
        ul.deleteRole(role);
        assertNull(dl.loadMusicianRole(33));
    }

    @Test
    public void TestRemoveCompilationRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        CompilationRole role = new CompilationRole(20, "compiler");
        ul.addRole(role);
        ul.deleteRole(role);
        assertNull(dl.loadCompilationRole(20));
    }


    @Test
    public void TestUpdateProductionRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ProductionRole oldRole = new ProductionRole(20, "brodda");
        ProductionRole newRole = new ProductionRole(20, "sista");
        ul.addRole(oldRole);
        ul.updateRole(oldRole, newRole);
        assertEquals("sista", dl.loadProductionRole(20).getName());
    }

    @Test
    public void TestUpdateMusicianRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        MusicianRole oldRole = new MusicianRole(33, "bass booster");
        MusicianRole newRole = new MusicianRole(33, "chords booster");
        ul.addRole(oldRole);
        ul.updateRole(oldRole, newRole);
        assertEquals("chords booster", dl.loadMusicianRole(33).getName());
    }

    @Test
    public void TestUpdateCompilationRole() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        CompilationRole oldRole = new CompilationRole(20, "compiler");
        CompilationRole newRole = new CompilationRole(20, "decompiler");
        ul.addRole(oldRole);
        ul.updateRole(oldRole, newRole);
        assertEquals("decompiler", dl.loadCompilationRole(20).getName());
    }

    @Test
    public void TestAddRecording() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, false, musicalContributions, productionContributions);
        ul.addRecording(recording);
        Recording result = dl.loadRecording(1);
        assertEquals("See You Again", result.getName());
    }

    @Test
    public void TestRemoveRecording() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
        ul.addRecording(recording);
        ul.removeRecording(recording.getId());
        assertNull(dl.loadRecording(1));
    }

    @Test
    public void TestUpdateRecording() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording oldRecording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
        Recording newRecording = new Recording(1, "Pipe it up", new Timestamp(System.currentTimeMillis()), 203, false, musicalContributions, productionContributions);
        ul.addRecording(oldRecording);
        ul.updateRecording(oldRecording, newRecording);
        Recording result = dl.loadRecording(1);
        assertEquals("Pipe it up", result.getName());
    }

    @Test
    public void addContributorToRecording() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
        Contributor contributor1 = new Contributor(1, "Bob");
        Contributor contributor2 = new Contributor(2, "Marley");
        ProductionRole pRole = new ProductionRole(1, "composer");
        MusicianRole mRole = new MusicianRole(1, "accordionist");
        ul.addContributor(contributor1);
        ul.addContributor(contributor2);
        ul.addRecording(recording);
        ul.addContributorToRecording(recording, contributor1, pRole);
        ul.addContributorToRecording(recording, contributor2, mRole);
        Recording result = dl.loadRecording(1);
        for ( ProductionRole role : result.getProductionContributions().keySet()) {
            assertEquals("composer", role.getName());
        }
        for( MusicianRole role : result.getMusicalContributions().keySet()) {
            assertEquals("accordionist", role.getName());
        }
        assertEquals("Bob", result.getProductionContributions().get(pRole).get(0).getName());
        assertEquals("Marley", result.getMusicalContributions().get(mRole).get(0).getName());
    }

    @Test
    public void removeContributorToRecording() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
        Contributor contributor1 = new Contributor(1, "Bob");
        Contributor contributor2 = new Contributor(2, "Marley");
        ProductionRole pRole = new ProductionRole(1, "composer");
        MusicianRole mRole = new MusicianRole(1, "accordionist");
        ul.addContributor(contributor1);
        ul.addContributor(contributor2);
        ul.addRecording(recording);
        ul.addContributorToRecording(recording, contributor1, pRole);
        ul.addContributorToRecording(recording, contributor2, mRole);
        ul.removeContributorToRecording(recording, contributor1, pRole);
        ul.removeContributorToRecording(recording, contributor2, mRole);
        Recording result = dl.loadRecording(1);
        assertEquals(0, result.getProductionContributions().size());
        assertEquals(0, result.getMusicalContributions().size());
    }

    @Test
    public void TestAddMarket() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Market market = new Market(7, "Canada");
        ul.addMarket(market);
        Market result = dl.loadMarket(7);
        assertEquals("Canada", result.getName());
    }

    @Test
    public void TestRemoveMarket() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Market market = new Market(7, "Canada");
        ul.addMarket(market);
        ul.removeMarket(market);
        assertNull(dl.loadMarket(7));
    }

    @Test
    public void TestUpdateMarket() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Market market1 = new Market(7, "Canada");
        Market market2 = new Market(7, "USA");
        ul.addMarket(market1);
        ul.updateMarket(market1, market2);
        Market result = dl.loadMarket(7);
        assertEquals("USA", result.getName());
    }

    @Test
    public void TestAddLabel() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        RecordLabel label = new RecordLabel(1, "Aftermath");
        ul.addLabel(label);
        RecordLabel result = dl.loadRecordLabel(1);
        assertEquals("Aftermath", result.getName());
    }

    @Test
    public void TestRemoveLabel() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        RecordLabel label = new RecordLabel(1, "Aftermath");
        ul.addLabel(label);
        ul.removeLabel(label);
        assertNull(dl.loadRecordLabel(1));
    }

    @Test
    public void TestUpdateLabel() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        RecordLabel label1 = new RecordLabel(1, "Aftermath");
        RecordLabel label2 = new RecordLabel(1, "222 Records");
        ul.addLabel(label1);
        ul.updateLabel(label1, label2);
        assertEquals("222 Records", dl.loadRecordLabel(1).getName());
    }

    @Test
    public void TestAddDistribution() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collectionsInSet = new ArrayList<>();
        Collection collection = new Collection(1, "Whatever", compilations, collectionsInSet);
        RecordLabel label = new RecordLabel(1, "Aftermath");
        Market market = new Market(7, "Canada");
        Date releaseDate = new Date(10);
        Distribution distribution = new Distribution(1, collection, releaseDate, label, market);
        ul.addCollection(collection);
        ul.addLabel(label);
        ul.addMarket(market);
        ul.addDistribution(distribution);
        Distribution result = dl.loadDistribution(1);
        assertEquals("Whatever", result.getCollection().getName());
        assertEquals(releaseDate.toString(), result.getReleaseDate().toString());
        assertEquals("Aftermath", result.getLabel().getName());
        assertEquals("Canada", result.getMarket().getName());
    }

    @Test
    public void TestRemoveDistribution() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collectionsInSet = new ArrayList<>();
        Collection collection = new Collection(1, "Whatever", compilations, collectionsInSet);
        RecordLabel label = new RecordLabel(1, "Aftermath");
        Market market = new Market(7, "Canada");
        Date releaseDate = new Date(10);
        Distribution distribution = new Distribution(1, collection, releaseDate, label, market);
        ul.addCollection(collection);
        ul.addLabel(label);
        ul.addMarket(market);
        ul.addDistribution(distribution);
        ul.removeDistribution(distribution);
        assertNull(dl.loadDistribution(1));
    }

    @Test
    public void TestUpdateDistribution() throws Exception{
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        // First distribution
        ArrayList<Compilation> compilations1 = new ArrayList<>();
        ArrayList<Collection> collectionsInSet1 = new ArrayList<>();
        Collection collection1 = new Collection(1, "Whatever", compilations1, collectionsInSet1);
        RecordLabel label1 = new RecordLabel(1, "Aftermath");
        Market market1 = new Market(7, "Canada");
        Date releaseDate1 = new Date(10000000);
        Distribution distribution1 = new Distribution(1, collection1, releaseDate1, label1, market1);
        ul.addCollection(collection1);
        ul.addLabel(label1);
        ul.addMarket(market1);
        ul.addDistribution(distribution1);
        // Second distribution
        ArrayList<Compilation> compilations2 = new ArrayList<>();
        ArrayList<Collection> collectionsInSet2 = new ArrayList<>();
        Collection collection2 = new Collection(2, "Good For You", compilations2, collectionsInSet2);
        RecordLabel label2 = new RecordLabel(2, "222 Records");
        Market market2 = new Market(8, "USA");
        Date releaseDate2 = new Date(2000000000);
        Distribution distribution2 = new Distribution(1, collection2, releaseDate2, label2, market2);
        ul.addLabel(label2);
        ul.addMarket(market2);
        ul.addCollection(collection2);
        ul.updateDistribution(distribution1, distribution2);
        Distribution result = dl.loadDistribution(1);
        assertEquals(collection2.getName(), result.getCollection().getName());
        assertEquals(releaseDate2.toString(), result.getReleaseDate().toString());
        assertEquals(label2.getName(), result.getLabel().getName());
        assertEquals(market2.getName(), result.getMarket().getName());
    }
}