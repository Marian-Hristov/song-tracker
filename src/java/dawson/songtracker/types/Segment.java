package dawson.songtracker.types;

public class Segment<T> {
    private String id;
    private Compilation mainTrack;
    private T componentTrack;
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

    public void setId(String id) {
        this.id = id;
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

    public void setComponentTrack(T componentTrack) {
        this.componentTrack = componentTrack;
    }

    public Compilation getMainTrack() {
        return mainTrack;
    }

    public void setMainTrack(Compilation mainTrack) {
        this.mainTrack = mainTrack;
    }
}
