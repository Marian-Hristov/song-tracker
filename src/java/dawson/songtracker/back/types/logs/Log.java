package dawson.songtracker.back.types.logs;

import dawson.songtracker.back.types.DatabaseObject;

import java.sql.Timestamp;

public class Log extends DatabaseObject {
    private final int id;
    private final String message;
    private final Timestamp log_time;

    public Log(int id, String message, Timestamp log_time) {
        this.id = id;
        this.message = message;
        this.log_time = log_time;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getLog_time() {
        return log_time;
    }
}
