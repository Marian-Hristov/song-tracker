package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.edit.ContributorDetailEditController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.add.AddContributorController;
import dawson.songtracker.controllers.searchPanel.ContributorSearchController;
import dawson.songtracker.event.AddContributorEvent;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.utils.Loader;

import java.sql.SQLException;

public class ContributorController extends DefaultWithDetailsController<
        Contributor, ContributorSearchController, AddContributorController, ContributorDetailEditController>
{

    public ContributorController() {
        super(Contributor.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllContributors());
    }

    @Override
    public void initialize() {
        super.initialize();
//        this.populateTable();
//        searchPanel.displayDefault();
        this.addPanel.addEventHandler(AddContributorEvent.ADD_CONTRIBUTOR_EVENT, c -> {
            try {
                addNewEntry(c.contributor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
