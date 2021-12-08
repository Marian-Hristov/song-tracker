package dawson.songtracker.controllers.paneControllers;


import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.detail.CompilationDetailController;
import dawson.songtracker.controllers.add.AddSongController;
import dawson.songtracker.controllers.searchPanel.SearchSongController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.utils.*;

import java.sql.SQLException;

public class CompilationController extends DefaultWithDetailsController
        <Compilation, SearchSongController, AddSongController, CompilationDetailController>
{
    public CompilationController() {
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        super.initialize();
        this.populateTable();
        searchPanel.setLabel("Compilation");
    }

    @Override
    public void onSearch(SearchEvent event) {
        try {
            if (event.message.isEmpty()) {
                this.searchPanel.displayDefault();
            } else {
                var matches = ObjectDownloader.getInstance().loadCompilationsByName(event.message);
                this.searchPanel.displaySearchResult(matches);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Searched for in compilation " + event.message);
    }

    @Override
    public void populateTable() {
        try {
            var objects = ObjectDownloader.getInstance().loadFirstCompilations(10);
            this.searchPanel.populateTable(objects);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addNewEntry(Compilation comp) {
        try {
            ObjectUploader.getInstance().addCompilation(comp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEntry(Compilation entry) {
        try {
            ObjectUploader.getInstance().deleteCompilation(entry.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntry(Compilation entry) {
        //ObjectUploader.getInstance().updateCompilation(entry, );
    }
}
