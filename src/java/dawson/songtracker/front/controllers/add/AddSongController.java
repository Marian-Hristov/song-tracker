package dawson.songtracker.front.controllers.add;

import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.CompilationBuilder;


public class AddSongController extends ProceduralAddPopupController<Compilation, CompilationBuilder> {
    public AddSongController() {
        super(Compilation.class, new CompilationBuilder());
    }
}
