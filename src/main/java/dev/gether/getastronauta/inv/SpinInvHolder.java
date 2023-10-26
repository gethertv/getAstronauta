package dev.gether.getastronauta.inv;

import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.config.RuneConfig;
import dev.gether.getastronauta.utils.ColorFixer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

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
