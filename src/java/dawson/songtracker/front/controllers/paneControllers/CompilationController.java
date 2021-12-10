package dawson.songtracker.front.controllers.paneControllers;


import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.controllers.detail.CompilationDetailController;
import dawson.songtracker.front.controllers.add.AddSongController;
import dawson.songtracker.front.controllers.searchPanel.SearchSongController;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.front.event.ContributorAssignedEvent;
import dawson.songtracker.front.messageLogger.Message;
import dawson.songtracker.front.messageLogger.MessageLoggerController;
import dawson.songtracker.front.utils.Loader;
import dawson.songtracker.front.utils.Popup;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CompilationController extends DefaultWithDetailsController
        <Compilation, SearchSongController<Compilation>, AddSongController, CompilationDetailController>
{
    @FXML
    Popup assignContributor;

    @FXML
    Popup assignSegment;

    public CompilationController() {
        super(Compilation.class);
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        super.initialize();
        searchPanel.setLabel("Compilation");
        this.addEventHandler(ContributorAssignedEvent.CONTRIBUTOR_ASSIGNED_EVENT, event -> {
            HashMap<CompilationRole, ArrayList<Contributor>> map= this.searchPanel.getSelectedRow().getContributorsRoleMap();
            var key = map.get(event.role);
            if (key != null) {
                if (key.contains(event.role)) {
                    MessageLoggerController.getInstance().addMessage(new Message("User is already a contributor"));
                } else {
                    key.add(event.contributor);
                }
            } else {
                map.put((CompilationRole) event.role, new ArrayList<>(
                        Arrays.asList(event.contributor)
                ));
            }

            this.searchPanel.getSelectedRow().setContributions(map);

            try {
                this.updateEntry(this.searchPanel.getSelectedRow(), this.searchPanel.getSelectedRow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllCompilations());
    }


    public void onAddContributor() {
        this.assignContributor.show();
    }

    public void onAddSegment() {
        this.assignSegment.show();
    }


}
