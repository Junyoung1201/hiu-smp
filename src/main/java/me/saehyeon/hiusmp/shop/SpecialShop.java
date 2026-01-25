package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.items.InventorySavePaper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SpecialShop {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "스페셜 상점");

        ExtraShopItem[] items = new ExtraShopItem[] {
            new ExtraShopItem(Material.PAPER, "§e인벤세이브 스크롤", 500, 700)
                    .registerRealItem(InventorySavePaper.getItem())
                    .setLore(Arrays.asList("§f지니고 있으면 인벤토리의 아이템과 레벨을 보호합니다.",""))
        };

        player.openInventory(inv);

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
