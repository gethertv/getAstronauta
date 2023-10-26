package dev.gether.getastronauta.rune;

import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.config.RuneConfig;
import dev.gether.getastronauta.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;


public class RuneManager {


    private final RuneConfig runeConfig;
    private final Config config;

    public RuneManager(RuneConfig runeConfig, Config config) {
        this.runeConfig = runeConfig;
        this.config = config;
    }

    // get object Rune by Type of rune
    public Optional<Rune> findRuneByType(RuneType runeType) {
        Rune rune = runeConfig.runes.get(runeType);
        return Optional.ofNullable(rune);
    }

    // get max level to achieve by type runes
    public Optional<Integer> findMaxLevelRuneByType(RuneType runeType) {
        Rune rune = runeConfig.runes.get(runeType);
        return rune.maxLevel(runeType);
    }

    public Optional<Rune> isRune(ItemStack itemStack) {
        for (Rune rune : runeConfig.runes.values()) {
            // check item is similar that rune
            if (itemStack.isSimilar(rune.getItemStack())) {
                return Optional.of(rune);
            }
        }
        return Optional.empty();
    }


    public void setNewCoinItem(Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if(itemInMainHand==null || itemInMainHand.getType()== Material.AIR) {
            MessageUtil.sendMessage(player, "&cMusisz trzymac item w lapce!");
            return;
        }
        config.itemCoins = itemInMainHand;
        MessageUtil.sendMessage(player, "&aPomyslnie ustawiono nowa walute astronauty!");
    }

    public void giveRune(Player player, RuneType runeType, int amount) {
        Optional<Rune> runeTemp = findRuneByType(runeType);
        if(!runeTemp.isPresent()) {
            MessageUtil.sendMessage(player, "&cPodana runa nie istnieje!");
            return;
        }
        Rune rune = runeTemp.get();
        // copy itemstack the rune and change amount
        ItemStack clone = rune.getItemStack().clone();
        clone.setAmount(amount);

        player.getInventory().addItem(clone);
        MessageUtil.sendMessage(player, "&aPomyslnie nadano rune");
    }


}
