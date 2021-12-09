package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.types.distributions.CollectionBuilder;
import dawson.songtracker.utils.ICrud;

import java.util.ArrayList;

public class AddCollectionController extends ProceduralAddPopupController<Collection, CollectionBuilder>{

    public AddCollectionController() {
        super(Collection.class, new CollectionBuilder());
    }
}
