package dawson.songtracker.utils;

public interface ICrud<Type> {
    void addNewEntry(Type entry);
    void removeEntry(Type entry);
    void updateEntry(Type entry);
}
