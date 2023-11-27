package dev.gether.getastronauta.user;

import dev.gether.getastronauta.GetAstronauta;
import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.config.LangConfig;
import dev.gether.getastronauta.config.RuneConfig;
import dev.gether.getastronauta.inv.AstronautaInvHolder;
import dev.gether.getastronauta.rune.Rune;
import dev.gether.getastronauta.rune.RuneLevel;
import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.utils.ItemUtil;
import dev.gether.getastronauta.utils.MessageUtil;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class UserManager {
    private HashMap<UUID, User> userData = new HashMap<>();
    private final UserService userService;
    private final RuneConfig runeConfig;
    private final LangConfig langConfig;
    private final Config config;

    public UserManager(UserService userService, RuneConfig runeConfig, LangConfig langConfig, Config config) {
        this.userService = userService;
        this.runeConfig = runeConfig;
        this.langConfig = langConfig;
        this.config = config;
    }

    public void openInv(Player player) {
        User user = userData.get(player.getUniqueId());
        // create astronauta inv holder and open the inv
        new AstronautaInvHolder(player, user, config, runeConfig, langConfig);
    }
    private boolean maxRuneLevel(RuneType runeType, int actuallyLevel) {
        Rune rune = runeConfig.runes.get(runeType);
        Optional<Integer> maxLevel = rune.maxLevel(runeType);
        if (maxLevel.isPresent()) {
            if(actuallyLevel < maxLevel.get()) {
                return false;
            }
        }
        return true;
    }
    public void useRune(Player player, Rune rune) {
        User user = userData.get(player.getUniqueId());

        // actually level of rune
        int actuallyLevel = user.getActuallyLevel(rune.getRuneType());
        // check max level
        if(maxRuneLevel(rune.getRuneType(), actuallyLevel)) {
            MessageUtil.sendMessage(player, langConfig.maxLevelRune);
            return;
        }
        // take rune next level
        Optional<RuneLevel> tempRune = rune.getRuneByLevel(actuallyLevel + 1);
        // if is empty = rune not exists = MAX LEVEL
        if(tempRune.isEmpty())
            return;

        // get stats for new level of rune
        RuneLevel runeLevel = tempRune.get();

        // counting amount of item with the rune
        int hasAmount = ItemUtil.calcItem(player, rune.getItemStack());
        // cost
        int cost = (int) runeLevel.getCost();
        // if not has enough money
        if(hasAmount<cost) {
            MessageUtil.sendMessage(player, langConfig.noRunesItem.replace("{need}", String.valueOf(cost-hasAmount)));
            return;
        }

        // take item rune and increase level
        ItemUtil.removeItem(player, rune.getItemStack(), cost);
        // increase level of rune
        user.increaseRune(rune.getRuneType());

        // is only one exception when the type of rune is HEARTS then
        // add heart to attribute
        if(rune.getRuneType()==RuneType.HEARTS) {

            // multiply * 2 - reason attribute of heart has default 20 value but 10 heart
            double heartDouble = runeLevel.getValue() * 2;

            // get attribute
            AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

            // set new value
            attribute.setBaseValue(20+heartDouble);

        }

        MessageUtil.sendMessage(player, langConfig.useRune);
    }

    // if return the true, then bought this successfully
    public boolean canUpgradeTheRune(Player player, RuneType runeType) {
        User user = userData.get(player.getUniqueId());

        // actually level of rune
        int actuallyLevel = user.getActuallyLevel(runeType);
        // check max level
        if(maxRuneLevel(runeType, actuallyLevel)) {
            MessageUtil.sendMessage(player, langConfig.maxLevelRune);
            return false;
        }
        // get cost upgrade
        Rune rune = runeConfig.runes.get(runeType);

        // if rune is disabled then return
        if(!rune.isEnable())
            return false;

        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel + 1);
        RuneLevel runeLevel = runeByLevel.get();
        // count how much user has item coin
        int count = ItemUtil.calcItem(player, config.itemCoins);

        // if not has enough coins
        if(count < runeLevel.getCost()) {
            MessageUtil.sendMessage(player, langConfig.noCoins);
            return false;
        }

        ItemUtil.removeItem(player, config.itemCoins, (int) runeLevel.getCost());
        user.increaseRune(rune.getRuneType());
        MessageUtil.sendMessage(player, langConfig.successfullyUpgrade);
        return true;
    }

    public void loadUser(Player player) {

        // add to database
        Optional<User> user = userService.loadUser(player, runeConfig);
        // if user exits in database
        if(user.isPresent()) {
            // add to local memory
            userData.put(player.getUniqueId(), user.get());
            return;
        }
        // if user not exists
        // add user with default constructor
        //userData.put(player.getUniqueId(), new User(runeConfig));

    }

    public void updateUser(Player player) {

        // get uset from map
        User user = userData.get(player.getUniqueId());
        if(user==null) {
            throw new RuntimeException("User is null "+player.getName());
        }
        // if user exists then update
        userService.updateUser(user, player);

    }

    public void deleteFromMemory(Player player) {
        userData.remove(player.getUniqueId());
    }


    // implement all user from database why is online when the plugin is loaded
    public void loadOnlineUsers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadUser(player);
        }
    }

    // save all progress users in database
    public void saveAllUsers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateUser(player);
        }
    }

    // runes all runes tod default value = 0
    public void resetRunes(Player player) {
        User user = userData.get(player.getUniqueId());
        user.resetRunes();
        // set default hearts 10 = 20 health
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }

    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(userData.get(uuid));
    }

    public void debugPlayer(LiteSender liteSender, Player player) {
        Optional<User> userTemp = getUser(player.getUniqueId());
        if(!userTemp.isPresent()) {
            MessageUtil.sendMessage(liteSender, "&cNie znaleziono podanego gracza!");
            return;
        }
        User user = userTemp.get();

        // list of runes
        List<String> runes = new ArrayList<>();
        user.getLevelOfRunes().forEach(((runeType, level) ->  {
            runes.add(runeType.name()+" -> "+level);
        }));

        MessageUtil.sendMessage(liteSender, MessageUtil.joinListToString(runes));
    }

    public void giveCoin(LiteSender liteSender, Player target, int amount) {
        // prepare item with right amount
        ItemStack item = config.itemCoins.clone();
        item.setAmount(amount);

        // give item
        target.getInventory().addItem(item);

        // info admin
        MessageUtil.sendMessage(liteSender, "&aPomyslnie nadano odlamek!");
    }

}
