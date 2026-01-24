package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FarmShop {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "농작물 상점");
        player.openInventory(inv);

        ShopItem[] items = new ShopItem[] {
                new ShopItem("밀 씨앗", Material.WHEAT_SEEDS, 1,3),
                new ShopItem("호박 씨앗", Material.PUMPKIN_SEEDS, 2,4),
                new ShopItem("수박 씨앗", Material.MELON_SEEDS, 1,3),
                new ShopItem("비트 씨앗", Material.BEETROOT_SEEDS, 2,5),
                new ShopItem("고대 씨앗", Material.TORCHFLOWER_SEEDS, 3,6),
                new ShopItem("코코아", Material.COCOA_BEANS, 3,5),

                new ShopItem("밀", Material.WHEAT, 3,9),
                new ShopItem("호박", Material.PUMPKIN, 6,12),
                new ShopItem("수박 슬라이스", Material.MELON_SLICE, 3,9),
                new ShopItem("수박", Material.MELON, 27,81),
                new ShopItem("비트", Material.BEETROOT, 6,15),
                new ShopItem("횃불꽃", Material.TORCHFLOWER, 9,18),
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
