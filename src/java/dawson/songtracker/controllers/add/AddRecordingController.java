package dawson.songtracker.controllers.add;

import dawson.songtracker.types.components.Recording;
import dawson.songtracker.types.components.RecordingBuilder;

public class AddRecordingController extends ProceduralAddPopupController<Recording, RecordingBuilder> {
    public AddRecordingController() {
        super(Recording.class, new RecordingBuilder());
    }
}
