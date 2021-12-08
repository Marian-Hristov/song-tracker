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
    public void setCacheUpdateMethod() {
        cache.setUpdateMethod(() -> ObjectDownloader.getInstance().loadAllMarkets());
    }

    @Override
    public void initialize() {
        super.initialize();
//        this.populateTable();
//        searchPanel.displayDefault();
    }

    @Override
    public void addNewEntry(Market entry) {
        try {
            ObjectUploader.getInstance().addMarket(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEntry(Market entry) {
        try {
            ObjectUploader.getInstance().removeMarket(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntry(Market entry) {
        try {
            ObjectUploader.getInstance().updateMarket(entry, entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearch(SearchEvent search) {
        try {
            var markets = ObjectDownloader.getInstance().loadMarketsByName(search.message);
            searchPanel.displaySearchResult(markets);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
