package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.saehyeon.hiusmp.Constants.shop.SHOP_SELECT_GUI_TITLE;

public class ShopManager {
    public static void openSelectScreen(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, SHOP_SELECT_GUI_TITLE);

        ItemStack blockShop = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta blockShopMeta = blockShop.getItemMeta();;
        blockShopMeta.setDisplayName(Constants.shop.BLOCK_SHOP_ICON_NAME);
        blockShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blockShop.setItemMeta(blockShopMeta);

        inv.setItem(10, blockShop);

        ItemStack blockShop2 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta blockShop2Meta = blockShop2.getItemMeta();;
        blockShop2Meta.setDisplayName(Constants.shop.BLOCK_SHOP_TWO_ICON_NAME);
        blockShop2Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blockShop2.setItemMeta(blockShop2Meta);

        inv.setItem(12, blockShop2);

        ItemStack minerShop = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta minerMeta = minerShop.getItemMeta();
        minerMeta.setDisplayName(Constants.shop.MINER_SHOP_ICON_NAME);
        minerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        minerShop.setItemMeta(minerMeta);

        inv.setItem(14, minerShop);

        ItemStack farmShop = new ItemStack(Material.IRON_HOE);
        ItemMeta farmShopMeta = farmShop.getItemMeta();
        farmShopMeta.setDisplayName(Constants.shop.FARM_SHOP_ICON_NAME);
        farmShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        farmShop.setItemMeta(farmShopMeta);

        inv.setItem(16, farmShop);

        ItemStack monsterShop = new ItemStack(Material.IRON_SWORD);
        ItemMeta monsterShopMeta = monsterShop.getItemMeta();
        monsterShopMeta.setDisplayName(Constants.shop.MONSTER_SHOP_ICON_NAME);
        monsterShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        monsterShop.setItemMeta(monsterShopMeta);

        inv.setItem(28, monsterShop);

        ItemStack treasureShop = new ItemStack(Material.NETHER_STAR);
        ItemMeta treasureMeta = treasureShop.getItemMeta();
        treasureMeta.setDisplayName(Constants.shop.TREASURE_SHOP_ICON_NAME);
        treasureMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        treasureShop.setItemMeta(treasureMeta);

        inv.setItem(30, treasureShop);

        ItemStack specialShop = new ItemStack(Material.ECHO_SHARD);
        ItemMeta specialShopMeta = specialShop.getItemMeta();
        specialShopMeta.setDisplayName(Constants.shop.SPECIAL_SHOP_ICON_NAME);
        specialShopMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        specialShop.setItemMeta(specialShopMeta);

        inv.setItem(32, specialShop);

        player.openInventory(inv);
    }
}
