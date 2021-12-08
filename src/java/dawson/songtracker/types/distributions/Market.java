package dawson.songtracker.types.distributions;

import dawson.songtracker.types.DatabaseObject;

public class Market extends DatabaseObject {
    private final int id;
    private String name;

    public Market(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Market market)) return false;

        return id == market.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
