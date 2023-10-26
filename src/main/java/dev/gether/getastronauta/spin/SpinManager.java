package dev.gether.getaustronauta.spin;

import dev.gether.getaustronauta.GetAustronauta;
import dev.gether.getaustronauta.config.Config;
import dev.gether.getaustronauta.config.LangConfig;
import dev.gether.getaustronauta.config.RuneConfig;
import dev.gether.getaustronauta.inv.SpinInvHolder;
import dev.gether.getaustronauta.rune.Rune;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.utils.ItemUtil;
import dev.gether.getaustronauta.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SpinManager {

    private Random random;
    private Config config;
    private RuneConfig runeConfig;
    private LangConfig langConfig;

    public SpinManager(Config config, RuneConfig runeConfig, LangConfig langConfig) {
        this.random = new Random(System.currentTimeMillis());
        this.config = config;
        this.runeConfig = runeConfig;
        this.langConfig = langConfig;
    }

    public void drawRune(Player player) {

        // counting amount of coins
        int count = ItemUtil.calcItem(player, config.itemCoins);
        // if not has enough then send message about wrong amount
        if(count < config.costDrawing) {
            MessageUtil.sendMessage(player, langConfig.noCoins);
            return;
        }
        // remove the item
        ItemUtil.removeItem(player, config.itemCoins, config.costDrawing);

        // create inv for the draw
        SpinInvHolder spinInventory = new SpinInvHolder(config, runeConfig);
        player.openInventory(spinInventory.getInventory());

        // start draw
        spin(player, spinInventory, 2, 1);
    }

    public void spin(Player player, SpinInvHolder spinInventory, int ticksPassed, double speedCopy)
    {
        Inventory inventory = spinInventory.getInventory();
        if(spinInventory.isCancel()) return;
        if(spinInventory.isFinish())
        {
            // if not finish final the animation just give the random rune
            ItemUtil.giveItem(player, getRandomItem());
            return;
        }

        if (ticksPassed >= 100) {
            spinInventory.cancel();
            new BukkitRunnable() {

                @Override
                public void run() {
                    ItemUtil.giveItem(player, inventory.getItem(13));
                    player.closeInventory();
                }
            }.runTaskLater(GetAustronauta.getInstance(), 40L);
            return;
        }
        int tick = ticksPassed;
        new BukkitRunnable() {
            double speed = speedCopy;
            int ticks = tick;
            @Override
            public void run() {

                for (int i = 9; i < 17; i++) {
                    inventory.setItem(i, inventory.getItem(i + 1));
                }

                inventory.setItem(17, getRandomItem());

                ticks += 2;

                if(ticks > 86)
                {
                    speed*=1.5;
                } else {
                    speed*=1.02;
                }
                spin(player, spinInventory, ticks, speed);
            }
        }.runTaskLater(GetAustronauta.getInstance(), (int) speedCopy);
    }

    public ItemStack getRandomItem(){
        List<Rune> runes = runeConfig.runes.values().stream().toList();
        Rune rune = runes.get(random.nextInt(runes.size()));
        if(rune.getRuneType() == RuneType.BOOST_DROP || rune.getRuneType() == RuneType.BOOST_POINTS) {
            return getRandomItem();
        }
        return rune.getItemStack();
    }
}
