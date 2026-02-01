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
            new ShopItem("썩은 고기", Material.ROTTEN_FLESH, 20,120),
            new ShopItem("뼈", Material.BONE, 20,120),
            new ShopItem("화살", Material.ARROW, 20,100),
            new ShopItem("실", Material.STRING, 10,60),
            new ShopItem("거미 눈", Material.SPIDER_EYE, 30,180),
            new ShopItem("토끼 가죽", Material.RABBIT_HIDE, 20,120),
            new ShopItem("토끼 발", Material.RABBIT_FOOT, 40,240),
            new ShopItem("가죽", Material.LEATHER, 30,160),
            new ShopItem("양털", Material.WHITE_WOOL, 10,60),
            new ShopItem("깃털", Material.FEATHER, 10,60),
            new ShopItem("화약", Material.GUNPOWDER, 10, 60),
            new ShopItem("부싯돌", Material.FLINT, 1, 20),
            new ShopItem("발광 먹물 주머니", Material.GLOW_INK_SAC, 50, 300),

            new ShopItem("소고기", Material.BEEF, 30, 180),
            new ShopItem("돼지고기", Material.PORKCHOP, 40, 200),
            new ShopItem("닭고기", Material.CHICKEN, 20, 120),
            new ShopItem("양고기", Material.MUTTON, 30, 160),
            new ShopItem("달걀", Material.EGG, 10, 30),
            new ShopItem("책",Material.BOOK, 10, 30),
            new ShopItem("슬라임볼",Material.SLIME_BALL, 25, 100)
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
