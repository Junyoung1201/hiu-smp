package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MinerShop {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "광물 상점");
        player.openInventory(inv);

        ShopItem[] items = new ShopItem[] {
            new ShopItem("구리", Material.COPPER_INGOT, 1,2),
            new ShopItem("자수정", Material.AMETHYST_SHARD, 30,50),
            new ShopItem("석탄", Material.COAL, 1,5),
            new ShopItem("레드스톤", Material.REDSTONE, 1,3),
            new ShopItem("청금석", Material.LAPIS_LAZULI, 2,10),
            new ShopItem("철 광석", Material.RAW_IRON, 30,80),
            new ShopItem("철", Material.IRON_INGOT, 50,100),
            new ShopItem("금 광석", Material.RAW_GOLD, 120, 470),
            new ShopItem("금", Material.GOLD_INGOT, 150, 525),
            new ShopItem("다이아몬드", Material.DIAMOND, 5000, 15000),
            new ShopItem("에메랄드", Material.EMERALD, 7000,25000),
            new ShopItem("네더라이트", Material.NETHERITE_INGOT, 9000,40000)
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
