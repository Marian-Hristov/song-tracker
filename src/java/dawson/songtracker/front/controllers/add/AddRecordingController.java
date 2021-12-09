package dawson.songtracker.front.controllers.add;


import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.RecordingBuilder;

public class AddRecordingController extends ProceduralAddPopupController<Recording, RecordingBuilder> {
    public AddRecordingController() {
        super(Recording.class, new RecordingBuilder());
    }
}
