package dev.gether.getaustronauta.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static void giveItem(Player player, ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
    }

    // method to remove item from player inventory
    public static void removeItem(Player player, ItemStack itemToRemove, int amount)
    {
        int removeItem = amount;

        for(ItemStack itemStack : player.getInventory()) {
            if(itemStack==null || itemStack.getType()== Material.AIR)
                continue;

            if(itemStack.isSimilar(itemToRemove))
            {
                if(removeItem<=0)
                    break;

                if(itemStack.getAmount()<=removeItem)
                {
                    removeItem-=itemStack.getAmount();
                    itemStack.setAmount(0);
                } else {
                    itemStack.setAmount(itemStack.getAmount()-removeItem);
                    removeItem=0;
                }
            }
        }
    }
    public static int calcItem(Player player, ItemStack fromItem)
    {
        int amount = 0;
        for(ItemStack itemStack : player.getInventory())
        {
            if(itemStack==null || itemStack.getType()== Material.AIR)
                continue;

            if(itemStack.isSimilar(fromItem))
                amount+=itemStack.getAmount();
        }

        return amount;
    }

}
