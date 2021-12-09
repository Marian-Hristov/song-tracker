package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.add.AddSongController;
import dawson.songtracker.controllers.edit.DistributionDetailEditController;
import dawson.songtracker.controllers.searchPanel.DistributionSearchController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.utils.Loader;

public class DistributionController extends DefaultWithDetailsController<
        Distribution, DistributionSearchController, AddSongController, DistributionDetailEditController>
{

    public DistributionController() {
        super(Distribution.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllDistributions() );
    }

    @Override
    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Distribution");
    }
    @Override
    public void onSearch(SearchEvent search) {
        System.out.println("You can't search for distributions?");
    }

}
