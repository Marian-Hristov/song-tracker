package dawson.songtracker.types.roles;

import dawson.songtracker.types.Builder;

public class ContributorBuilder extends Builder<Contributor> {
    private int id = -1;
    private String name;

    public ContributorBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Contributor build() {
        return new Contributor(id, name);
    }

}