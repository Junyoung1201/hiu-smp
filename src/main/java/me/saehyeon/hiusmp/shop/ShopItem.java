package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class ShopItem {
    String displayName;
    Material material;
    int sellPrice;
    int buyPrice;

    ShopItem(Material mat, int sellPrice, int buyPrice) {
        this.material = mat;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;

        ShopStorage.globalSellPrice.put(mat, sellPrice);
        ShopStorage.globalBuyPrice.put(mat, buyPrice);
    }

    ShopItem(String displayName, Material mat, int sellPrice, int buyPrice) {
        this.displayName = displayName;
        this.material = mat;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;

        ShopStorage.globalSellPrice.put(mat, sellPrice);
        ShopStorage.globalBuyPrice.put(mat, buyPrice);
    }

    public static int getSellPrice(ItemStack item) {
        return ShopStorage.globalSellPrice.get(item.getType());
    }

    public static int getBuyPrice(ItemStack item) {
        return ShopStorage.globalBuyPrice.get(item.getType());
    }

    public static boolean isShopItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == Constants.shop.SHOP_ITEM_CUSTOM_MODEL_DATA;
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§f"+displayName);
        meta.setLore(Arrays.asList("§7[좌클릭] §6"+sellPrice+"원§f으로 판매", "§7[우클릭] §6"+buyPrice+"원§f으로 구매","","§f쉬프트 + 클릭으로 10개씩 구매 또는 판매"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // 아이템의 custom model data가 1001 이면 -> 상점 아이템임
        meta.setCustomModelData(Constants.shop.SHOP_ITEM_CUSTOM_MODEL_DATA);

        item.setItemMeta(meta);
        return item;
    }
}
