package dawson.songtracker.controllers.paneControllers;


import dawson.songtracker.controllers.edit.CompilationDetailEditController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.detail.CompilationDetailController;
import dawson.songtracker.controllers.add.AddSongController;
import dawson.songtracker.controllers.searchPanel.SearchSongController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.utils.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompilationController extends DefaultWithDetailsController
        <Compilation, SearchSongController, AddSongController, CompilationDetailController>
{
    public CompilationController() {
        super(Compilation.class);
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        super.initialize();
        searchPanel.setLabel("Compilation");
    }

    @Override
    public void populateTable(ArrayList<Compilation> objects) {
            this.searchPanel.populateTable(objects);
    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllCompilations());
    }

}
