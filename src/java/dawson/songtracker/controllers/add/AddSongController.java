package dawson.songtracker.controllers.add;

public class AddSongController extends SimpleAddPopupController {
    @Override
    public void setName() {
        this.title.setText("Add track");
    }

    @Override
    public void onAdd() {
        System.out.println("On add not yet implemented.");
    }
}
