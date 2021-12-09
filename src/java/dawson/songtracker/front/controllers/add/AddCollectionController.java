package dawson.songtracker.front.controllers.add;

import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.CollectionBuilder;

public class AddCollectionController extends ProceduralAddPopupController<Collection, CollectionBuilder>{

    public AddCollectionController() {
        super(Collection.class, new CollectionBuilder());
    }
}
