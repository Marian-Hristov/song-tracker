package dawson.songtracker.controllers;

import dawson.songtracker.DBObjects.objectLoaders.ObjectUploader;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class AddSongController extends Popup {
   @FXML
   protected Label title;


   @FXML
   protected TextField textInput;

   public AddSongController() {
      Loader.LoadAndSet(this);
   }

   @FXML
   abstract public void onAdd();

}
