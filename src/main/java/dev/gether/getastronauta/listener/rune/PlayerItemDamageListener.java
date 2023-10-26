package dev.gether.getaustronauta.listener.rune;

import dev.gether.getaustronauta.rune.Rune;
import dev.gether.getaustronauta.rune.RuneLevel;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.user.User;
import dev.gether.getaustronauta.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.Optional;
import java.util.Random;

public class PlayerItemDamageListener implements Listener {

    private Random random;
    private UserManager userManager;
    private RuneManager runeManager;

    public PlayerItemDamageListener(UserManager userManager, RuneManager runeManager) {
        this.random = new Random(System.currentTimeMillis());
        this.userManager = userManager;
        this.runeManager = runeManager;
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        // player
        Player player = event.getPlayer();
        // user
        Optional<User> userTemp = userManager.getUser(player.getUniqueId());
        if(userTemp.isEmpty())
            return;

        User user = userTemp.get();
        int actuallyLevel = user.getActuallyLevel(RuneType.DURABILITY);
        // if rune of durability is 0 then dont do anything
        if(actuallyLevel==0) return;

        // get rune
        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.DURABILITY);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        // get rune by user level
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();
        double value = runeLevel.getValue();
        double winTick = random.nextDouble() * 100;
        // if winTick is lower than chance then cancel event and not take a durability
        if(winTick <= value) {
            event.setCancelled(true);
        }
    }
}
