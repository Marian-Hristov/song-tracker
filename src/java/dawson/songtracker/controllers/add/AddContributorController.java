package dawson.songtracker.controllers.add;

import dawson.songtracker.event.AddContributorEvent;

import java.util.HashMap;
import java.util.Map;

public class AddContributorController extends SimpleAddPopupController {
    @Override
    public void setName() {
        this.title.setText("Contributors");
    }

    public void onAdd() {
        this.fireEvent(new AddContributorEvent(this.textInput.getText()));
    }
}
