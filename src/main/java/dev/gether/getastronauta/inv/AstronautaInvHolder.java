package dev.gether.getastronauta.inv;

import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.config.LangConfig;
import dev.gether.getastronauta.config.RuneConfig;
import dev.gether.getastronauta.rune.Rune;
import dev.gether.getastronauta.rune.RuneLevel;
import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.user.User;
import dev.gether.getastronauta.utils.ColorFixer;
import dev.gether.getastronauta.utils.ConsoleColor;
import dev.gether.getastronauta.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AstronautaInvHolder implements InventoryHolder {

    private Inventory inventory;
    private User user;
    private Config config;
    private RuneConfig runeConfig;
    private LangConfig langConfig;

    public AstronautaInvHolder(Player player, User user, Config config, RuneConfig runeConfig, LangConfig langConfig) {
        this.user = user;
        this.config = config;
        this.runeConfig = runeConfig;
        this.langConfig = langConfig;

        // create inv
        inventory = Bukkit.createInventory(this, config.invSize, ColorFixer.addColors(config.title));

        // set items
        fillInventory();

        // open inv
        player.openInventory(inventory);
    }

    public void fillInventory() {
        // fill background
        fillBackground(config);

        // set item where in lore is set active runes
        setItemWithRunes(user, runeConfig, config, langConfig);


        // set item with upgrade the perk (rune) drop
        setItemPerk(user, config, RuneType.BOOST_DROP, config.slotDrop);
        // set item with upgrade the perk (rune) rank
        setItemPerk(user, config, RuneType.BOOST_POINTS, config.slotRank);
        // set item with drawing the rune
        setItemDraw(config);
    }

    private void setItemDraw(Config config) {
        inventory.setItem(config.slotDrawRune, config.drawItem);
    }

    private void setItemPerk(User user, Config config, RuneType runeType, int slot) {
        Rune rune = runeConfig.runes.get(runeType);
        // if its disabled then return
        if(!rune.isEnable())
            return;

        // actually level rune of boost rank
        int actuallyLevel = user.getActuallyLevel(runeType);
        // get item for this level and set in the inventory
        Map<Integer, ItemStack> itemLevels = config.itemTypeLevel.get(runeType);
        ItemStack itemStack = itemLevels.get(actuallyLevel);
        // when item not exists for this level perk
        if(itemStack==null) {
            MessageUtil.sendLoggerInro("Nie ustawion przedmiotu dla runy "+runeType.name()+" poziom "+actuallyLevel, ConsoleColor.RED);
            return;
        }
        // set item to inv
        inventory.setItem(slot, itemStack);
    }

    private void fillBackground(Config config) {
        for (Config.BackgroundItem backgroundItem : config.backgroundConfig) {
            for (Integer slot : backgroundItem.slots) {
                inventory.setItem(slot, backgroundItem.item);
            }
        }
    }

    private void setItemWithRunes(User user, RuneConfig runeConfig, Config config, LangConfig langConfig) {
        // prepare list of active runes
        List<String> activeRunes = new ArrayList<>();
        user.getLevelOfRunes().forEach((runeType, level) -> {
            // this two runes is default in inventory so just ignore it
            if(runeType == RuneType.BOOST_DROP || runeType == RuneType.BOOST_POINTS) return;
            // if level = 0 so rune is not active
            if(level==0) return;

            Rune rune = runeConfig.runes.get(runeType);
            // if its disabled then return
            if(!rune.isEnable())
                return;

            Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(level);
            // if level with rune exists then add name to list
            runeByLevel.ifPresent(runeLevel -> activeRunes.add(runeLevel.getName()));

        });

        // item with list of runes (empty)
        ItemStack itemWithListRunes = config.itemWithListRunes.clone();
        ItemMeta itemMeta = itemWithListRunes.getItemMeta();
        if(itemMeta!=null && itemMeta.getLore()!=null && !itemMeta.getLore().isEmpty()) {
            // replace the {runes} with list of active runes
            List<String> newLore = new ArrayList<>();
            for (String line : itemMeta.getLore()) {
                if(line.contains("{runes}")) {
                    if(activeRunes.isEmpty()) {
                        newLore.add(langConfig.noRunes);
                    } else {
                        newLore.addAll(activeRunes);
                    }
                    continue;
                }
                newLore.add(line);
            }
            // set new lore
            itemMeta.setLore(ColorFixer.addColors(newLore));

            itemWithListRunes.setItemMeta(itemMeta);
        }

        // set item with list of active runes to inv
        inventory.setItem(config.slotItemRunes, itemWithListRunes);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
