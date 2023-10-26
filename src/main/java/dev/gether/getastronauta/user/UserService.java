package dev.gether.getastronauta.user;

import dev.gether.getastronauta.GetAstronauta;
import dev.gether.getastronauta.config.RuneConfig;
import dev.gether.getastronauta.database.Database;
import dev.gether.getastronauta.rune.RuneType;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserService {

    private final GetAstronauta plugin;
    private Database database;
    private String userTable;
    public UserService(GetAstronauta plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }
    public Optional<User> loadUser(Player player, RuneConfig runeConfig) {
        // user object
        User user = null;
        // query
        String selectSql = "SELECT * FROM " + database.getUserTable() +" WHERE uuid = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setString(1, player.getUniqueId().toString());
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                // if user exists
                if (resultSet.next()) {
                    // all runes player
                    HashMap<RuneType, Integer> runeData = new HashMap<>();
                    for (RuneType runeType : runeConfig.runes.keySet()) {
                        int level = resultSet.getInt(runeType.name());
                        runeData.put(runeType, level);
                    }
                    // create user object with data from database
                    user = new User(runeData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // if not exists in database
        if (user == null) {
            // insert to database new user
            createUser(player);
            user = new User(runeConfig);
        }

        return Optional.ofNullable(user);
    }

    // only insert user into database
    public void createUser(Player player) {
        String insertSql = "INSERT INTO " + database.getUserTable() +" (uuid, username) VALUES (?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, player.getUniqueId().toString());
            insertStatement.setString(2, player.getName());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserTable() {
        return userTable;
    }

    public void updateUser(User user, Player player) {

        // if runes is empty  | not update
        if(RuneType.values().length==0) return;

        List<String> args = new ArrayList<>();
        String runes = "";
        for (RuneType runeType : RuneType.values()) {
            int actuallyLevel = user.getActuallyLevel(runeType);
            // add argument to replace in query
            args.add(String.valueOf(actuallyLevel));

            // add SET column_name to update in database
            runes += runeType.name()+" = ?, ";
        }
        // add username argument
        args.add(player.getName());
        // add uuid argument
        args.add(player.getUniqueId().toString());


        String sql = "UPDATE " + database.getUserTable() +" SET " + runes + " username = ? WHERE uuid = ?";
        database.executeQuery(sql, args.toArray());

    }
}
