package dawson.songtracker.controllers.add;

import dawson.songtracker.types.Builder;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.types.distributions.MarketBuilder;
import dawson.songtracker.utils.ICrud;

public class AddMarketController extends ProceduralAddPopupController<Market, MarketBuilder>{
    public AddMarketController() {
        super(Market.class, new MarketBuilder());
    }

}
