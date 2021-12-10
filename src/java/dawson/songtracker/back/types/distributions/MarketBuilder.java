package dawson.songtracker.back.types.distributions;

import dawson.songtracker.back.types.Builder;

public class MarketBuilder extends Builder<Market> {
    private String name;

    public MarketBuilder() {
    }

    public MarketBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Market build() {
        return new Market(-1, this.name);
    }

}
