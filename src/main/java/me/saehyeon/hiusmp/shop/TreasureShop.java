package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TreasureShop {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "희귀품 상점");
        player.openInventory(inv);

        ShopItem[] items = new ShopItem[] {
                new ShopItem("네더라이트 형판", Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1000,35000),
                new ShopItem("네더의 별", Material.NETHER_STAR, 50000,130000),
                new ShopItem("바다의 심장", Material.HEART_OF_THE_SEA, 25000,13000),
                new ShopItem("§e인첸트된 황금사과", Material.ENCHANTED_GOLDEN_APPLE, 7000,30000),
                new ShopItem("쉘커 껍데기", Material.SHULKER_SHELL, 5000,8000),
                new ShopItem("돌풍구", Material.WIND_CHARGE, 2000,13000),
                new ShopItem("겉날개", Material.ELYTRA, 45000,240000),
                new ShopItem("폭죽", Material.FIREWORK_ROCKET, 50,500),
                new ShopItem("엔더드래곤 머리", Material.DRAGON_HEAD, 35000,100000),
                new ShopItem("토템", Material.TOTEM_OF_UNDYING, 40000,200000),
                new ShopItem("엔더드래곤 알", Material.DRAGON_EGG, 10000,50000),
                new ShopItem("팬텀 막", Material.PHANTOM_MEMBRANE, 2500,10000),
                new ShopItem("소 스폰알", Material.COW_SPAWN_EGG, 0,5000),
                new ShopItem("양 스폰알", Material.SHEEP_SPAWN_EGG, 0,5000),
                new ShopItem("돼지 스폰알", Material.PIG_SPAWN_EGG, 0,5000),
                new ShopItem("닭 스폰알", Material.CHICKEN_SPAWN_EGG, 0,5000),
                new ShopItem("말 스폰알", Material.HORSE_SPAWN_EGG, 0,10000),
                new ShopItem("아홀로틀 스폰알", Material.AXOLOTL_SPAWN_EGG, 0,10000),
                new ShopItem("벌 스폰알", Material.BEE_SPAWN_EGG, 0,2500),
                new ShopItem("고양이 스폰알", Material.CAT_SPAWN_EGG, 0,3500),
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
