package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.assign.ContributorPopupController;
import dawson.songtracker.controllers.add.AddSongController;
import dawson.songtracker.controllers.searchPanel.SearchSongController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.components.Recording;
import dawson.songtracker.utils.*;

import java.sql.SQLException;

public class RecordingController extends DefaultWithDetailsController
        <Recording, SearchSongController, AddSongController, ContributorPopupController>
{

    public RecordingController() {
        super(Recording.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllRecordings());
    }

    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Recording");
//        this.populateTable();
//        this.searchPanel.displayDefault();
    }
}
