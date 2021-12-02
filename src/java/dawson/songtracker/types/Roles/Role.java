package dawson.songtracker.types.Roles;

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

    public abstract boolean equals(Object o);
}
