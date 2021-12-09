package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.edit.DefaultDetailEditController;
import dawson.songtracker.controllers.edit.LabelDetailEditController;
import dawson.songtracker.controllers.searchPanel.LabelSearchController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.utils.Loader;

import java.util.ArrayList;

public class RecordLabelController extends DefaultWithDetailsController<RecordLabel,
        LabelSearchController,
        DefaultDetailEditController,
        LabelDetailEditController>
{

    public RecordLabelController() {
        super(RecordLabel.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void initialize() {
        super.initialize();
        this.searchPanel.setLabel("Record Label");
    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllRecordLabels());
    }

}
