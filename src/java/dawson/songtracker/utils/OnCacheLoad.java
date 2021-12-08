package dawson.songtracker.utils;

import java.util.ArrayList;

@FunctionalInterface
public interface OnCacheLoad<T> {
    void onCacheLoad(ArrayList<T> l);
}
