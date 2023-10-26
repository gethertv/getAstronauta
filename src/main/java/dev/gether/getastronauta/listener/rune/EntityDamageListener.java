package dev.gether.getastronauta.listener.rune;

import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.rune.Rune;
import dev.gether.getastronauta.rune.RuneLevel;
import dev.gether.getastronauta.rune.RuneManager;
import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.user.User;
import dev.gether.getastronauta.user.UserManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;
import java.util.Random;

public class EntityDamageListener implements Listener {

    private UserManager userManager;
    private RuneManager runeManager;
    private Config config;
    private Random random;

    public EntityDamageListener(UserManager userManager, RuneManager runeManager, Config config) {
        this.random = new Random(System.currentTimeMillis());
        this.userManager = userManager;
        this.runeManager = runeManager;
        this.config = config;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();

        // modify damage by rune strength
        // so damager must be a player
        if(damager instanceof Player player) {
            Optional<User> userTemp = userManager.getUser(player.getUniqueId());
            if(userTemp.isPresent()) {
                User user = userTemp.get();
                // rune for strength
                handleEventByRuneStrength(event, user);
                // rune for life steal
                handleEventByRuneLifeSteal(event, player, user);
                // rune for poison
                handleEventByRunePoison(user, entity);
                // rune for resistance
                handleEVentByRuneResistance(event, entity);
                // rune for weakness
                handleEventByRuneWeakness(user, entity);
                // rune for slowness
                handleEventByRuneSlowness(user, entity);
            }
        }
    }

    private void handleEventByRuneSlowness(User user, Entity entity) {
        if(!(entity instanceof Player victim)) return;

        // get user level for slowness
        int actuallyLevel = user.getActuallyLevel(RuneType.SLOWNESS);
        if(actuallyLevel==0) return;

        // get rune
        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.SLOWNESS);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        // get rune level
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();

        // get change to SLOWNESS victim
        double chance = runeLevel.getValue();

        // win ticket
        double winTicket = random.nextDouble() * 100;

        // if winTicker is lower than chance than give effect
        if(winTicket <= chance) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*config.secondSlowness, 1, true));
        }
    }

    private void handleEventByRuneWeakness(User user, Entity entity) {
        if(!(entity instanceof Player victim)) return;

        // get user level for weakness
        int actuallyLevel = user.getActuallyLevel(RuneType.WEAKNESS);
        if(actuallyLevel==0) return;

        // get rune
        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.WEAKNESS);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        // get rune level
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();

        // get change to WEAKNESS victim
        double chance = runeLevel.getValue();

        // win ticket
        double winTicket = random.nextDouble() * 100;

        // if winTicker is lower than chance than give effect
        if(winTicket <= chance) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20*config.secondWeakness, 1, true));
        }
    }

    private void handleEVentByRuneResistance(EntityDamageByEntityEvent event, Entity entity) {
        if(!(entity instanceof Player)) return;

        Player victim = (Player) entity;
        // get user object for victim
        Optional<User> userTemp = userManager.getUser(victim.getUniqueId());
        if(userTemp.isEmpty())
            return;

        User user = userTemp.get();
        // get actually level user for rune RESISTANCE
        int actuallyLevel = user.getActuallyLevel(RuneType.RESISTANCE);
        // if is default then return
        if(actuallyLevel==0) return;

        // get rune object RESISTANCE
        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.RESISTANCE);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        // get stats for user perk
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();
        // percent by how much need to reduce damage
        // division per 100 to get multiply value on damage
        double damageMultiply = runeLevel.getValue()/100;
        double damage = event.getDamage();
        // set damage
        event.setDamage(damage*damageMultiply);


    }

    private void handleEventByRunePoison(User user, Entity entity) {
        if(!(entity instanceof Player victim)) return;

        // get rune
        int actuallyLevel = user.getActuallyLevel(RuneType.POISON);
        if(actuallyLevel==0) return;

        // get rune
        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.POISON);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        // get rune level
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();

        // get change to poison victim
        double chance = runeLevel.getValue();

        // win ticket
        double winTicket = random.nextDouble() * 100;

        // if winTicker is lower than chance than give effect
        if(winTicket <= chance) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*config.secondPoison, 1, true));
        }
    }

    private void handleEventByRuneLifeSteal(EntityDamageByEntityEvent event, Player damager, User user) {
        int actuallyLevel = user.getActuallyLevel(RuneType.LIFE_STEAL);
        if(actuallyLevel==0)
            return;

        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.LIFE_STEAL);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();
        // final damage
        double finalDamage = event.getFinalDamage();
        // chance to heal
        double chance = runeLevel.getValue();
        // win ticket
        double winTicket = random.nextDouble() * 100;
        // if is lower than heal player
        if(winTicket <= chance) {
            // max health
            double maxHealth = damager.getMaxHealth();
            // actually health
            double health = damager.getHealth();
            // calc healing health
            double newHealth = health + finalDamage;
            // check the new hp is over the limit
            damager.setHealth(Math.min(newHealth, maxHealth));
        }

    }

    private void handleEventByRuneStrength(EntityDamageByEntityEvent event, User user) {
        int actuallyLevel = user.getActuallyLevel(RuneType.STRENGTH);
        if(actuallyLevel==0)
            return;

        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.STRENGTH);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;

        RuneLevel runeLevel = runeByLevel.get();
        double finalDamage = event.getDamage() + runeLevel.getValue();
        event.setDamage(finalDamage);
        return;
    }
}
