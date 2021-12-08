package dawson.songtracker.controllers.add;

import dawson.songtracker.types.distributions.RecordLabel;
import dawson.songtracker.utils.ICrud;

public class AddLabelController extends SimpleAddPopupController{
    @Override
    public void setName() {
        this.title.setText("Add Label");
    }

    public void onAdd() {
        if (this.getParent() instanceof ICrud) {
            ((ICrud<RecordLabel>) this.getParent()).addNewEntry(new RecordLabel(-1, this.textInput.getText()));
        }
    }
}
