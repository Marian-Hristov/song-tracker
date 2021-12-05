import dawson.songtracker.DBObjects.DBConnection;
import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.DBObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.types.Distributions.Collection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectUploaderTests {

    // Each of the test is run after the build script has
    // been on run on the database

    @Test
    public void TestaddCollection() throws Exception{
        DBConnection.setUsername("A2033348");
        DBConnection.setPassword("6019@ria_database");
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ObjectUploader ul = ObjectUploader.getInstance();
//        ul.addCollection("Good For You");
        Collection collection = dl.loadCollection(1);
        assertEquals("Good For You", collection.getName());
    }

    @Test
    public void TestaddCompilation() throws Exception {
        DBConnection.setUsername("A2033348");
        DBConnection.setPassword("6019@ria_database");
    }
}