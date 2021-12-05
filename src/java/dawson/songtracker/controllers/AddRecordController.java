package dawson.songtracker.controllers;

import dawson.songtracker.DBObjects.objectLoaders.ObjectUploader;
import dawson.songtracker.types.Components.Compilation;

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
