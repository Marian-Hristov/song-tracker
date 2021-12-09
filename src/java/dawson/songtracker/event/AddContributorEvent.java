package dawson.songtracker.event;

import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.ContributorBuilder;
import javafx.event.Event;
import javafx.event.EventType;

public class AddContributorEvent extends Event {
    public static final EventType<AddContributorEvent> ADD_CONTRIBUTOR_EVENT= new EventType<>(Event.ANY, "ADD_CONTRIBUTOR");

    public final Contributor contributor;

    public AddContributorEvent(String name) {
        super(ADD_CONTRIBUTOR_EVENT);
        this.contributor = new ContributorBuilder().setName(name).build();
    }
}
