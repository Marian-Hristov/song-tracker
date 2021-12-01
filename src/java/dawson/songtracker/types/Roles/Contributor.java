package dawson.songtracker.types.Roles;

public class Contributor {
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
        if (!(o instanceof Contributor that)) return false;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
