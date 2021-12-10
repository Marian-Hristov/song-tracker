package dawson.songtracker.back.types.roles;

import dawson.songtracker.back.types.Builder;

public class RoleBuilder extends Builder<Role> {
    private int id;
    private String name;
    private ERoleCategory category;

    public RoleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RoleBuilder setCategory(ERoleCategory category) {
        this.category = category;
        return this;
    }

    public Role build() {
        switch (this.category) {
            case PRODUCTION -> {
                return new ProductionRole(-1, this.name);
            }
            case MUSICIAN -> {
                return new MusicianRole(-1, this.name);
            }
            case COMPILATION -> {
                return new CompilationRole(-1, this.name);
            }
        }
        return null;
    }

}