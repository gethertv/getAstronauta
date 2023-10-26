package dev.gether.getastronauta.hook;

import dev.gether.getastronauta.GetAstronauta;
import dev.gether.getastronauta.rune.RuneManager;
import dev.gether.getastronauta.user.UserManager;
import dev.gether.getastronauta.utils.ConsoleColor;
import dev.gether.getastronauta.utils.MessageUtil;
import org.bukkit.plugin.Plugin;

public class HookManager {

    private boolean getClanHooks = false;

    public HookManager(GetAstronauta plugin, UserManager userManager, RuneManager runeManager) {
        Plugin getClan = plugin.getServer().getPluginManager().getPlugin("getClan");
        if(getClan!=null) {
            MessageUtil.sendLoggerInro("Implementacja pluginu getClan", ConsoleColor.GREEN);
            getClanHooks = true;
            // register event listener
            plugin.getServer().getPluginManager().registerEvents(new ClanChangePointsListener(userManager, runeManager), plugin);
        }
    }

    public boolean isGetClanHooks() {
        return getClanHooks;
    }
}
