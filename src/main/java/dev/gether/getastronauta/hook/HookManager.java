package dev.gether.getastronauta.hook;

import dev.gether.getastronauta.GetAstronauta;
import dev.gether.getastronauta.rune.RuneManager;
import dev.gether.getastronauta.user.UserManager;
import dev.gether.getastronauta.utils.ConsoleColor;
import dev.gether.getastronauta.utils.MessageUtil;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HookManager {

    private boolean getClanHooks = false;

    public HookManager(GetAstronauta plugin, UserManager userManager, RuneManager runeManager) {
        Plugin getClan = plugin.getServer().getPluginManager().getPlugin("getClan");
        if(getClan!=null) {
            String version = getClan.getDescription().getVersion();
            // check version is over or = 1.1.3
            if(!isCurrentVersion(version)) {
                MessageUtil.sendLoggerInro("Plugin getClan posiada za stara wersje! "+version, ConsoleColor.RED);
                return;
            }

            MessageUtil.sendLoggerInro("Implementacja pluginu getClan", ConsoleColor.GREEN);
            getClanHooks = true;
            // register event listener
            plugin.getServer().getPluginManager().registerEvents(new ClanChangePointsListener(userManager, runeManager), plugin);
        }
    }

    private boolean isCurrentVersion(String version) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)(?:\\.(\\d+))?");
        Matcher matcher = pattern.matcher(version);

        if (matcher.matches()) {
            int major = Integer.parseInt(matcher.group(1));
            int minor = Integer.parseInt(matcher.group(2));
            int patch = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;

            int compareResult = compareVersions(major, minor, patch, 1, 1, 3);
            if (compareResult >= 0) {
                return true;
            }
        }
        return false;
    }

    public static int compareVersions(int major1, int minor1, int patch1, int major2, int minor2, int patch2) {
        if (major1 != major2) {
            return major1 - major2;
        } else if (minor1 != minor2) {
            return minor1 - minor2;
        } else {
            return patch1 - patch2;
        }
    }

    public boolean isGetClanHooks() {
        return getClanHooks;
    }
}
