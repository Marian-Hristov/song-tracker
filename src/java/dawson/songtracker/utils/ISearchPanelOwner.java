package dawson.songtracker.utils;

import dawson.songtracker.event.SearchEvent;
import dawson.songtracker.event.UpdateTableEvent;

public interface ISearchPanelOwner {
    void onSearch(SearchEvent search);
    void onUpdate(UpdateTableEvent event);
    void populateTable();
}
