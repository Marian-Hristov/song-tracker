package dawson.songtracker.types.distributions;

import dawson.songtracker.types.Builder;

public class RecordLabelBuilder extends Builder<RecordLabel> {
    private String name;

    public RecordLabelBuilder() {
    }

    public RecordLabelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public RecordLabel build() {
        return new RecordLabel(-1, name);
    }
}
