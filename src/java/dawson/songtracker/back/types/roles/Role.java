package dawson.songtracker.back.types.roles;

import dawson.songtracker.back.types.DatabaseObject;

public abstract class Role extends DatabaseObject {
    protected final int id;
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

    public abstract ERoleCategory getRoleCategory();

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
