package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;

import java.util.ArrayList;
import java.util.Arrays;

public class AssignContributorToRecordingController extends AssignContributorController {
    public AssignContributorToRecordingController() {
        super(new ArrayList<>(
                Arrays.asList(
                        ProductionRole.class,
                        MusicianRole.class
                )
        ));
    }
}
