package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.utils.ICrud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCollectionController extends SimpleAddPopupController {

    public AddCollectionController() {
    }

    @Override
    public void setName() {
        this.title.setText("Add Collection");
    }

    public void onAdd() {
        if (this.getParent() instanceof ICrud) {
            var collection = new Collection(-1, textInput.getText(), new ArrayList<>(), new ArrayList<>());
            ((ICrud<Collection>) this.getParent()).addNewEntry(collection);

        }
    }
}
