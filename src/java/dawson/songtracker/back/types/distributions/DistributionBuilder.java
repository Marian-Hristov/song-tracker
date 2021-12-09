package dawson.songtracker.back.types.distributions;

import dawson.songtracker.back.types.Builder;

import java.sql.Date;
import java.util.Calendar;

public class DistributionBuilder extends Builder<Distribution> {
    private Collection collection;
    private RecordLabel label;
    private Market market;

    public DistributionBuilder setCollection(Collection collection) {
        this.collection = collection;
        return this;
    }

    public DistributionBuilder setLabel(RecordLabel label) {
        this.label = label;
        return this;
    }

    public DistributionBuilder setMarket(Market market) {
        this.market = market;
        return this;
    }

    public Distribution build() {
        return new Distribution(-1, collection, new Date(Calendar.getInstance().getTime().getTime()), label, market);
    }
}