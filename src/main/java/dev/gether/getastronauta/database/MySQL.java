package dev.gether.getaustronauta.database;

import dev.gether.getaustronauta.GetAustronauta;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.utils.ConsoleColor;
import dev.gether.getaustronauta.utils.MessageUtil;
import org.bukkit.Bukkit;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySQL extends Database {

    private String host;
    private String username;
    private String password;
    private String database;
    private int port;
    private boolean ssl;

    public MySQL(String host, String username, String password, String database, int port, boolean ssl) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
        this.ssl = ssl;
    }

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);
            properties.setProperty("autoReconnect", "true");
            properties.setProperty("useSSL", String.valueOf(ssl));
            properties.setProperty("requireSSL", String.valueOf(ssl));
            properties.setProperty("verifyServerCertificate", "false");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            connection = DriverManager.getConnection(url, properties);

            MessageUtil.sendLoggerInro("Successfully connected to database!", ConsoleColor.GREEN);

        } catch (ClassNotFoundException | SQLException e) {
            MessageUtil.sendLoggerInro("Error: "+e.getMessage(), ConsoleColor.RED);
            Bukkit.getPluginManager().disablePlugin(GetAustronauta.getInstance());
        }
    }

    @Override
    public void createTable() {
        List<String> temp = new ArrayList<>();
        for (RuneType value : RuneType.values()) {
            temp.add(value.name()+" INT (11) NOT NULL DEFAULT 0");
        }
        String sql = "CREATE TABLE IF NOT EXISTS " + getUserTable() + "( " +
                "id INT(10) AUTO_INCREMENT PRIMARY KEY," +
                "uuid VARCHAR(100)," +
                "username VARCHAR(100)," +
                String.join(", ", temp)  +
                ")";

        executeQuery(sql);
    }
}
