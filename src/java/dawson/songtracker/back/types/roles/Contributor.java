package dawson.songtracker.back.types.roles;

import dawson.songtracker.back.types.DatabaseObject;

public class Contributor extends DatabaseObject {
    private final int id;
    private String name;

    public Contributor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contributor contributor)) return false;

        return id == contributor.id;
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
