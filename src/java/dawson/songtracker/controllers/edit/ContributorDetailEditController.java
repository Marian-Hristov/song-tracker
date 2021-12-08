package dawson.songtracker.controllers.edit;

import dawson.songtracker.controllers.edit.DefaultDetailEditController;
import dawson.songtracker.types.roles.Contributor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;

public class ContributorDetailEditController extends DefaultDetailEditController<Contributor> {
    @Override
    HBox arrayListHbox(Method method, Label label) {
        return null;
    }
}
