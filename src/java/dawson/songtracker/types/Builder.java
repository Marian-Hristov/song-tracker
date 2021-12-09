package dawson.songtracker.types;

public abstract class Builder<T extends DatabaseObject> {
    public abstract T build();
}
