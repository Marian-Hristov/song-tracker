import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.DBObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Components.Segment;
import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.ProductionRole;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectUploaderTests {

    private final String userName = "";
    private final String password = "";

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
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, sampledCompilations, sampledRecordings, contributions);
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
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, sampledCompilations, sampledRecordings, contributions);
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
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);
        assertEquals("Pipe it up", dl.loadCompilation(1).getName());

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

        ArrayList<Segment<Compilation>> sampledCompilations = new ArrayList<>();
        ArrayList<Segment<Recording>> sampledRecordings = new ArrayList<>();
        Map<CompilationRole, ArrayList<Contributor>> contributions = new HashMap<>();
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, sampledCompilations, sampledRecordings, contributions);
        ul.addCompilation(compilation);
        ul.deleteCompilation(compilation);
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
        Compilation compilation = new Compilation(1, "Pipe it up", new Timestamp(0), 110.3, sampledCompilations, sampledRecordings, contributions);
        Compilation compilation1 = new Compilation(1, "Pipe it down", new Timestamp(0), 110.3, sampledCompilations, sampledRecordings, contributions);
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
}