package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.detail.DetailPopupController;
import dawson.songtracker.controllers.searchPanel.SearchPanelController;
import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.utils.IDetailedInfo;
import dawson.songtracker.utils.Popup;
import dawson.songtracker.utils.PopupOwner;
import javafx.fxml.FXML;

import java.util.ArrayList;

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


}
