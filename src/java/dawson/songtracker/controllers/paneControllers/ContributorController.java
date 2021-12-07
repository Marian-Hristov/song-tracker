package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.DBObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.DBObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.add.AddContributorController;
import dawson.songtracker.controllers.searchPanel.ContributorSearchController;
import dawson.songtracker.event.AddContributorEvent;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.utils.Loader;

import java.sql.SQLException;

public class ContributorController extends DefaultController<
        Contributor, ContributorSearchController, AddContributorController>
{

    public ContributorController() {
        Loader.LoadAndSet(this);
    }

    @Override
    public void initialize() {
        super.initialize();
        this.populateTable();
        searchPanel.displayDefault();
        this.addPanel.addEventHandler(AddContributorEvent.ADD_CONTRIBUTOR_EVENT, c -> addNewEntry(c.contributor));
    }

    @Override
    public void addNewEntry(Contributor entry) {
        try {
            ObjectUploader.getInstance().addContributor(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEntry(Contributor entry) {
        try {
            ObjectUploader.getInstance().deleteContributor(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntry(Contributor entry) {
        try {
            ObjectUploader.getInstance().updateContributor(entry, entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearch(SearchEvent search) {
        try {
            var contributors = ObjectDownloader.getInstance().loadContributorsByName(search.message);
            searchPanel.displaySearchResult(contributors);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void populateTable() {
        try {
            var contributors = ObjectDownloader.getInstance().loadFirstContributors(10);
            searchPanel.populateTable(contributors);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
