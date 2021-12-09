package dawson.songtracker.utils;

import dawson.songtracker.types.DatabaseObject;

public interface ICrud<Type extends DatabaseObject> {
    void addNewEntry(Type entry) throws Exception;
    void removeEntry(Type entry) throws Exception;
    void updateEntry(Type old, Type entry) throws Exception;
}
