package dawson.songtracker.types.Distributions;

import dawson.songtracker.types.Components.Compilation;

import java.util.ArrayList;

public class Collection {
    private final int id;
    private String name;
    private ArrayList<Compilation> compilations;
    private final ArrayList<Collection> collectionsInSet;

    public Collection(int id, String name, ArrayList<Compilation> compilations, ArrayList<Collection> collectionsInSet) {
        if (compilations == null) {
            throw new NullPointerException("the compilation array list is null");
        }
        this.id = id;
        this.name = name;
        this.compilations = compilations;
        this.collectionsInSet = collectionsInSet;
    }

    public ArrayList<Compilation> getCompilations() {
        return compilations;
    }

    public void setCompilation(ArrayList<Compilation> compilations) {
        if (compilations == null) {
            throw new NullPointerException("the compilation array list is null");
        }
        this.compilations = compilations;
    }

    public void addCompilation(Compilation compilation) {
        if (compilation == null) {
            throw new NullPointerException("The compilation is null");
        }
        this.compilations.add(compilation);
    }

    public void removeCompilation(Compilation compilation) {
        if (compilation == null) {
            throw new NullPointerException("The compilation is null");
        }
        this.compilations.remove(compilation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("The name is null");
        }
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collection collection)) return false;

        return id == collection.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
