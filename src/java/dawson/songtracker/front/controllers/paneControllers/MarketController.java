package dawson.songtracker.front.controllers.paneControllers;

import dawson.songtracker.front.controllers.add.AddMarketController;
import dawson.songtracker.front.controllers.edit.MarketDetailEditController;
import dawson.songtracker.front.controllers.searchPanel.MarketSearchController;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.front.utils.Loader;

import java.util.ArrayList;

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
        this.cache.setUpdateMethod(()-> (ArrayList<Market>) Downloader.getInstance().getLoader(Market.class).loadAll());
    }

    @Override
    public void initialize() {
        super.initialize();
//        this.populateTable();
//        searchPanel.displayDefault();
    }
}
