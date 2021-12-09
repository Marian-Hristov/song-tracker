package dawson.songtracker.dbObjects.objectLoaders.uploader;

import dawson.songtracker.types.DatabaseObject;

public interface IDBUploader<T extends DatabaseObject> {
    void add(T t);
    void update(T t);
    void remove(T t);
}
