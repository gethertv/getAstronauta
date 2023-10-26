package dev.gether.getaustronauta.database;

import dev.gether.getaustronauta.config.DatabaseConfig;

import static dev.gether.getaustronauta.database.TypeDatabase.MYSQL;

public class DatabaseFactory {

    /**
     * Choose the database type from config
     *
     * @param config
     * @return Database
     */
    public static Database createDatabase(DatabaseConfig config) {
        TypeDatabase typeDatabase = config.typeDatabase;
        switch (typeDatabase) {
            case MYSQL:
                return new MySQL(
                        config.host,
                        config.username,
                        config.password,
                        config.database,
                        config.port,
                        config.ssl
                );
            case SQL:
                return new SQLite(config.database);
        }
        throw new IllegalArgumentException("Unsupported database type: " + typeDatabase.name());
    }

}
