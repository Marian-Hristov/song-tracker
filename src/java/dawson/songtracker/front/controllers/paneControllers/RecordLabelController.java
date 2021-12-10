package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.edit.DefaultDetailEditController;
import dawson.songtracker.front.controllers.edit.LabelDetailEditController;
import dawson.songtracker.front.controllers.searchPanel.LabelSearchController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.front.utils.Loader;

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
