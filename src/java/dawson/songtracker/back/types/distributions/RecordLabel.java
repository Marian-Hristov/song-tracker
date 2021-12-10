package dawson.songtracker.back.types.distributions;

import dawson.songtracker.back.types.DatabaseObject;

public class RecordLabel extends DatabaseObject {
    private final int id;
    private String name;

    public RecordLabel(int id, String name) {
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
        if (!(o instanceof RecordLabel that)) return false;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
