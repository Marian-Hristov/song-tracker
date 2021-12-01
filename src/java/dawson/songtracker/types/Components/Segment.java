package dawson.songtracker.types.Components;

<<<<<<< HEAD:src/java/dawson/songtracker/types/compilations_samples/Segment.java
public class Segment<T extends SongComponent> {
    private final String id;
=======
public class Segment<T extends SongComponent > {
    private final int id;
>>>>>>> 6f16a1d1542452be9323fe69b19507173588d7f4:src/java/dawson/songtracker/types/Components/Segment.java
    private final Compilation mainTrack;
    private final T componentTrack;
    private int mainTrackOffset;
    private int durationInMainTrack;
    private int componentTrackOffset;
    private int durationOfComponent;

    public Segment(int id, Compilation mainTrack, T componentTrack, int mainTrackOffset, int durationInMainTrack, int componentTrackOffset, int durationOfComponent) {
        this.id = id;
        this.mainTrack = mainTrack;
        this.componentTrack = componentTrack;
        this.mainTrackOffset = mainTrackOffset;
        this.durationInMainTrack = durationInMainTrack;
        this.componentTrackOffset = componentTrackOffset;
        this.durationOfComponent = durationOfComponent;
    }

    public int getId() {
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
