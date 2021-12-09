package dawson.songtracker.front.controllers.add;

import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.MarketBuilder;

public class AddMarketController extends ProceduralAddPopupController<Market, MarketBuilder>{
    public AddMarketController() {
        super(Market.class, new MarketBuilder());
    }

}
