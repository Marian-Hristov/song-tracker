package dawson.songtracker.controllers.add;

import dawson.songtracker.event.AddContributorEvent;

public class AddContributorController extends SimpleAddPopupController {

    @Override
    public void setName() {
        this.title.setText("Contributors");
    }

    @Override
    public void onAdd() {
        this.fireEvent(new AddContributorEvent(this.textInput.getText()));
    }
}
