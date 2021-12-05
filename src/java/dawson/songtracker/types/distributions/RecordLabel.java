package dawson.songtracker.types.distributions;

public class RecordLabel {
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
}
