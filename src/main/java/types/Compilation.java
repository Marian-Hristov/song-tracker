package types;

public class Compilation {
    private Segment<Compilation>[] sampledCompilations;
    private Segment<Recording>[] sampledRecordings;

    public Compilation(Segment<Compilation>[] sampledCompilations, Segment<Recording>[] sampledRecordings) {
        this.sampledCompilations = sampledCompilations;
        this.sampledRecordings = sampledRecordings;
    }

    public Segment<Recording>[] getSampledRecordings() {
        return sampledRecordings;
    }

    public void setSampledRecordings(Segment<Recording>[] sampledRecordings) {
        this.sampledRecordings = sampledRecordings;
    }

    public Segment<Compilation>[] getSampledCompilations() {
        return sampledCompilations;
    }

    public void setSampledCompilations(Segment<Compilation>[] sampledCompilations) {
        this.sampledCompilations = sampledCompilations;
    }
}
