package dawson.songtracker.types.Roles;

public class ProductionRole extends Role {
    public ProductionRole(int id, String name) {
        super(id, name);
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
