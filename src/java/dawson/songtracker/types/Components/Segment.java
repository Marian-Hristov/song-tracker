package dawson.songtracker.types.Components;

public class Segment<T extends SongComponent> {
    private final int id;
    private final Compilation mainTrack;
    private final T componentTrack;
    private int mainTrackOffset;
    private int durationInMainTrack;
    private int componentTrackOffset;
    private int durationOfComponentUsed;

    public Segment(int id, Compilation mainTrack, T componentTrack, int mainTrackOffset, int durationInMainTrack, int componentTrackOffset, int durationOfComponent) {
        if(mainTrack == null){
            throw new NullPointerException("the mainTrack is null");
        }
        if(componentTrack == null){
            throw new NullPointerException("The componentTrack is null");
        }
        this.id = id;
        this.mainTrack = mainTrack;
        this.componentTrack = componentTrack;
        this.mainTrackOffset = mainTrackOffset;
        this.durationInMainTrack = durationInMainTrack;
        this.componentTrackOffset = componentTrackOffset;
        this.durationOfComponentUsed = durationOfComponent;
    }

    public int getId() {
        return id;
    }

    public int getDurationOfComponentUsed() {
        return durationOfComponentUsed;
    }

    public void setDurationOfComponentUsed(int durationOfComponentUsed) {
        this.durationOfComponentUsed = durationOfComponentUsed;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Segment<?> segment)) return false;

        return id == segment.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
