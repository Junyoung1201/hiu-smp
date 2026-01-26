package me.saehyeon.hiusmp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {
    public static void teleport(Player player, String world, double x, double y, double z, float yaw, float pitch) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:"+world+" run tp "+player.getName()+" "+x+" "+y+" "+z+" "+yaw+" "+pitch);
    }

    public static boolean isMainHandItemName(Player player, String itemDisplayName) {
        return player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(itemDisplayName);
    }
}
