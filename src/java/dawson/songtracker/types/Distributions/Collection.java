package dawson.songtracker.types.Distributions;

import dawson.songtracker.types.Components.Compilation;

import java.util.ArrayList;

public class Collection {
    private final String id;
    private String name;
    private ArrayList<Compilation> compilations;

    public Collection(String id, String name, ArrayList<Compilation> compilations) {
        if (compilations == null) {
            throw new NullPointerException("the compilation array list is null");
        }
        this.id = id;
        this.name = name;
        this.compilations = compilations;
    }

    public ArrayList<Compilation> getCompilation() {
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

    public String getId() {
        return id;
    }
}
