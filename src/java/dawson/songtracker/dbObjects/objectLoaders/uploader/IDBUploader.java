package dawson.songtracker.dbObjects.objectLoaders.uploader;

import dawson.songtracker.types.DatabaseObject;

public interface IDBUploader<T extends DatabaseObject> {
    void add(T t) throws Exception;
    void update(T t) throws Exception;
    void remove(T t) throws Exception;
}
