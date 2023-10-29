package dev.gether.getastronauta.rune;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public class Rune extends OkaeriConfig {

    private boolean enable;
    private RuneType runeType;
    private ItemStack itemStack;
    private Map<Integer, RuneLevel> runeLevel;

    public Rune(boolean enable, RuneType runeType, ItemStack itemStack, Map<Integer, RuneLevel> runeLevel) {
        this.enable = enable;
        this.runeType = runeType;
        this.itemStack = itemStack;
        this.runeLevel = runeLevel;
    }

    public boolean isEnable() {
        return enable;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public RuneType getRuneType() {
        return runeType;
    }

    public Optional<RuneLevel> getRuneByLevel(int level) {
        return Optional.ofNullable(runeLevel.get(level));
    }

    public Optional<Integer> maxLevel(RuneType runeType) {
        return runeLevel.keySet().stream().max(Integer::compare);
    }

}
