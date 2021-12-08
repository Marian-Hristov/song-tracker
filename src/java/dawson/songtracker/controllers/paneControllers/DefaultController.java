package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.Cache;
import dawson.songtracker.CacheManager;
import dawson.songtracker.controllers.searchPanel.SearchPanelController;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.event.UpdateTableEvent;
import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.utils.ICrud;
import dawson.songtracker.utils.ISearchPanelOwner;
import dawson.songtracker.utils.Popup;
import dawson.songtracker.utils.PopupOwner;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Represents a basic controller that will be able to CRUD and contains
 * a search panel.
 * @param <Type>
 */
public abstract class DefaultController<
        Type extends DatabaseObject,
        SearchControllerType extends SearchPanelController,
        AddControllerType extends Popup
        > extends Pane
    implements ICrud<Type>, ISearchPanelOwner<Type>, PopupOwner {
    
    protected Cache<Type> cache;

    public DefaultController(Class<Type> t) {
        cache = CacheManager.getCache(t);
        setCacheUpdateMethod();
        cache.subscribe(arg -> this.populateTable(arg));
        cache.update();
    }

    @Override
    public void populateTable(ArrayList<Type> t) {
        System.out.println("populated.");
        this.searchPanel.populateTable(t);
        this.searchPanel.displayDefault();
    }

    abstract public void setCacheUpdateMethod();

    public void initialize() {
        this.addListeners();
    }

    @FXML
    protected SearchControllerType searchPanel;

    @FXML
    protected AddControllerType addPanel;

    @Override
    public void onUpdate(UpdateTableEvent event) {
        this.searchPanel.displayDefault();
    }

    private void addListeners() {
        this.searchPanel.addEventHandler(SearchEvent.SEARCH_EVENT, this::onSearch);
        this.searchPanel.addEventHandler(UpdateTableEvent.UPDATE_TABLE_EVENT, this::onUpdate);
    }

    @Override
    public void onPopupClicked() {
        this.addPanel.show();
    }


}
