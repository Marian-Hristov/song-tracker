package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.edit.CollectionDetailEditController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.front.controllers.add.AddCollectionController;
import dawson.songtracker.front.controllers.searchPanel.CollectionSearchController;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.front.utils.Loader;

import java.util.ArrayList;

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
        cache.setUpdateMethod(() -> (ArrayList<Collection>) Downloader.getInstance().getLoader(Collection.class).loadAll());
    }

    @Override
    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Collection");
        this.searchPanel.displayDefault();
    }

}
