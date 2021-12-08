package dawson.songtracker.controllers.edit;

import dawson.songtracker.Cache;
import dawson.songtracker.CacheManager;
import dawson.songtracker.controllers.paneControllers.DistributionController;
import dawson.songtracker.event.UpdateEntityEvent;
import dawson.songtracker.types.distributions.Distribution;
import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.utils.ComboBoxCheckBuilder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DistributionDetailEditController extends DefaultDetailEditController<Distribution> {
}
