package dawson.songtracker.types.recordings_contributions;

public abstract class Role {
    protected int id;
    protected String name;

    public Role(int id, String name) {
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
        if (!(o instanceof Role role)) return false;

        return id == role.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
