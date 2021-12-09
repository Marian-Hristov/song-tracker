package dawson.songtracker.controllers.paneControllers;

import dawson.songtracker.controllers.add.AddMarketController;
import dawson.songtracker.controllers.edit.MarketDetailEditController;
import dawson.songtracker.controllers.searchPanel.MarketSearchController;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.dbObjects.objectLoaders.uploader.ObjectUploader;
import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.utils.Loader;

import java.sql.SQLException;

public class MarketController extends DefaultWithDetailsController<Market, MarketSearchController, AddMarketController, MarketDetailEditController>
{

    public MarketController() {
        super(Market.class);
        Loader.LoadAndSet(this);
    }

    @Override
    public void onPopupClicked() {
        super.onPopupClicked();
    }

    @Override
    public void setCacheUpdateMethod() {
        cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllMarkets());
    }

    @Override
    public void initialize() {
        super.initialize();
//        this.populateTable();
//        searchPanel.displayDefault();
    }
}
