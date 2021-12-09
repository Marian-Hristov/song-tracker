package dawson.songtracker.types.distributions;

import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.types.components.Compilation;

import java.util.ArrayList;
import java.util.List;

public class Collection extends DatabaseObject {
    private int id;
    private String name;
    private List<Compilation> compilations;
    private ArrayList<Collection> collectionsInSet;

    public Collection(int id, String name, ArrayList<Compilation> compilations, ArrayList<Collection> collectionsInSet) {
        if (compilations == null) {
            throw new NullPointerException("the compilation array list is null");
        }
        this.id = id;
        this.name = name;
        this.compilations = compilations;
        this.collectionsInSet = collectionsInSet;
    }

    public List<Compilation> getCompilations() {
        return compilations;
    }

    public ArrayList<Collection> getCollectionsInSet() {
        return collectionsInSet;
    }

    public void setFinalCollectionsInSet(ArrayList<Collection> collectionsInSet) {
        this.collectionsInSet = collectionsInSet;
    }

    public int getTracks() {
        return compilations.size();
    }

    public void setCompilations(List<Compilation> compilations) {
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

    public void setFinalId(int id) {
        this.id = id;
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
        return name;
    }
}
