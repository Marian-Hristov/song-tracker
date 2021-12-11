package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.front.controllers.assign.ContributorPopupController;
import dawson.songtracker.front.controllers.add.AddSongController;
import dawson.songtracker.front.controllers.searchPanel.SearchSongController;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.front.utils.Loader;

import java.util.ArrayList;

public class RecordingController extends DefaultWithDetailsController
        <Recording, SearchSongController, AddSongController, ContributorPopupController>
{

    public RecordingController() {
        super(Recording.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(()-> (ArrayList<Recording>) Downloader.getInstance().getLoader(Recording.class).loadAll());
    }

    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Recording");
//        this.populateTable();
//        this.searchPanel.displayDefault();
    }
}
