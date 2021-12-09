package dawson.songtracker.front.utils;

import java.util.ArrayList;

@FunctionalInterface
public interface OnCacheLoad<T> {
    void onCacheLoad(ArrayList<T> l);
}
