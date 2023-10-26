package dev.gether.getaustronauta.listener;

import dev.gether.getaustronauta.inv.SpinInvHolder;
import dev.gether.getaustronauta.spin.SpinManager;
import dev.gether.getaustronauta.utils.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCloseListener implements Listener {


    private SpinManager spinManager;

    public InventoryCloseListener(SpinManager spinManager) {
        this.spinManager = spinManager;
    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        InventoryHolder holder = event.getInventory().getHolder();
        if(!(holder instanceof SpinInvHolder))
            return;

        SpinInvHolder spinInventory = (SpinInvHolder) holder;

        if(!spinInventory.isFinish() && !spinInventory.isCancel())
        {
            spinInventory.cancel();
            ItemUtil.giveItem(player, spinManager.getRandomItem());
        }
    }

}
