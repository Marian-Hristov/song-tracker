package dawson.songtracker.types.roles;

public class MusicianRole extends Role {
    public MusicianRole(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicianRole musicianRole)) return false;

        return id == musicianRole.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
