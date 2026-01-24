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
                new ShopItem("네더라이트 형판", Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 3500,10000),
                new ShopItem("네더의 별", Material.NETHER_STAR, 20000,30000),
                new ShopItem("바다의 심장", Material.HEART_OF_THE_SEA, 5000,13000),
                new ShopItem("쉘커 껍데기", Material.SHULKER_SHELL, 5000,8000),
                new ShopItem("돌풍구", Material.WIND_CHARGE, 2000,10000),
                new ShopItem("겉날개", Material.ELYTRA, 45000,225000),
                new ShopItem("폭죽", Material.FIREWORK_ROCKET, 50,250),
                new ShopItem("토템", Material.TOTEM_OF_UNDYING, 40000,200000),
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
