package dev.gether.getastronauta.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder glow(boolean status) {
        if(status) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta!=null) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
            }
        }
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(ColorFixer.addColors(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder data(short data) {
        itemStack.setDurability(data);
        return this;
    }

    public ItemBuilder name(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ColorFixer.addColors(displayName));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public static ItemBuilder create(Material material) {
        return new ItemBuilder(material);
    }

}