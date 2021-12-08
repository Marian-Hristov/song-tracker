package dawson.songtracker.utils;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.event.UpdateTableEvent;

import java.util.ArrayList;
import java.util.List;

public interface ISearchPanelOwner<T> {
    void onSearch(SearchEvent search);
    void onUpdate(UpdateTableEvent event);
    void populateTable(ArrayList<T> info);
}
