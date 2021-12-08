package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.add.AddSongController;
import dawson.songtracker.controllers.searchPanel.DistributionSearchController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.utils.Loader;

import java.sql.SQLException;

public class DistributionController extends DefaultController<
        Distribution, DistributionSearchController, AddSongController>
{

    public DistributionController() {
        Loader.LoadAndSet(this);
    }

    @Override
    public void initialize() {
        super.initialize();
        this.populateTable();
        this.searchPanel.displayDefault();
        this.searchPanel.setLabel("Distribution");
    }

    @Override
    public void addNewEntry(Distribution entry) {
        try {
            ObjectUploader.getInstance().addDistribution(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEntry(Distribution entry) {
        try {
            ObjectUploader.getInstance().removeDistribution(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntry(Distribution entry) {
        try {
            ObjectUploader.getInstance().updateDistribution(entry, entry);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSearch(SearchEvent search) {
        System.out.println("You can't search for distributions?");
    }

    @Override
    public void populateTable() {
        System.out.println("Populating distribution");
        try {
            var distributions = ObjectDownloader.getInstance().loadAllDistributions();
            distributions.forEach(System.out::println);
            this.searchPanel.populateTable(distributions);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
