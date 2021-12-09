package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.edit.RoleDetailEditController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.controllers.add.AddRoleController;
import dawson.songtracker.controllers.searchPanel.RoleSearchController;
import dawson.songtracker.event.AddRoleEvent;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.event.UpdateTableEvent;
import dawson.songtracker.types.roles.CompilationRole;
import dawson.songtracker.types.roles.MusicianRole;
import dawson.songtracker.types.roles.ProductionRole;
import dawson.songtracker.types.roles.Role;
import dawson.songtracker.utils.ICrud;
import dawson.songtracker.utils.ISearchPanelOwner;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.PopupOwner;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoleController extends DefaultWithDetailsController
    <Role, RoleSearchController, AddRoleController, RoleDetailEditController>
{

    @FXML
    private RoleSearchController searchPanel;

    @FXML
    private AddRoleController addPanel;

    public RoleController() {
        super(Role.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void setCacheUpdateMethod() {
        cache.setUpdateMethod(() -> {
            var mr =  ObjectDownloader.getInstance().loadAllMusicianRoles();
            var cr =  ObjectDownloader.getInstance().loadAllCompilationRoles();
            var pr = ObjectDownloader.getInstance().loadAllProductionRoles();

            // Sub optimal
            ArrayList<Role> all = new ArrayList<>();
            all.addAll(mr);
            all.addAll(cr);
            all.addAll(pr);
            return all;
        });
    }

    public void initialize() {
        super.initialize();
        this.addPanel.addEventHandler(AddRoleEvent.ADD_ROLE_EVENT, this::onAddRole);
    }

    private void onAddRole(AddRoleEvent event) {
        try {
            switch (event.category) {
                case MUSICIAN -> addNewEntry(new MusicianRole(12391, event.roleName));
                case PRODUCTION -> addNewEntry(new ProductionRole(12391, event.roleName));
                case COMPILATION -> addNewEntry(new CompilationRole(12391, event.roleName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(UpdateTableEvent event) {
        this.cache.update();
        this.searchPanel.displayDefault();
    }

}
