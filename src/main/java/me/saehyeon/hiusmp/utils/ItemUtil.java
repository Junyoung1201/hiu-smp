package me.saehyeon.hiusmp.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
    public static String getDisplayName(ItemStack item) {
        if(item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
            return "";
        }

        return item.getItemMeta().getDisplayName();
    }
}
