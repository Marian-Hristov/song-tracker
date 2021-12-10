package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.back.types.roles.CompilationRole;

import java.util.ArrayList;
import java.util.Arrays;

public class AssignContributorToCompilationController extends AssignContributorController{
    public AssignContributorToCompilationController() {
        super(
                new ArrayList<>(
                        Arrays.asList(CompilationRole.class)
                )
        );

    }
}
