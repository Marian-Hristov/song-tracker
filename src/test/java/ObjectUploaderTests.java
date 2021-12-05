import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.DBObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.ProductionRole;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
    }

    @Test
    public void TestremoveCompilationToCollection() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }


    @Test
    public void TestupdateCollection() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestaddCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestaddSampleToCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestdeleteSampleFromCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestdeleteCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestupdateCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestaddContributorToCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
    }

    @Test
    public void TestremoveContributorToCompilation() throws Exception {
        DBConnection.setUsername(userName);
        DBConnection.setPassword(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
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
        DBConnection.setUsername(password);
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
        Map<MusicianRole, ArrayList<Contributor>> musicalContributions = new HashMap<>();
        Map<ProductionRole, ArrayList<Contributor>> productionContributions = new HashMap<>();
        Recording recording = new Recording(1, "See You Again", new Timestamp(System.currentTimeMillis()), 203, musicalContributions, productionContributions);
        ul.addRecording(recording);
        Recording result = dl.loadRecording(1);
        assertEquals("See You Again", result.getName());
    }

    @Test
    public void TestRemoveRecording() throws Exception{
        
    }
}