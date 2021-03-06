package dawson.songtracker.front.controllers.add;

import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.DistributionBuilder;

public class AddDistributionController extends ProceduralAddPopupController<Distribution, DistributionBuilder> {
    public AddDistributionController() {
        super(Distribution.class, new DistributionBuilder());
    }
}
