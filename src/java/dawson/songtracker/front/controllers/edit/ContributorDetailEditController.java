package dawson.songtracker.front.controllers.edit;

import dawson.songtracker.front.Cache;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.SongComponent;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class ContributorDetailEditController extends DefaultDetailEditController<Contributor> {

    private record RoleSong(Role role, SongComponent songComponent){};

    @Override
    public void show(Contributor entity) {
        super.show(entity);



        this.addMoreNodes(createTable());

    }

    private TableView<RoleSong> createTable() {
        TableView<RoleSong> tbData = new TableView<>();
        TableColumn<RoleSong, String> songName = new TableColumn<>("Recording/Compilation name");
        TableColumn<RoleSong, String> role = new TableColumn<>("Role");

        songName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().songComponent.getName()));
        role.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().role.getName()));


        tbData.getColumns().addAll(songName, role);
        tbData.setItems(FXCollections.observableList(getAllRoles()));

        return tbData;
    }

    private ArrayList<RoleSong> getAllRoles() {
        Cache<Compilation> compilationCache = CacheManager.getCache(Compilation.class);
        Cache<Recording> recordingCache = CacheManager.getCache(Recording.class);

        ArrayList<RoleSong> matches = new ArrayList<>();

        compilationCache
                .getCachedItems()
                .forEach(compilation -> compilation.
                        getContributions()
                        .forEach((role, contributors) -> {
                            if (contributors.contains(entity)) {
                                matches.add(new RoleSong(role, compilation));
                            }
                        }));

        recordingCache
                .getCachedItems()
                .forEach(recording -> {
                    recording.getMusicalContributions()
                            .forEach((role, contributors) -> {

                                if (contributors.contains(entity)) {
                                    matches.add(new RoleSong(role, recording));
                                }
                            });
                    recording.getProductionContributions()
                            .forEach((role, contributors) -> {
                                if (contributors.contains(entity)) {
                                    matches.add(new RoleSong(role, recording));
                                }
                            });

                }
                );

        return matches;

    }
}
