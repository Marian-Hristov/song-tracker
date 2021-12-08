package dawson.songtracker.dbObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String username;
    private static String password;
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (username == null || password == null) {
            throw new NullPointerException("the username and/or password are not set");
        }
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca", username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public static void setUsername(String name) {
        username = name;
    }

    public static void setPassword(String pWord) {
        password = pWord;
    }
}
