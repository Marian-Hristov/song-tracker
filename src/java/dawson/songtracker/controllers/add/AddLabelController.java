package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.types.distributions.RecordLabelBuilder;
import dawson.songtracker.utils.ICrud;

public class AddLabelController extends ProceduralAddPopupController<RecordLabel, RecordLabelBuilder> {
    public AddLabelController() {
        super(RecordLabel.class, new RecordLabelBuilder());
    }
}
