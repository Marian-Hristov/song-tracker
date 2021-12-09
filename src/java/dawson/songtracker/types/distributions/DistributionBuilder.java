package dawson.songtracker.types.distributions;

import dawson.songtracker.types.Builder;

import java.sql.Date;

public class DistributionBuilder extends Builder<Distribution> {
    private Collection collection;
    private Date releaseDate;
    private RecordLabel label;
    private Market market;

    public DistributionBuilder setCollection(Collection collection) {
        this.collection = collection;
        return this;
    }

    public DistributionBuilder setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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
        return new Distribution(-1, collection, releaseDate, label, market);
    }
}