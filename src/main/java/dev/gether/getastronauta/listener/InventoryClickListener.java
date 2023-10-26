package dev.gether.getaustronauta.listener;

import dev.gether.getaustronauta.config.Config;
import dev.gether.getaustronauta.inv.AstronautaInvHolder;
import dev.gether.getaustronauta.inv.SpinInvHolder;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.spin.SpinManager;
import dev.gether.getaustronauta.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements Listener {

    private Config config;
    private UserManager userManager;
    private SpinManager spinManager;

    public InventoryClickListener(Config config, UserManager userManager, SpinManager spinManager) {
        this.config = config;
        this.userManager = userManager;
        this.spinManager = spinManager;
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        if(holder==null) return;

        Player player = (Player) event.getWhoClicked();
        if(holder instanceof SpinInvHolder) {
            event.setCancelled(true);
            return;
        }
        if(holder instanceof AstronautaInvHolder) {
            event.setCancelled(true);

            // clicked inv
            Inventory clickedInventory = event.getClickedInventory();
            if(clickedInventory==null) return;

            // if clicked inv is a astronauta inv then do action
            if(clickedInventory.getHolder()!=null && clickedInventory.getHolder() instanceof AstronautaInvHolder) {
                // clicked slot
                int slot = event.getSlot();

                // UPGRADE RANK BOOST
                if(config.slotRank == slot) {
                    boolean successfullyBuy = userManager.canUpgradeTheRune(player, RuneType.BOOST_POINTS);
                    updateInv(successfullyBuy, clickedInventory);
                    return;
                }

                // UPGRADE THE BOOST DROP
                if(config.slotDrop == slot) {
                    boolean successfullyBuy = userManager.canUpgradeTheRune(player, RuneType.BOOST_DROP);
                    updateInv(successfullyBuy, clickedInventory);
                    return;
                }

                // DRAW RUNES
                if(config.slotDrawRune == slot) {
                    spinManager.drawRune(player);
                    return;
                }
            }

        }
    }

    private void updateInv(boolean successfullyBuy, Inventory inventory) {
        if(!successfullyBuy)
            return;

        // update the inv
        AstronautaInvHolder invAstronauta = (AstronautaInvHolder) inventory.getHolder();
        invAstronauta.fillInventory();

    }
}
