package dawson.songtracker.front.controllers.add;

import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.back.types.distributions.RecordLabelBuilder;

public class AddLabelController extends ProceduralAddPopupController<RecordLabel, RecordLabelBuilder> {
    public AddLabelController() {
        super(RecordLabel.class, new RecordLabelBuilder());
    }
}
