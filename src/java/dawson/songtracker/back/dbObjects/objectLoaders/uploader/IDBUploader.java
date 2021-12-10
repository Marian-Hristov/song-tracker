package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.types.DatabaseObject;

public interface IDBUploader<T extends DatabaseObject> {
    void add(T t) throws Exception;
    void update(T t) throws Exception;
    void remove(T t) throws Exception;
}
