package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.Cache;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.controllers.searchPanel.SearchPanelController;
import dawson.songtracker.back.dbObjects.objectLoaders.uploader.IDBUploader;
import dawson.songtracker.back.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.front.event.UpdateTableEvent;
import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.front.messageLogger.Message;
import dawson.songtracker.front.messageLogger.MessageLoggerController;
import dawson.songtracker.front.utils.ICrud;
import dawson.songtracker.front.utils.ISearchPanelOwner;
import dawson.songtracker.front.utils.Popup;
import dawson.songtracker.front.utils.PopupOwner;
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
    private IDBUploader<Type> uploader;


    public DefaultController(Class<Type> t) {
        cache = CacheManager.getCache(t);
        setCacheUpdateMethod();
        cache.subscribe(arg -> this.populateTable(arg));
        cache.update();

        try {
            uploader = (IDBUploader<Type>) ObjectUploader.getInstance().getUploader(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.cache.update();
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

    @Override
    public void addNewEntry(Type entry) throws Exception{
        this.uploader.add(entry);
        this.cache.update();
        this.addPanel.hide();
        MessageLoggerController.getInstance().addMessage(
                new Message("Added new entry. Fetching from database...")
        );
    }

    @Override
    public void removeEntry(Type entry) throws Exception{
        if(entry!=null)
        this.uploader.remove(entry);
        this.cache.update();
        MessageLoggerController.getInstance().addMessage(
                new Message("Removed entry. Fetching from database...")
        );
    }

    @Override
    public void updateEntry(Type old, Type entry) throws Exception {
        this.uploader.update(entry);
        this.addPanel.hide();
        this.cache.update();
        MessageLoggerController.getInstance().addMessage(
                new Message("Updated entry. Fetching from database...")
        );
    }
}
