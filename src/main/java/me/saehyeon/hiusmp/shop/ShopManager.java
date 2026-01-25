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
        Inventory inv = Bukkit.createInventory(null, 54, "상점 선택");

        ItemStack blockShop = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta blockShopMeta = blockShop.getItemMeta();;
        blockShopMeta.setDisplayName("§f블럭 상점");
        blockShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blockShop.setItemMeta(blockShopMeta);

        inv.setItem(10, blockShop);

        ItemStack minerShop = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta minerMeta = minerShop.getItemMeta();
        minerMeta.setDisplayName("§f광물 상점");
        minerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        minerShop.setItemMeta(minerMeta);

        inv.setItem(12, minerShop);

        ItemStack farmShop = new ItemStack(Material.IRON_HOE);
        ItemMeta farmShopMeta = farmShop.getItemMeta();
        farmShopMeta.setDisplayName("§f농작물 상점");
        farmShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        farmShop.setItemMeta(farmShopMeta);

        inv.setItem(14, farmShop);

        ItemStack treasureShop = new ItemStack(Material.NETHER_STAR);
        ItemMeta treasureMeta = treasureShop.getItemMeta();
        treasureMeta.setDisplayName("§f희귀품 상점");
        treasureMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        treasureShop.setItemMeta(treasureMeta);

        inv.setItem(16, treasureShop);

        ItemStack monsterShop = new ItemStack(Material.STONE_SWORD);
        ItemMeta monsterShopMeta = monsterShop.getItemMeta();
        monsterShopMeta.setDisplayName("§f잡템 상점");
        monsterShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        monsterShop.setItemMeta(monsterShopMeta);

        inv.setItem(28, monsterShop);

        ItemStack specialShop = new ItemStack(Material.ECHO_SHARD);
        ItemMeta specialShopMeta = specialShop.getItemMeta();
        specialShopMeta.setDisplayName("§d스페셜 상점");
        specialShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        specialShop.setItemMeta(specialShopMeta);

        inv.setItem(30, specialShop);

        player.openInventory(inv);
    }
}
