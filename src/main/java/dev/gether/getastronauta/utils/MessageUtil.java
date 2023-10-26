package dev.gether.getastronauta.utils;

import dev.gether.getastronauta.GetAstronauta;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Logger;

public class MessageUtil {

    // logger
    private static final Logger LOGGER = GetAstronauta.getInstance().getLogger();

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ColorFixer.addColors(message));
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(ColorFixer.addColors(message));
    }

    public static void sendMessage(CommandSender sender, List<String> message) {
        sender.sendMessage(ColorFixer.addColors(String.join("\n", message)));
    }
    public static void sendMessage(LiteSender sender, String message) {
        sender.sendMessage(ColorFixer.addColors(message));
    }
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ColorFixer.addColors(message));
    }

    public static String joinListToString(List<String> lists) {
        StringBuilder sb = new StringBuilder();

        for (String element : lists) {
            sb.append(element).append("\n");
        }

        return sb.toString();
    }

    public static void sendLoggerInro(String message, String color) {
        LOGGER.info(color + message);
    }
}
