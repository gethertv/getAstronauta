package dev.gether.getastronauta.config;

import dev.gether.getastronauta.database.TypeDatabase;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class DatabaseConfig extends OkaeriConfig {

    @Comment("Database type [MYSQL / SQLITE]")
    public TypeDatabase typeDatabase = TypeDatabase.SQL;
    @Comment("if your database its MYSQL, fill out under field")
    public String host = "localhost";
    public String username = "root";
    public String password = "";

    public String database = "database";
    public int port = 3306;
    public boolean ssl = false;

}
