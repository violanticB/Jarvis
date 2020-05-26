package controller.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {

    private Connection connection;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    /*
     * Methods
     */

    public SQLManager(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        openConnection();
    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Getters
     */

    public Connection getConnection() {
        if (connection == null) {
            openConnection();
            return connection;
        }

        try {
            if (connection.isClosed()) {
                openConnection();
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
