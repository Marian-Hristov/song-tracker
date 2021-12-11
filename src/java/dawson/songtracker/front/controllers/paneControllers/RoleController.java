package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.edit.RoleDetailEditController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.front.controllers.add.AddRoleController;
import dawson.songtracker.front.controllers.searchPanel.RoleSearchController;
import dawson.songtracker.front.event.AddRoleEvent;
import dawson.songtracker.front.event.UpdateTableEvent;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.utils.Loader;
import javafx.fxml.FXML;

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

            var mr =  (ArrayList<MusicianRole>)Downloader.getInstance().getLoader(MusicianRole.class).loadAll();
            var cr =  (ArrayList<CompilationRole>)Downloader.getInstance().getLoader(CompilationRole.class).loadAll();
            var pr =  (ArrayList<ProductionRole>)Downloader.getInstance().getLoader(ProductionRole.class).loadAll();

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
