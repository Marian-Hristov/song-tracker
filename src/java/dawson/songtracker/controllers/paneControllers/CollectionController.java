package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.DBObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.add.AddCollectionController;
import dawson.songtracker.controllers.searchPanel.CollectionSearchController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.utils.Loader;

import java.sql.SQLException;

public class CollectionController extends DefaultController<
        Collection,
        CollectionSearchController,
        AddCollectionController>
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
            //ObjectUploader.getInstance().collec(entry);
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
            collections.forEach(System.out::println);
            searchPanel.populateTable(collections);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
