package dawson.songtracker;

import dawson.songtracker.DatabaseCallback;
import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.utils.OnCacheLoad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cache<T extends DatabaseObject> {
    private boolean wasModified;
    private List<T> cachedItems = new ArrayList<>();

    private DatabaseCallback<T> updateMethod;
    private ArrayList<OnCacheLoad<T>> subscribedMethods = new ArrayList<>();
    private boolean isLoaded = false;

    public void update() {
        if (updateMethod == null ) {
            System.out.println("Update methodis not set for this cache.");
        }

        Thread t = new Thread(() -> {
            try {
                final ArrayList<T> objects = this.updateMethod.run();
                cachedItems = objects;
                isLoaded = true;
                subscribedMethods.forEach(c -> c.onCacheLoad(objects));
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
        t.start();
    }

    public boolean isWasModified() {
        return wasModified;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

    public List<T> getCachedItems() {
        return cachedItems;
    }

    public void setCachedItems(List<T> cachedItems) {
        this.cachedItems = cachedItems;
    }

    public void setUpdateMethod(DatabaseCallback<T> l) {
        this.updateMethod = l;
    }

    public void subscribe(OnCacheLoad<T> method) {
        subscribedMethods.add(method);
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}

