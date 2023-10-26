package dev.gether.getastronauta.database;

import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.utils.ConsoleColor;
import dev.gether.getastronauta.utils.MessageUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database {

    private String userTable = "getastronauta_user";
    protected Connection connection;
    public abstract void connect();
    public abstract void createTable();

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void executeQuery(String sql, Object... params) {
        connection = getConnection();
        try {
            if (connection!=null && !connection.isClosed()) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    // fill args to query
                    for (int i = 0; i < params.length; i++) {
                        preparedStatement.setObject(i + 1, params[i]);
                    }

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ensureAllColumnsExist() {
        DatabaseMetaData metaData;
        List<String> missingColumns = new ArrayList<>();

        try {
            metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, getUserTable(), null);

            List<String> existingColumns = new ArrayList<>();
            // get all columns from database
            while (columns.next()) {
                existingColumns.add(columns.getString("COLUMN_NAME"));
            }


            for (RuneType value : RuneType.values()) {
                if (!existingColumns.contains(value.name())) {
                    missingColumns.add(value.name());
                }
            }

            // add missing column
            for (String missingColumn : missingColumns) {
                String addColumnSQL = "ALTER TABLE " + getUserTable() + " ADD " + missingColumn + " INT(11) NOT NULL DEFAULT 0";
                executeQuery(addColumnSQL);
            }

            columns.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            MessageUtil.sendLoggerInro("Błąd podczas sprawdzania połączenia z bazą danych! "+e.getMessage(), ConsoleColor.RED);
        }
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserTable() {
        return userTable;
    }
}
