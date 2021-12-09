package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.CacheManager;
import dawson.songtracker.controllers.edit.CollectionDetailEditController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.add.AddCollectionController;
import dawson.songtracker.controllers.searchPanel.CollectionSearchController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.utils.Loader;

public class CollectionController extends DefaultWithDetailsController<
        Collection,
        CollectionSearchController,
        AddCollectionController,
        CollectionDetailEditController>
{

    public CollectionController() {
        super(Collection.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllCollections());
    }

    @Override
    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Collection");
        this.searchPanel.displayDefault();
    }

}
