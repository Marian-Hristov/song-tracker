package dawson.songtracker.front.controllers.paneControllers;


import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.controllers.assign.AssignSegmentController;
import dawson.songtracker.front.controllers.detail.CompilationDetailController;
import dawson.songtracker.front.controllers.add.AddSongController;
import dawson.songtracker.front.controllers.detail.ContributorRole;
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
    AssignSegmentController assignSegment;

    public CompilationController() {
        super(Compilation.class);
        Loader.LoadAndSet(this);
    }

    public void initialize() {
        super.initialize();
        searchPanel.setLabel("Compilation");
        this.addEventHandler(ContributorAssignedEvent.CONTRIBUTOR_ASSIGNED_EVENT, this::onContributorAssigned);
    }

    private void onContributorAssigned(ContributorAssignedEvent event) {
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
            this.assignContributor.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setCacheUpdateMethod() {
        this.cache.setUpdateMethod(()-> (ArrayList<Compilation>) Downloader.getInstance().getLoader(Compilation.class).loadAll());
    }


    public void onAddContributor() {
        this.assignContributor.show();
    }

    public void onAddSegment() {
        this.assignSegment.show(this.searchPanel.getSelectedRow());
    }

    public void onRemoveContributor(ContributorRole cr) {
        if (cr == null) return;
        var recording = this.searchPanel.getSelectedRow();

        var roleMap = recording.getContributorsRoleMap();
        var array = roleMap.get(cr.role());
        array.remove(cr.contributor());
        recording.setContributions(roleMap);

        try {
            this.updateEntry(recording, recording);
            this.detailPane.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRemoveSegment(Segment segment) {
        if (segment == null) return;
        var compilation = searchPanel.getSelectedRow();
        var a = compilation.getSampledCompilations();
        var b = compilation.getSampledRecordings();

        a.remove(segment);
        b.remove(segment);

        compilation.setSampledCompilations(a);
        compilation.setSampledRecordings(b);

        try {
            this.updateEntry(compilation, compilation);
            this.detailPane.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
