package dawson.songtracker.controllers;

import dawson.songtracker.DBObjects.objectLoaders.ObjectUploader;

public class AddRecordController extends AddSongController {

    @Override
    public void onAdd() {
        String name = this.textInput.getText();
        ObjectUploader objectUploader;
        //objectUploader.addRecord(name);
    }

    public void initialize() {
       this.title.setText("Add recording");
    }

}
