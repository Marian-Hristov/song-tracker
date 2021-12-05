import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.ProductionRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ObjectDownloaderTests {

//    @Test
//    public void musicalRole() throws SQLException {
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        MusicianRole mr = dl.loadMusicianRole(1);
//        assertEquals(1, mr.getId());
//        assertEquals("accordionist", mr.getName());
//    }
//
//    @Test
//    public void productionRole() throws SQLException {
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        ProductionRole pr = dl.loadProductionRole(1);
//        assertEquals(1, pr.getId());
//        assertEquals("accordionist", pr.getName());
//    }
//
//    @Test
//    public void recordings() throws SQLException {
//        ObjectDownloader dl = ObjectDownloader.getInstance();
//        Recording recording = dl.loadRecording(1);
//
//        assertEquals(1, recording.getId());
//        for(Map.Entry<ProductionRole, ArrayList<Contributor>> entry : recording.getProductionContributions().entrySet()){
//            ProductionRole role = entry.getKey();
//            assertEquals("sound engineer", role.getName());
//            ArrayList<Contributor> contributors = entry.getValue();
//            for(Contributor contributor : contributors){
//                assertEquals("marian", contributor.getName());
//            }
//        }
//    }
}
