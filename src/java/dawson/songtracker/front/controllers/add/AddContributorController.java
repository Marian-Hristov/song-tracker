package dawson.songtracker.front.controllers.add;

import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.ContributorBuilder;

public class AddContributorController extends ProceduralAddPopupController<Contributor, ContributorBuilder> {
    public AddContributorController() {
        super(Contributor.class, new ContributorBuilder());
    }
}
