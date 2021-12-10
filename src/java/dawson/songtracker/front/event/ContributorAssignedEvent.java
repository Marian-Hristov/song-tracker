package dawson.songtracker.front.event;

import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import javafx.event.Event;
import javafx.event.EventType;

public class ContributorAssignedEvent extends Event{
    public static final EventType<ContributorAssignedEvent> CONTRIBUTOR_ASSIGNED_EVENT = new EventType<>(Event.ANY, "ASSIGN_CONTRIBUTOR");

    public final Role role;
    public final Contributor contributor;

    public ContributorAssignedEvent(Role role, Contributor contributor) {
        super(CONTRIBUTOR_ASSIGNED_EVENT);
        this.role = role;
        this.contributor = contributor;
    }
}
