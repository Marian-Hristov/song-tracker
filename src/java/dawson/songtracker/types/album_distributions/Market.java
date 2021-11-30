package dawson.songtracker.types.album_distributions;

public class Market {
    private final String id;
    private String name;

    public Market(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
