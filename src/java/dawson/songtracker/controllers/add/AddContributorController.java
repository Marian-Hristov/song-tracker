package dawson.songtracker.controllers.add;

import dawson.songtracker.types.roles.Contributor;
import dawson.songtracker.types.roles.ContributorBuilder;

public class AddContributorController extends ProceduralAddPopupController<Contributor, ContributorBuilder> {
    public AddContributorController() {
        super(Contributor.class, new ContributorBuilder());
    }
}
