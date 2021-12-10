package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.detail.DetailPopupController;
import dawson.songtracker.front.controllers.searchPanel.SearchPanelController;
import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.front.event.UpdateTableEvent;
import dawson.songtracker.front.utils.IDetailedInfo;
import dawson.songtracker.front.utils.Popup;
import dawson.songtracker.front.utils.PopupOwner;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class DefaultWithDetailsController<
        Type extends DatabaseObject,
        SearchControllerType extends SearchPanelController,
        AddControllerType extends Popup,
        DetailControllerType extends DetailPopupController>
    extends DefaultController<Type, SearchControllerType, AddControllerType>
    implements PopupOwner, IDetailedInfo<Type>
{
    @FXML
    DetailControllerType detailPane;

    public DefaultWithDetailsController(Class<Type> t) {
        super(t);
    }

    public void showDetailedInfo(Type entry) {
        this.detailPane.show(entry);
    }

    @Override
    public void onSearch(SearchEvent search) {
        ArrayList<Type> collection = (ArrayList<Type>) cache.getCachedItems();

        if (search.message.isEmpty()) {
            searchPanel.filterAndDisplay(collection);
            return;
        }
            collection = (ArrayList<Type>) collection
                    .stream()
                    .filter(c -> c.toString().toLowerCase().contains(search.message.toLowerCase()))
                    .collect(Collectors.toList());

            searchPanel.filterAndDisplay((ArrayList) collection);
    }

    @FXML
    public void onAdd() {
        System.out.println("here.");
        this.addPanel.show();
    }

    @Override
    public void updateEntry(Type old, Type entry) throws Exception {
        super.updateEntry(old, entry);
        this.detailPane.hide();
    }
}
