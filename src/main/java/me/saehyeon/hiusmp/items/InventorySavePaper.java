package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventorySavePaper {
    public static boolean hasItem(Player player) {
        for(ItemStack item : player.getInventory().getContents()) {
            if(ItemUtil.getDisplayName(item).equals("§e인벤세이브 스크롤")) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack getItem() {
        ItemStack scroll = new ItemStack(Material.PAPER);
        ItemMeta meta = scroll.getItemMeta();
        meta.setDisplayName("§e인벤세이브 스크롤");
        meta.setLore(Arrays.asList("§f인벤토리에 지니고 있으면 인벤토리에 있는 아이템이 보호됩니다."));
        meta.setCustomModelData(1001);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        scroll.setItemMeta(meta);
        return scroll;
    }
}
