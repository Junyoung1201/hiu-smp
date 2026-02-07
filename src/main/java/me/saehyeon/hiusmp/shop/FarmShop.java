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
                new ShopItem("밀 씨앗", Material.WHEAT_SEEDS, 2,5),
                new ShopItem("호박 씨앗", Material.PUMPKIN_SEEDS, 4,6),
                new ShopItem("수박 씨앗", Material.MELON_SEEDS, 2,5),
                new ShopItem("비트 씨앗", Material.BEETROOT_SEEDS, 4,7),
                new ShopItem("고대 씨앗", Material.TORCHFLOWER_SEEDS, 6,8),
                new ShopItem("코코아", Material.COCOA_BEANS, 6,8),

                new ShopItem("밀", Material.WHEAT, 30,90),
                new ShopItem("감자", Material.POTATO, 20,50),
                new ShopItem("구운감자", Material.BAKED_POTATO, 23,53),
                new ShopItem("독 감자", Material.POISONOUS_POTATO, 25,55),
                new ShopItem("당근", Material.CARROT, 20,50),
                new ShopItem("황금 당근", Material.GOLDEN_CARROT, 25 ,450),
                new ShopItem("사탕수수", Material.SUGAR_CANE, 20,60),
                new ShopItem("호박", Material.PUMPKIN, 60,120),
                new ShopItem("수박 슬라이스", Material.MELON_SLICE, 10,90),
                new ShopItem("수박", Material.MELON, 70,630),
                new ShopItem("비트", Material.BEETROOT, 60,150),
                new ShopItem("횃불꽃", Material.TORCHFLOWER,  55,180),
                new ShopItem("대나무", Material.BAMBOO, 20,50)
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
