package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.edit.CollectionDetailEditController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.front.controllers.add.AddCollectionController;
import dawson.songtracker.front.controllers.searchPanel.CollectionSearchController;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.front.utils.Loader;

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
