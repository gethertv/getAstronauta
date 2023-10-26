package dev.gether.getaustronauta.database;

import dev.gether.getaustronauta.GetAustronauta;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.utils.ConsoleColor;
import dev.gether.getaustronauta.utils.MessageUtil;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends Database {

    private String database;

    public SQLite(String database) {
        this.database = database;
    }
    @Override
    public void connect() {

        GetAustronauta plugin = GetAustronauta.getInstance();
        File dataFolder = new File(plugin.getDataFolder(), database + ".db");
        // check file with database exists
        // if not exists, then create
        checkDatabaseExists(dataFolder);

        // connecting
        try {
            if (connection != null && !connection.isClosed()) {
                return; // if connecting exists, dont create new connecting
            }

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder.getAbsolutePath());
            MessageUtil.sendLoggerInro("Successfully connected to database!", ConsoleColor.GREEN);

        } catch (SQLException | ClassNotFoundException ex) {
            MessageUtil.sendLoggerInro("Error: "+ex.getMessage(), ConsoleColor.RED);
        }
    }

    private void checkDatabaseExists(File dataFolder) {
        if (!dataFolder.exists()) {
            try {
                if (!dataFolder.createNewFile()) {
                    MessageUtil.sendLoggerInro("Cannot create database!", ConsoleColor.RED);
                }
            } catch (IOException e) {
                MessageUtil.sendLoggerInro("Error: "+e.getMessage(), ConsoleColor.RED);
            }
        }
    }

    @Override
    public void createTable() {
        List<String> temp = new ArrayList<>();
        for (RuneType value : RuneType.values()) {
            temp.add(value.name()+" INT (11) NOT NULL DEFAULT 0");
        }
        String sql = "CREATE TABLE IF NOT EXISTS " + getUserTable() + "( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uuid VARCHAR(100)," +
                "username VARCHAR(100)," +
                String.join(", ", temp)  +
                ")";

        executeQuery(sql);
    }
}
