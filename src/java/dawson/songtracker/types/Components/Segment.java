package dawson.songtracker.types.Components;

public class Segment<T extends SongComponent> {
    private final int id;
    private final Compilation mainTrack;
    private final T componentTrack;
    private double mainTrackOffset;
    private double durationInMainTrack;
    private double componentTrackOffset;
    private double durationOfComponentUsed;

    public Segment(int id, Compilation mainTrack, T componentTrack, double mainTrackOffset, double durationInMainTrack, double componentTrackOffset, double durationOfComponent) {
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

    public double getDurationOfComponentUsed() {
        return durationOfComponentUsed;
    }

    public void setDurationOfComponentUsed(double durationOfComponentUsed) {
        this.durationOfComponentUsed = durationOfComponentUsed;
    }

    public double getComponentTrackOffset() {
        return componentTrackOffset;
    }

    public void setComponentTrackOffset(double componentTrackOffset) {
        this.componentTrackOffset = componentTrackOffset;
    }

    public double getDurationInMainTrack() {
        return durationInMainTrack;
    }

    public void setDurationInMainTrack(double durationInMainTrack) {
        this.durationInMainTrack = durationInMainTrack;
    }

    public double getMainTrackOffset() {
        return mainTrackOffset;
    }

    public void setMainTrackOffset(double mainTrackOffset) {
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
