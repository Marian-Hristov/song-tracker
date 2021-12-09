package dawson.songtracker.types.distributions;

import dawson.songtracker.types.Builder;
import dawson.songtracker.types.components.Compilation;

import java.util.ArrayList;

public class CollectionBuilder extends Builder<Collection> {
    private int id = -1;
    private String name;
    private ArrayList<Compilation> compilations;
    private ArrayList<Collection> collectionsInSet;

    public CollectionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CollectionBuilder setCompilations(ArrayList<Compilation> compilations) {
        this.compilations = compilations;
        return this;
    }

    public CollectionBuilder setCollectionsInSet(ArrayList<Collection> collectionsInSet) {
        this.collectionsInSet = collectionsInSet;
        return this;
    }

    public Collection build() {
        return new Collection(id, name, compilations, collectionsInSet);
    }

}