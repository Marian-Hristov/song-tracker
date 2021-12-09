package dawson.songtracker.front.utils;

import dawson.songtracker.front.event.SearchEvent;
import dawson.songtracker.front.event.UpdateTableEvent;

import java.util.ArrayList;

public interface ISearchPanelOwner<T> {
    void onSearch(SearchEvent search);
    void onUpdate(UpdateTableEvent event);
    void populateTable(ArrayList<T> info);
}
