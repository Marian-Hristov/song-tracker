package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.edit.ContributorDetailEditController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.front.controllers.add.AddContributorController;
import dawson.songtracker.front.controllers.searchPanel.ContributorSearchController;
import dawson.songtracker.front.event.AddContributorEvent;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.front.utils.Loader;

import java.util.ArrayList;

public class ContributorController extends DefaultWithDetailsController<
        Contributor, ContributorSearchController, AddContributorController, ContributorDetailEditController>
{

    public ContributorController() {
        super(Contributor.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        cache.setUpdateMethod(()-> (ArrayList<Contributor>) Downloader.getInstance().getLoader(Contributor.class).loadAll());
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
