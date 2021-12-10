package dawson.songtracker.back.types.roles;

public class ProductionRole extends Role {
    public ProductionRole(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionRole productionRole)) return false;

        return id == productionRole.id;
    }

    @Override
    public ERoleCategory getRoleCategory() {
        return ERoleCategory.PRODUCTION;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
