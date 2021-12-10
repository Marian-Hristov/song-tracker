package dawson.songtracker.back.types.components;

import dawson.songtracker.back.types.Builder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class CompilationBuilder extends Builder<Compilation> {
    private int id = -1;
    private String name;

    public CompilationBuilder setName(String name) {
        this.name = name;
        return this;
    }
    @Override
    public Compilation build() {
        return new Compilation(id, name, new Timestamp(0), 0, false, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }
}