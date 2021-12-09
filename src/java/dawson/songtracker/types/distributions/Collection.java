package dawson.songtracker.types.distributions;

import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.types.components.Compilation;

import java.util.ArrayList;
import java.util.List;

public class Collection extends DatabaseObject {
    private final int id;
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

    public void setCollectionsInSet(ArrayList<Collection> collectionsInSet) {
        if(collectionsInSet == null) throw new NullPointerException("the  is null");
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
        return name;
    }
}
