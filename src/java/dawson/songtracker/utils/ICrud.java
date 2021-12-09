package dawson.songtracker.utils;

import dawson.songtracker.types.DatabaseObject;

public interface ICrud<Type extends DatabaseObject> {
    void addNewEntry(Type entry);
    void removeEntry(Type entry);
    void updateEntry(Type old, Type entry);
}
