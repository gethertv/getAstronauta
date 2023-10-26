package dev.gether.getaustronauta.inv;

import dev.gether.getaustronauta.config.Config;
import dev.gether.getaustronauta.config.RuneConfig;
import dev.gether.getaustronauta.utils.ColorFixer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SpinInvHolder implements InventoryHolder {

    private boolean finish;
    private Inventory inventory;
    private boolean cancel;
    public SpinInvHolder(Config config, RuneConfig runeConfig)
    {
        this.inventory = Bukkit.createInventory(
                this,
                27,
                ColorFixer.addColors(config.titleSpinInv));

        // fill background items for this inventory
        fillBackground(config);

        this.finish = false;
        this.cancel = false;

    }

    private void fillBackground(Config config) {
        for (Config.BackgroundItem bgItem : config.spinBackground) {
            for (Integer slot : bgItem.slots) {
                inventory.setItem(slot, bgItem.item);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public boolean isFinish() {
        return finish;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void cancel() {
        cancel = true;
    }

}
