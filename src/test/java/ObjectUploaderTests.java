import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.dbObjects.objectLoaders.uploader.IDBUploader;
import dawson.songtracker.back.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.back.types.roles.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectUploaderTests {

    // Each of the test is run after the build script has
    // been on run on the database
//
    @Test
    public void TestaddCollection() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collectionsInSet = new ArrayList<>();
        Collection collection = new Collection(1, "Whatever", false, compilations, collectionsInSet);
        IDBUploader<Collection> ob = (IDBUploader<Collection>) ul.getUploader(Collection.class);
        ob.add(collection);
        Collection result = dl.loadCollection(1);
        assertEquals("Whatever", result.getName());
    }

    @Test
    public void TestRemoveCollection() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collectionsInSet = new ArrayList<>();
        Collection collection = new Collection(1, "Whatever", false,  compilations, collectionsInSet);
        IDBUploader<Collection> ob = (IDBUploader<Collection>) ul.getUploader(Collection.class);
        ob.add(collection);
        ob.remove(collection);
        assertNull(dl.loadCollection(1));
    }

    @Test
    public void TestaddCompilationToCollection() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        IDBUploader<Collection> obCol = (IDBUploader<Collection>) ul.getUploader(Collection.class);
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0),  110.3, true, sampledCompilations, sampledRecordings, contributions);
        obCom.add(compilation);
        ArrayList<Compilation> compilations = new ArrayList<>();
        compilations.add(compilation);
        ArrayList<Collection> collections = new ArrayList<>();
        Collection collection = new Collection(1, "culture II", false, compilations, collections);
        obCol.add(collection);
        for (Compilation cmp :
                dl.loadCollection(1).getCompilations()) {
            assertEquals("Pipe it up", cmp.getName());
        }
    }

    @Test
    public void TestremoveCompilationFromCollection() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        IDBUploader<Collection> obCol = (IDBUploader<Collection>) ul.getUploader(Collection.class);
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        obCom.add(compilation);
        ArrayList<Compilation> compilations = new ArrayList<>();
        compilations.add(compilation);
        ArrayList<Collection> collections = new ArrayList<>();
        Collection collection = new Collection(1, "culture II", false, compilations, collections);
        obCol.add(collection);
        collection.getCompilations().remove(compilation);
        obCol.update(collection);
        for (Compilation ignored :
                dl.loadCollection(1).getCompilations()) {
            fail();
        }
    }

    @Test
    public void TestupdateCollection() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Collection> obCol = (IDBUploader<Collection>) ul.getUploader(Collection.class);
        ArrayList<Compilation> compilations = new ArrayList<>();
        ArrayList<Collection> collections = new ArrayList<>();
        Collection collection = new Collection(1, "culture II", false, compilations, collections);
        obCol.add(collection);
        Collection collection1 = new Collection(1, "culture III",false,  compilations, collections);
        obCol.update(collection1);
        Collection updatedCollection = dl.loadCollection(1);
        assertEquals("culture III", updatedCollection.getName());
    }

    @Test
    public void TestaddCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        obCom.add(compilation);
        assertEquals("Pipe it up", dl.loadCompilation(1).getName());
    }


    @Test
    public void TestaddSampleToCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Recording recording = new Recording(1, "Random instrumental", new Timestamp(1000000), 240, false, new HashMap<>(), new HashMap<>());
        Segment<Recording> sample = new Segment<>(1, 1, recording, 0, 100, 0, 100);
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0),  110.3, false, sampledCompilations, sampledRecordings, contributions);
        obRec.add(recording);
        sampledRecordings.add(sample);
        obCom.add(compilation);
        ArrayList<Segment<Recording>> result = dl.loadCompilation(1).getSampledRecordings();
        assertEquals(recording.getName(), result.get(0).getComponentTrack().getName());
    }

    // TODO not working getting 1 instead of 0
    @Test
    public void TestdeleteSampleFromCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Recording recording = new Recording(1, "Random instrumental", new Timestamp(1000000), 240, false,  new HashMap<>(), new HashMap<>());
        Segment<Recording> sample = new Segment<>(1, 1, recording, 0, 100, 0, 100);
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        obRec.add(recording);
        sampledRecordings.add(sample);
        obCom.add(compilation);
        compilation.getSampledRecordings().remove(sample);
        obCom.update(compilation);
        ArrayList<Segment<Recording>> result = dl.loadCompilation(1).getSampledRecordings();
        assertEquals(recording.getName(), result.get(0).getComponentTrack().getName());
        assertEquals(0, result.size());
    }

    @Test
    public void TestUpdateSample() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false,  sampledCompilations, sampledRecordings, contributions);
        Recording recording = new Recording(1, "Random instrumental", new Timestamp(1000000), 240, false, new HashMap<>(), new HashMap<>());
        Segment<Recording> sample = new Segment<>(1, 1, recording, 0, 100, 0, 100);
        obRec.add(recording);
        compilation.getSampledRecordings().add(sample);
        obCom.add(compilation);
        Segment<Recording> newSample = new Segment<>(1, 1, recording, 10, 78, 34, 67);
        compilation.getSampledRecordings().remove(sample);
        compilation.getSampledRecordings().add(newSample);
        obCom.update(compilation);
        ArrayList<Segment<Recording>> result = dl.loadCompilation(1).getSampledRecordings();
        assertEquals(newSample.getMainTrackOffset(), result.get(0).getMainTrackOffset());
        assertEquals(newSample.getDurationInMainTrack(), result.get(0).getDurationInMainTrack());
        assertEquals(newSample.getComponentTrackOffset(), result.get(0).getComponentTrackOffset());
        assertEquals(newSample.getComponentTrack().getDuration(), result.get(0).getComponentTrack().getDuration());
    }

    @Test
    public void TestdeleteCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        obCom.add(compilation);
        obCom.remove(compilation);
        assertNull(dl.loadCompilation(1));
    }

    @Test
    public void TestupdateCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        Compilation compilation1 = new Compilation(1, "Pipe it down", new Timestamp(0), 110.3, true, sampledCompilations, sampledRecordings, contributions);
        obCom.add(compilation);
        obCom.update(compilation1);
        Compilation updatedCompilation = dl.loadCompilation(1);
        assertEquals("Pipe it down", updatedCompilation.getName());
    }

    @Test
    public void TestaddContributorToCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        IDBUploader<Contributor> obCon = (IDBUploader<Contributor>) ul.getUploader(Contributor.class);
        Contributor contributor = new Contributor(1, "Bob");
        CompilationRole role = new CompilationRole(2, "remixer");
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        obCon.add(contributor);
        obCom.add(compilation);
        compilation.getContributions().get(role).add(contributor); // TODO this gives null?
        obCom.update(compilation);
//        ArrayList<Contributor> result = dl.loadCompilation(1).getContributorsInRole(role);
        assertEquals(contributor.getName(), dl.loadCompilation(1).getContributorsInRole(role).get(0).getName());
    }

    @Test
    public void TestremoveContributorToCompilation() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Compilation> obCom = (IDBUploader<Compilation>) ul.getUploader(Compilation.class);
        IDBUploader<Contributor> obCon = (IDBUploader<Contributor>) ul.getUploader(Contributor.class);
        Contributor contributor = new Contributor(1, "Bob");
        CompilationRole role = new CompilationRole(2, "remixer");
        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, false, sampledCompilations, sampledRecordings, contributions);
        obCon.add(contributor);
        compilation.getContributorsRoleMap().get(role).add(contributor);
        obCom.add(compilation);
        compilation.getContributorsRoleMap().get(role).remove(contributor); // TODO this also gives null?
        obCom.update(compilation);
//        ul.addContributorToCompilation(compilation, contributor, role);
//        ul.removeContributorToCompilation(compilation, contributor, role);
        Map<CompilationRole, ArrayList<Contributor>> result = dl.loadCompilation(1).getContributions();
        assertNull(result.get(role));
    }

    @Test
    public void TestaddContributor() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Contributor> obCon = (IDBUploader<Contributor>) ul.getUploader(Contributor.class);
        Contributor contributor = new Contributor(1, "Bob");
        obCon.add(contributor);
        Contributor result = dl.loadContributor(1);
        assertEquals("Bob", result.getName());
    }

    @Test
    public void TestdeleteContributor() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Contributor> obCon = (IDBUploader<Contributor>) ul.getUploader(Contributor.class);
        Contributor contributor = new Contributor(1, "Bob");
        obCon.add(contributor);
        obCon.remove(contributor);
        assertNull(dl.loadContributor(1));
    }

    @Test
    public void TestupdateContributor() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Contributor> obCon = (IDBUploader<Contributor>) ul.getUploader(Contributor.class);
        Contributor oldContributor = new Contributor(1, "Bob");
        Contributor newContributor = new Contributor(1, "Marley");
        obCon.add(oldContributor);
        obCon.update(newContributor);
        Contributor result = dl.loadContributor(1);
        assertEquals("Marley", result.getName());
    }

    @Test
    public void TestAddProductionRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        ProductionRole role = new ProductionRole(20, "brodda");
        obRole.add(role);
        ProductionRole result = dl.loadProductionRole(20);
        assertEquals("brodda", result.getName());
    }

    @Test
    public void TestAddMusicianRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        MusicianRole role = new MusicianRole(33, "bass booster");
        obRole.add(role);
        MusicianRole result = dl.loadMusicianRole(33);
        assertEquals("bass booster", result.getName());
    }

    @Test
    public void TestAddCompilationRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        CompilationRole role = new CompilationRole(1, "compiler");
        obRole.add(role);
        CompilationRole result = dl.loadCompilationRole(20);
        assertEquals("compiler", result.getName());
    }

    @Test
    public void TestRemoveProductionRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        ProductionRole role = new ProductionRole(20, "brodda");
        obRole.add(role);
        obRole.remove(role);
        assertNull(dl.loadProductionRole(20));
    }

    @Test
    public void TestRemoveMusicianRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        MusicianRole role = new MusicianRole(33, "bass booster");
        obRole.add(role);
        obRole.remove(role);
        assertNull(dl.loadMusicianRole(33));
    }

    @Test
    public void TestRemoveCompilationRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        CompilationRole role = new CompilationRole(20, "compiler");
        obRole.add(role);
        obRole.remove(role);
        assertNull(dl.loadCompilationRole(20));
    }


    @Test
    public void TestUpdateProductionRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        ProductionRole oldRole = new ProductionRole(20, "brodda");
        ProductionRole newRole = new ProductionRole(20, "sista");
        obRole.add(oldRole);
        obRole.update(newRole);
        assertEquals("sista", dl.loadProductionRole(20).getName());
    }

    @Test
    public void TestUpdateMusicianRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        MusicianRole oldRole = new MusicianRole(33, "bass booster");
        MusicianRole newRole = new MusicianRole(33, "chords booster");
        obRole.add(oldRole);
        obRole.update(newRole);
        assertEquals("chords booster", dl.loadMusicianRole(33).getName());
    }

    @Test
    public void TestUpdateCompilationRole() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Role> obRole = (IDBUploader<Role>) ul.getUploader(Role.class);
        CompilationRole oldRole = new CompilationRole(20, "compiler");
        CompilationRole newRole = new CompilationRole(20, "decompiler");
        obRole.add(oldRole);
        obRole.update(newRole);
        assertEquals("decompiler", dl.loadCompilationRole(20).getName());
    }

    @Test
    public void TestAddRecording() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, false, musicalContributions, productionContributions);
        obRec.add(recording);
        Recording result = dl.loadRecording(1);
        assertEquals("See You Again", result.getName());
    }

    @Test
    public void TestRemoveRecording() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
        obRec.add(recording);
        obRec.remove(recording);
        assertNull(dl.loadRecording(1));
    }

    @Test
    public void TestUpdateRecording() throws Exception {
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording oldRecording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
        Recording newRecording = new Recording(1, "Pipe it up", new Timestamp(System.currentTimeMillis()), 203, false, musicalContributions, productionContributions);
        obRec.add(oldRecording);
        obRec.update(newRecording);
        Recording result = dl.loadRecording(1);
        assertEquals("Pipe it up", result.getName());
    }

//    @Test
//    public void addContributorToRecording() throws Exception{
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        ObjectUploader ul = ObjectUploader.getInstance();
//        IDBUploader<Recording> obRec = (IDBUploader<Recording>) ul.getUploader(Recording.class);
//        IDBUploader<Contributor> obCon = (IDBUploader<Contributor>) ul.getUploader(Contributor.class);
//        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
//        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
//        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
//        Contributor contributor1 = new Contributor(1, "Bob");
//        Contributor contributor2 = new Contributor(2, "Marley");
//        ProductionRole pRole = new ProductionRole(1, "composer");
//        MusicianRole mRole = new MusicianRole(1, "accordionist");
//        ul.addContributor(contributor1);
//        ul.addContributor(contributor2);
//        ul.addRecording(recording);
//        ul.addContributorToRecording(recording, contributor1, pRole);
//        ul.addContributorToRecording(recording, contributor2, mRole);
//        Recording result = dl.loadRecording(1);
//        for ( ProductionRole role : result.getProductionContributions().keySet()) {
//            assertEquals("composer", role.getName());
//        }
//        for( MusicianRole role : result.getMusicalContributions().keySet()) {
//            assertEquals("accordionist", role.getName());
//        }
//        assertEquals("Bob", result.getProductionContributions().get(pRole).get(0).getName());
//        assertEquals("Marley", result.getMusicalContributions().get(mRole).get(0).getName());
//    }

//    @Test
//    public void removeContributorToRecording() throws Exception{
//        
//
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        ObjectUploader ul = ObjectUploader.getInstance();
//        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
//        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
//        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, true, musicalContributions, productionContributions);
//        Contributor contributor1 = new Contributor(1, "Bob");
//        Contributor contributor2 = new Contributor(2, "Marley");
//        ProductionRole pRole = new ProductionRole(1, "composer");
//        MusicianRole mRole = new MusicianRole(1, "accordionist");
//        ul.addContributor(contributor1);
//        ul.addContributor(contributor2);
//        ul.addRecording(recording);
//        ul.addContributorToRecording(recording, contributor1, pRole);
//        ul.addContributorToRecording(recording, contributor2, mRole);
//        ul.removeContributorToRecording(recording, contributor1, pRole);
//        ul.removeContributorToRecording(recording, contributor2, mRole);
//        Recording result = dl.loadRecording(1);
//        assertEquals(0, result.getProductionContributions().size());
//        assertEquals(0, result.getMusicalContributions().size());
//    }

    @Test
    public void TestAddMarket() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Market> obMar = (IDBUploader<Market>) ul.getUploader(Market.class);
        Market market = new Market(7, "Canada");
        obMar.add(market);
        Market result = dl.loadMarket(7);
        assertEquals("Canada", result.getName());
    }

    @Test
    public void TestRemoveMarket() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Market> obMar = (IDBUploader<Market>) ul.getUploader(Market.class);
        Market market = new Market(7, "Canada");
        obMar.add(market);
        obMar.remove(market);
        assertNull(dl.loadMarket(7));
    }

    @Test
    public void TestUpdateMarket() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<Market> obMar = (IDBUploader<Market>) ul.getUploader(Market.class);
        Market market1 = new Market(7, "Canada");
        Market market2 = new Market(7, "USA");
        obMar.add(market1);
        obMar.update(market2);
        Market result = dl.loadMarket(7);
        assertEquals("USA", result.getName());
    }

    @Test
    public void TestAddLabel() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<RecordLabel> obLab = (IDBUploader<RecordLabel>) ul.getUploader(RecordLabel.class);
        RecordLabel label = new RecordLabel(1, "Aftermath");
        obLab.add(label);
        RecordLabel result = dl.loadRecordLabel(1);
        assertEquals("Aftermath", result.getName());
    }

    @Test
    public void TestRemoveLabel() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<RecordLabel> obLab = (IDBUploader<RecordLabel>) ul.getUploader(RecordLabel.class);
        RecordLabel label = new RecordLabel(1, "Aftermath");
        obLab.add(label);
        obLab.remove(label);
        assertNull(dl.loadRecordLabel(1));
    }

    @Test
    public void TestUpdateLabel() throws Exception{
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        IDBUploader<RecordLabel> obLab = (IDBUploader<RecordLabel>) ul.getUploader(RecordLabel.class);
        RecordLabel label1 = new RecordLabel(1, "Aftermath");
        RecordLabel label2 = new RecordLabel(1, "222 Records");
        obLab.add(label1);
        obLab.update(label2);
        assertEquals("222 Records", dl.loadRecordLabel(1).getName());
    }
//
//    @Test
//    public void TestAddDistribution() throws Exception{
//        
//
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        ObjectUploader ul = ObjectUploader.getInstance();
//        ArrayList<Compilation> compilations = new ArrayList<>();
//        ArrayList<Collection> collectionsInSet = new ArrayList<>();
//        Collection collection = new Collection(1, "Whatever", compilations, collectionsInSet);
//        RecordLabel label = new RecordLabel(1, "Aftermath");
//        Market market = new Market(7, "Canada");
//        Date releaseDate = new Date(10);
//        Distribution distribution = new Distribution(1, collection, releaseDate, label, market);
//        ul.addCollection(collection);
//        ul.addLabel(label);
//        ul.addMarket(market);
//        ul.addDistribution(distribution);
//        Distribution result = dl.loadDistribution(1);
//        assertEquals("Whatever", result.getCollection().getName());
//        assertEquals(releaseDate.toString(), result.getReleaseDate().toString());
//        assertEquals("Aftermath", result.getLabel().getName());
//        assertEquals("Canada", result.getMarket().getName());
//    }
//
//    @Test
//    public void TestRemoveDistribution() throws Exception{
//        
//
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        ObjectUploader ul = ObjectUploader.getInstance();
//        ArrayList<Compilation> compilations = new ArrayList<>();
//        ArrayList<Collection> collectionsInSet = new ArrayList<>();
//        Collection collection = new Collection(1, "Whatever", compilations, collectionsInSet);
//        RecordLabel label = new RecordLabel(1, "Aftermath");
//        Market market = new Market(7, "Canada");
//        Date releaseDate = new Date(10);
//        Distribution distribution = new Distribution(1, collection, releaseDate, label, market);
//        ul.addCollection(collection);
//        ul.addLabel(label);
//        ul.addMarket(market);
//        ul.addDistribution(distribution);
//        ul.removeDistribution(distribution);
//        assertNull(dl.loadDistribution(1));
//    }
//
//    @Test
//    public void TestUpdateDistribution() throws Exception{
//        
//
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        ObjectUploader ul = ObjectUploader.getInstance();
//        // First distribution
//        ArrayList<Compilation> compilations1 = new ArrayList<>();
//        ArrayList<Collection> collectionsInSet1 = new ArrayList<>();
//        Collection collection1 = new Collection(1, "Whatever", compilations1, collectionsInSet1);
//        RecordLabel label1 = new RecordLabel(1, "Aftermath");
//        Market market1 = new Market(7, "Canada");
//        Date releaseDate1 = new Date(10000000);
//        Distribution distribution1 = new Distribution(1, collection1, releaseDate1, label1, market1);
//        ul.addCollection(collection1);
//        ul.addLabel(label1);
//        ul.addMarket(market1);
//        ul.addDistribution(distribution1);
//        // Second distribution
//        ArrayList<Compilation> compilations2 = new ArrayList<>();
//        ArrayList<Collection> collectionsInSet2 = new ArrayList<>();
//        Collection collection2 = new Collection(2, "Good For You", compilations2, collectionsInSet2);
//        RecordLabel label2 = new RecordLabel(2, "222 Records");
//        Market market2 = new Market(8, "USA");
//        Date releaseDate2 = new Date(2000000000);
//        Distribution distribution2 = new Distribution(1, collection2, releaseDate2, label2, market2);
//        ul.addLabel(label2);
//        ul.addMarket(market2);
//        ul.addCollection(collection2);
//        ul.updateDistribution(distribution1, distribution2);
//        Distribution result = dl.loadDistribution(1);
//        assertEquals(collection2.getName(), result.getCollection().getName());
//        assertEquals(releaseDate2.toString(), result.getReleaseDate().toString());
//        assertEquals(label2.getName(), result.getLabel().getName());
//        assertEquals(market2.getName(), result.getMarket().getName());
//    }
}