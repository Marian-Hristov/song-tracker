package dawson.songtracker.front.event;

import dawson.songtracker.back.types.roles.ERoleCategory;
import javafx.event.Event;
import javafx.event.EventType;

public class AddRoleEvent extends Event {
    public static final EventType<AddRoleEvent> ADD_ROLE_EVENT = new EventType<>(Event.ANY, "ADD_ROLE");

    public final String roleName;
    public final ERoleCategory category;

    public AddRoleEvent(String roleName, ERoleCategory category) {
        super(ADD_ROLE_EVENT);
        this.roleName = roleName;
        this.category = category;
    }
}
