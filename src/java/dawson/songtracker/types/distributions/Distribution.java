package dawson.songtracker.types.distributions;


import dawson.songtracker.types.DatabaseObject;

import java.sql.Date;

public class Distribution extends DatabaseObject {
    private int id;
    private Collection collection;
    private Date releaseDate;
    private RecordLabel label;
    private Market market;

    public Distribution(int id, Collection collection, Date releaseDate, RecordLabel label, Market market) {
        if (collection == null) {
            throw new NullPointerException("the collection is null");
        }
        if (releaseDate == null) {
            throw new NullPointerException("the releaseDate is null");
        }
        if (label == null) {
            throw new NullPointerException("the label is null");
        }
        if (market == null) {
            throw new NullPointerException("the market is null");
        }
        this.id = id;
        this.collection = collection;
        this.releaseDate = releaseDate;
        this.label = label;
        this.market = market;
    }

    public void setLabel(RecordLabel label) {
        this.label = label;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public int getId() {
        return id;
    }

    public void setFinalId(int id) {
        this.id = id;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setFinalCollection(Collection collection) {
        this.collection = collection;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setFinalReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public RecordLabel getLabel() {
        return label;
    }

    public Market getMarket() {
        return market;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distribution distribution)) return false;

        return id == distribution.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return  label + "-" + market + "-" + releaseDate;
    }
}
