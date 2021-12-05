import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.DBObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.ProductionRole;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        assertEquals("Good For You", result.getName());
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
}