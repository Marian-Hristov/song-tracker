package dawson.songtracker.front.utils;

import dawson.songtracker.back.types.DatabaseObject;

public interface ICrud<Type extends DatabaseObject> {
    void addNewEntry(Type entry) throws Exception;
    void removeEntry(Type entry) throws Exception;
    void updateEntry(Type old, Type entry) throws Exception;
}
