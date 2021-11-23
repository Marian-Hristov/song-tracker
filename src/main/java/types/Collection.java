package types;

public class Collection {
    private String id;
    private String name;
    private Compilation[] compilation;

    public Collection(String id, String name, Compilation[] compilation) {
        this.id = id;
        this.name = name;
        this.compilation = compilation;
    }

    public Compilation[] getCompilation() {
        return compilation;
    }

    public void setCompilation(Compilation[] compilation) {
        this.compilation = compilation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
