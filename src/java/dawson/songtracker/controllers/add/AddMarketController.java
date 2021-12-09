package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.Market;
import dawson.songtracker.utils.ICrud;

public class AddMarketController extends SimpleAddPopupController {
    @Override
    public void setName() {
        this.title.setText("Add Market");
    }

    public void onAdd() {
        if (this.getParent() instanceof ICrud) {
            ((ICrud<Market>) this.getParent()).addNewEntry(new Market(-1, this.textInput.getText()));
        }
    }

}
