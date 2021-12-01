package dawson.songtracker.types.compilations_samples;

import dawson.songtracker.types.SongComponent;
import dawson.songtracker.types.compilations_samples.Compilation;

public class Segment<T extends SongComponent > {
    private final String id;
    private final Compilation mainTrack;
    private final T componentTrack;
    private int mainTrackOffset;
    private int durationInMainTrack;
    private int componentTrackOffset;
    private int durationOfComponent;

    public Segment(String id, Compilation mainTrack, T componentTrack, int mainTrackOffset, int durationInMainTrack, int componentTrackOffset, int durationOfComponent) {
        this.id = id;
        this.mainTrack = mainTrack;
        this.componentTrack = componentTrack;
        this.mainTrackOffset = mainTrackOffset;
        this.durationInMainTrack = durationInMainTrack;
        this.componentTrackOffset = componentTrackOffset;
        this.durationOfComponent = durationOfComponent;
    }

    public String getId() {
        return id;
    }

    public int getDurationOfComponent() {
        return durationOfComponent;
    }

    public void setDurationOfComponent(int durationOfComponent) {
        this.durationOfComponent = durationOfComponent;
    }

    public int getComponentTrackOffset() {
        return componentTrackOffset;
    }

    public void setComponentTrackOffset(int componentTrackOffset) {
        this.componentTrackOffset = componentTrackOffset;
    }

    public int getDurationInMainTrack() {
        return durationInMainTrack;
    }

    public void setDurationInMainTrack(int durationInMainTrack) {
        this.durationInMainTrack = durationInMainTrack;
    }

    public int getMainTrackOffset() {
        return mainTrackOffset;
    }

    public void setMainTrackOffset(int mainTrackOffset) {
        this.mainTrackOffset = mainTrackOffset;
    }

    public T getComponentTrack() {
        return componentTrack;
    }

    public Compilation getMainTrack() {
        return mainTrack;
    }
}
