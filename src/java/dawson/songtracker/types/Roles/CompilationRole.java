package dawson.songtracker.types.Roles;

public class CompilationRole extends Role {
    public CompilationRole(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompilationRole compilationRole)) return false;

        return id == compilationRole.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


}
