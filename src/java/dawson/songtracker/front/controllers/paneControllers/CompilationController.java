package dawson.songtracker.front.controllers.paneControllers;


import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.front.controllers.detail.CompilationDetailController;
import dawson.songtracker.front.controllers.add.AddSongController;
import dawson.songtracker.front.controllers.searchPanel.SearchSongController;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.front.utils.Loader;

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
