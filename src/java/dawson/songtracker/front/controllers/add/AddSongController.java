package dawson.songtracker.front.controllers.add;

import java.util.HashMap;

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
