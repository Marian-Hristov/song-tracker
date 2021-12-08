package dawson.songtracker.controllers.add;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddSongController extends SimpleAddPopupController {
    private static HashMap<String, Class> FIELDS = new HashMap<>();
    static {
        FIELDS.put("Name", String.class);
    }


    public AddSongController() {
        super();
    }

    @Override
    public void setName() {
        this.title.setText("Add track");
    }

    public void onAdd() {
        System.out.println("On add not yet implemented.");
    }
}
