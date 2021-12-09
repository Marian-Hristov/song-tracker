package dawson.songtracker.types.components;

public class SegmentBuilder<T extends SongComponent> {
    private int id = -1;
    private int mainTrackId;
    private T componentTrack;
    private double mainTrackOffset;
    private double durationInMainTrack;
    private double componentTrackOffset;
    private double durationOfComponent;

    public SegmentBuilder setMainTrackId(int mainTrackId) {
        this.mainTrackId = mainTrackId;
        return this;
    }

    public SegmentBuilder setComponentTrack(T componentTrack) {
        this.componentTrack = componentTrack;
        return this;
    }

    public SegmentBuilder setMainTrackOffset(double mainTrackOffset) {
        this.mainTrackOffset = mainTrackOffset;
        return this;
    }

    public SegmentBuilder setDurationInMainTrack(double durationInMainTrack) {
        this.durationInMainTrack = durationInMainTrack;
        return this;
    }

    public SegmentBuilder setComponentTrackOffset(double componentTrackOffset) {
        this.componentTrackOffset = componentTrackOffset;
        return this;
    }

    public SegmentBuilder setDurationOfComponent(double durationOfComponent) {
        this.durationOfComponent = durationOfComponent;
        return this;
    }

    public Segment createSegment() {
        return new Segment(id, mainTrackId, componentTrack, mainTrackOffset, durationInMainTrack, componentTrackOffset, durationOfComponent);
    }
}