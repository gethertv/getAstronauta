package dev.gether.getaustronauta.listener.rune;

import dev.gether.getaustronauta.config.Config;
import dev.gether.getaustronauta.rune.Rune;
import dev.gether.getaustronauta.rune.RuneLevel;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.user.User;
import dev.gether.getaustronauta.user.UserManager;
import org.bukkit.Location;
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
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(event.isCancelled())
            return;

        Block block = event.getBlock();
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
        // get stats for this rune including level player rune
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();
        // value where block will be multiplying
        double multiplyBlock = runeLevel.getValue();

        // get drop
        Collection<ItemStack> drops = block.getDrops(player.getItemInHand());
        for (ItemStack drop : drops) {
            // if material is not on the list then ignore
            if(config.allowMaterial.contains(drop.getType()))
                continue;

            double amount = drop.getAmount() * multiplyBlock;
            drop.setAmount((int) amount);
        }

    }
}
