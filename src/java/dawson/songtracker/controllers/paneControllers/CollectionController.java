package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.edit.CollectionDetailEditController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.add.AddCollectionController;
import dawson.songtracker.controllers.searchPanel.CollectionSearchController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.utils.Loader;

import java.sql.SQLException;

public class CollectionController extends DefaultWithDetailsController<
        Collection,
        CollectionSearchController,
        AddCollectionController,
        CollectionDetailEditController>
{

    public CollectionController() {
        Loader.LoadAndSet(this);
    }

    @Override
    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Collection");
        this.populateTable();
        this.searchPanel.displayDefault();
    }

    @Override
    public void addNewEntry(Collection entry) {
        System.out.println("Added new collection");
        try {
            ObjectUploader.getInstance().addCollection(entry);
            this.populateTable();
            this.searchPanel.displayDefault();
            this.addPanel.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEntry(Collection entry) {
        try {
            System.out.println("Can't delete collections yet.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateEntry(Collection entry) {
        try {
            ObjectUploader.getInstance().updateCollection(entry, entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearch(SearchEvent search) {
        try {
            var collection = ObjectDownloader.getInstance().loadCollectionsByName(search.message);
            searchPanel.filterAndDisplay(collection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void populateTable() {
        try {
            var collections = ObjectDownloader.getInstance().loadAllCollections();
            searchPanel.populateTable(collections);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
