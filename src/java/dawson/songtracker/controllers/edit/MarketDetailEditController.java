package dawson.songtracker.controllers.edit;

import dawson.songtracker.types.distributions.Market;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;

public class MarketDetailEditController extends DefaultDetailEditController<Market> {
    @Override
    HBox arrayListHbox(Method method, Label label, Class c) {
        return null;
    }
}
