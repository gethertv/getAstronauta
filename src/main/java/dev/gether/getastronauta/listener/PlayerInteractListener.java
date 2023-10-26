package dev.gether.getaustronauta.listener;

import dev.gether.getaustronauta.rune.Rune;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class PlayerInteractListener implements Listener {

    private RuneManager runeManager;
    private UserManager userManager;


    public PlayerInteractListener(RuneManager runeManager, UserManager userManager) {
        this.runeManager = runeManager;
        this.userManager = userManager;
    }

    @EventHandler
    public void onInteractPlayer(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        Action action = event.getAction();
        if(action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        // check this item is rune
        // if is, then return object of rune
        Optional<Rune> runeTemp = runeManager.isRune(itemInMainHand);
        if(runeTemp.isPresent()) {

            event.setCancelled(true);
            if(event.getHand()== EquipmentSlot.OFF_HAND)
                return;

            Rune rune = runeTemp.get();
            // use item
            // check the player has max level if not then use
            userManager.useRune(player, rune);
            return;

        }
    }
}
