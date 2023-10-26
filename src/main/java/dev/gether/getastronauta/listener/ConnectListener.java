package dev.gether.getastronauta.listener;

import dev.gether.getastronauta.GetAstronauta;
import dev.gether.getastronauta.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectListener implements Listener {

    private final GetAstronauta plugin;
    private final UserManager userManager;

    public ConnectListener(GetAstronauta plugin, UserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> userManager.loadUser(player));
    }
}
