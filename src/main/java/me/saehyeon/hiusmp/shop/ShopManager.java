package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopManager {
    public static void openSelectScreen(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "상점 선택");

        ItemStack blockShop = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta blockShopMeta = blockShop.getItemMeta();;
        blockShopMeta.setDisplayName("§f블럭 상점");
        blockShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blockShop.setItemMeta(blockShopMeta);

        inv.setItem(11, blockShop);

        ItemStack minerShop = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta minerMeta = minerShop.getItemMeta();
        minerMeta.setDisplayName("§f광물 상점");
        minerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        minerShop.setItemMeta(minerMeta);

        inv.setItem(13, minerShop);

        ItemStack treasureShop = new ItemStack(Material.NETHER_STAR);
        ItemMeta treasureMeta = treasureShop.getItemMeta();
        treasureMeta.setDisplayName("§f희귀품 상점");
        treasureMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        treasureShop.setItemMeta(treasureMeta);

        inv.setItem(15, treasureShop);

        player.openInventory(inv);
    }
}
