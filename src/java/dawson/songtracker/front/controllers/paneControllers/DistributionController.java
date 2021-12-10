package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.add.AddDistributionController;
import dawson.songtracker.front.controllers.edit.DistributionDetailEditController;
import dawson.songtracker.front.controllers.searchPanel.DistributionSearchController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.front.utils.Loader;

public class DistributionController extends DefaultWithDetailsController<
        Distribution, DistributionSearchController, AddDistributionController, DistributionDetailEditController>
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
