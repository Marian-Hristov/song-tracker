package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.add.AddMarketController;
import dawson.songtracker.front.controllers.edit.MarketDetailEditController;
import dawson.songtracker.front.controllers.searchPanel.MarketSearchController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.front.utils.Loader;

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
