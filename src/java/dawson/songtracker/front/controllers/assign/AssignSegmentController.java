package dawson.songtracker.front.controllers.assign;

import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.components.SongComponent;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.controllers.paneControllers.CollectionController;
import dawson.songtracker.front.controllers.paneControllers.CompilationController;
import dawson.songtracker.front.utils.Loader;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AssignSegmentController extends AssignPopupController {
    @FXML ChoiceBox<SongComponent> componentTrack;
    @FXML TextField mainOffset;
    @FXML TextField mainDuration;
    @FXML TextField componentOffset;
    @FXML TextField componentDuration;

    Compilation compilation;

    public AssignSegmentController(){
        Loader.LoadAndSet(this);
    }

    private void populateComponentTrack() {
        ArrayList<SongComponent> sc = new ArrayList<>();
        sc.addAll(CacheManager.getCompilations().getCachedItems());
        sc.addAll(CacheManager.getRecordings().getCachedItems());

        componentTrack.getItems().setAll(sc);
        componentTrack.setValue(sc.get(1));
    }

    @Override
    public void onAdd(){
        var parent = ((CompilationController) getParent());


        var selected = componentTrack.getSelectionModel().getSelectedItem();

        if (selected instanceof Compilation) {
            var segment = new Segment<Compilation>(-1,
                    compilation.getId(),
                    (Compilation) selected,
                    Double.parseDouble(mainOffset.getText()),
                    Double.parseDouble(mainDuration.getText()),
                    Double.parseDouble(componentOffset.getText()),
                    Double.parseDouble(componentDuration.getText())
            );
            var old = compilation.getSampledCompilations();
            old.add(segment);

            compilation.setSampledCompilations(old);

        } else if (selected instanceof Recording) {
            var segment = new Segment<Recording>(-1,
                    compilation.getId(),
                    (Recording) selected,
                    Double.parseDouble(mainOffset.getText()),
                    Double.parseDouble(mainDuration.getText()),
                    Double.parseDouble(componentOffset.getText()),
                    Double.parseDouble(componentDuration.getText())
            );

            var old = compilation.getSampledRecordings();
            old.add(segment);

            compilation.setSampledRecordings(old);
        }

        try {
            parent.updateEntry(compilation, compilation);
            this.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void show(Compilation compilation) {
        this.compilation = compilation;
        populateComponentTrack();
        this.show();
    }

}
