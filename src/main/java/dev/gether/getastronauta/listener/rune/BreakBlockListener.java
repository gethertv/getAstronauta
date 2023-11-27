package dev.gether.getastronauta.listener.rune;

import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.rune.Rune;
import dev.gether.getastronauta.rune.RuneLevel;
import dev.gether.getastronauta.rune.RuneManager;
import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.user.User;
import dev.gether.getastronauta.user.UserManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Optional;

public class BreakBlockListener implements Listener {

    private Config config;
    private UserManager userManager;
    private RuneManager runeManager;

    public BreakBlockListener(Config config, UserManager userManager, RuneManager runeManager) {
        this.config = config;
        this.userManager = userManager;
        this.runeManager = runeManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(event.isCancelled())
            return;

        Block block = event.getBlock();

        // if material is not on the list then ignore
        if(!config.allowMaterial.contains(block.getType()))
            return;

//        getGenerator
//        List<MetadataValue> generator = block.getMetadata("generator");
//        if(generator.isEmpty())
//            return;

        // get user object
        Optional<User> userTemp = userManager.getUser(player.getUniqueId());
        if(userTemp.isEmpty())
            return;

        User user = userTemp.get();
        // get actually level rune of boost drop
        int actuallyLevel = user.getActuallyLevel(RuneType.BOOST_DROP);
        // if level = 0 then ignore/return
        if(actuallyLevel==0) return;

        // get rune object boost drop
        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.BOOST_DROP);
        if(runeByType.isEmpty())
            return;

        // object rune - boost drop
        Rune rune = runeByType.get();
        // check rune is enabled if not than return
        if(!rune.isEnable())
            return;

        // get stats for this rune including level player rune
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();
        // value where block will be multiplying
        double multiplyBlock = runeLevel.getValue();

        // get drop
        Collection<ItemStack> drops = block.getDrops(player.getItemInHand());

        // next delete this (SET AIR) and cancel the event
        event.setCancelled(true);
        block.setType(Material.AIR);

        for (ItemStack drop : drops) {
            double amount = drop.getAmount() * multiplyBlock;
            drop.setAmount((int) amount);

            block.getLocation().getWorld().dropItemNaturally(block.getLocation(), drop);
        }

    }
}
