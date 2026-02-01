package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BlockShop2 {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "블럭 상점 #2");
        player.openInventory(inv);

        ShopItem[] items = new ShopItem[] {
            new ShopItem("하얀색 테라코타", Material.WHITE_TERRACOTTA, 10, 25),
            new ShopItem("밝은 회색 테라코타", Material.LIGHT_GRAY_TERRACOTTA, 10, 25),
            new ShopItem("회색 테라코타", Material.GRAY_TERRACOTTA, 10, 25),
            new ShopItem("검은색 테라코타", Material.BLACK_TERRACOTTA, 10, 25),
            new ShopItem("빨간색 테라코타", Material.RED_TERRACOTTA, 10, 25),
            new ShopItem("주황색 테라코타", Material.ORANGE_TERRACOTTA, 10, 25),
            new ShopItem("갈색 테라코타", Material.BROWN_TERRACOTTA, 10, 25),
            new ShopItem("노란색 테라코타", Material.YELLOW_TERRACOTTA, 10, 25),
            new ShopItem("초록색 테라코타", Material.LIME_TERRACOTTA, 10, 25),
            new ShopItem("녹색 테라코타", Material.GREEN_TERRACOTTA, 10, 25),
            new ShopItem("청록색 테라코타", Material.CYAN_TERRACOTTA, 10, 25),
            new ShopItem("하늘색 테라코타", Material.LIGHT_BLUE_TERRACOTTA, 10, 25),
            new ShopItem("파란색 테라코타", Material.BLUE_TERRACOTTA, 10, 25),
            new ShopItem("분홍색 테라코타", Material.PINK_TERRACOTTA, 10, 25),
            new ShopItem("보라색 테라코타", Material.PURPLE_TERRACOTTA, 10, 25),
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
