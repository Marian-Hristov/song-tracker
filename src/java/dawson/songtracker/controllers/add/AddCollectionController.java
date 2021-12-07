package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.utils.ICrud;

import java.util.ArrayList;

public class AddCollectionController extends SimpleAddPopupController {
    @Override
    public void setName() {
        this.title.setText("Add Collection");
    }

    @Override
    public void onAdd() {
        if (this.getParent() instanceof ICrud) {
            var collection = new Collection(-1, textInput.getText(), new ArrayList<>(), new ArrayList<>());
            ((ICrud<Collection>) this.getParent()).addNewEntry(collection);

        }
    }
}
