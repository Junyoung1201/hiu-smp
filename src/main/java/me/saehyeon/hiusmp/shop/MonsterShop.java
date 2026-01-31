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
            new ShopItem("썩은 고기", Material.ROTTEN_FLESH, 20,60),
            new ShopItem("뼈", Material.BONE, 20,60),
            new ShopItem("실", Material.STRING, 10,30),
            new ShopItem("거미 눈", Material.SPIDER_EYE, 30,90),
            new ShopItem("토끼 가죽", Material.RABBIT_HIDE, 20,60),
            new ShopItem("토끼 발", Material.RABBIT_FOOT, 40,120),
            new ShopItem("가죽", Material.LEATHER, 30,80),
            new ShopItem("양털", Material.WHITE_WOOL, 10,30),
            new ShopItem("깃털", Material.FEATHER, 10,30),

            new ShopItem("소고기", Material.BEEF, 30, 90),
            new ShopItem("돼지고기", Material.PORKCHOP, 40, 100),
            new ShopItem("닭고기", Material.CHICKEN, 20, 60),
            new ShopItem("양고기", Material.MUTTON, 30, 80),
            new ShopItem("달걀", Material.EGG, 10, 15),
            new ShopItem("책",Material.BOOK, 10, 15),
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
