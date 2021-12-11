package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.controllers.assign.AssignContributorToRecordingController;
import dawson.songtracker.front.controllers.assign.AssignSegmentController;
import dawson.songtracker.front.controllers.assign.ContributorPopupController;
import dawson.songtracker.front.controllers.add.AddSongController;
import dawson.songtracker.front.controllers.detail.CompilationDetailController;
import dawson.songtracker.front.controllers.detail.ContributorRole;
import dawson.songtracker.front.controllers.detail.RecordingDetailController;
import dawson.songtracker.front.controllers.searchPanel.SearchSongController;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.front.event.ContributorAssignedEvent;
import dawson.songtracker.front.messageLogger.Message;
import dawson.songtracker.front.messageLogger.MessageLoggerController;
import dawson.songtracker.front.utils.Loader;
import dawson.songtracker.front.utils.Popup;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RecordingController extends DefaultWithDetailsController
        <Recording, SearchSongController<Recording>, AddSongController, RecordingDetailController>
{

    @FXML
    Popup assignContributor;


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
        this.addEventHandler(ContributorAssignedEvent.CONTRIBUTOR_ASSIGNED_EVENT, this::onContributorAssigned);
    }

    private void onContributorAssigned(ContributorAssignedEvent event) {
        HashMap<Role, ArrayList<Contributor>> map= this.searchPanel.getSelectedRow().getContributorsRoleMap();
        var key = map.get(event.role);
        if (key != null) {
            if (key.contains(event.role)) {
                MessageLoggerController.getInstance().addMessage(new Message("User is already a contributor"));
            } else {
                key.add(event.contributor);
            }
        } else {
            map.put(event.role, new ArrayList<>(
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

    public void onAddContributor() {
        this.assignContributor.show();
    }

    public void onRemoveContributor(ContributorRole cr) {
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

}
