package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.types.distributions.DistributionBuilder;

public class AddDistributionController extends ProceduralAddPopupController<Distribution, DistributionBuilder> {
    public AddDistributionController() {
        super(Distribution.class, new DistributionBuilder());
    }
}
