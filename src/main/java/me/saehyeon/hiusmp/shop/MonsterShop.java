package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MonsterShop {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "잡템 상점");
        player.openInventory(inv);

        ShopItem[] items = new ShopItem[] {
            new ShopItem("썩은 고기", Material.ROTTEN_FLESH, 2,6),
            new ShopItem("뼈", Material.BONE, 2,6),
            new ShopItem("거미 눈", Material.SPIDER_EYE, 3,9),
            new ShopItem("토끼 가죽", Material.RABBIT_HIDE, 2,6),
            new ShopItem("토끼 발", Material.RABBIT_FOOT, 4,12)
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
