import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.types.Components.Compilation;
import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Distributions.Collection;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.Contributor;
import dawson.songtracker.types.Roles.MusicianRole;
import dawson.songtracker.types.Roles.ProductionRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ObjectDownloaderTests {

    @Test
    public void musicalRole() throws SQLException {
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
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<ProductionRole> productionRoles = dl.loadFirstProductionRoles(50);
        System.out.println(productionRoles.size());
        for (ProductionRole productionRole:
             productionRoles) {
            System.out.println(productionRole);

        }
    }

    @Test
    public void compilationRole() throws SQLException {
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
        ObjectDownloader dl = ObjectDownloader.getInstance();
        ArrayList<Collection> collections = dl.loadFirstCollections(50);
        System.out.println(collections.size());
        for (Collection collection :
                collections) {
            System.out.println(collection);
        }
    }
}
