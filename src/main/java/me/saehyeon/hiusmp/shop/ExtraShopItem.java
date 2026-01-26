package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ExtraShopItem {
    int sellPrice, buyPrice;
    Material material;
    String displayName;
    List<String> lores = new ArrayList<>();

    public ExtraShopItem(Material material, String displayName, int sellPrice, int buyPrice) {
        this.material = material;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.displayName = displayName;

        ShopStorage.globalExtraItemSellPrice.put(displayName, sellPrice);
        ShopStorage.globalExtraItemBuyPrice.put(displayName, buyPrice);
    }

    public ExtraShopItem registerRealItem(ItemStack items) {
        ShopStorage.realExtraItems.put(this.displayName, items);
        return this;
    }

    public static ItemStack getRealItem(ItemStack shopItem) {
        return ShopStorage.realExtraItems.get(ItemUtil.getDisplayName(shopItem));
    }

    public static int getSellPrice(ItemStack item) {
        return ShopStorage.globalExtraItemSellPrice.get(ItemUtil.getDisplayName(item));
    }

    public static int getBuyPrice(ItemStack item) {
        return ShopStorage.globalExtraItemBuyPrice.get(ItemUtil.getDisplayName(item));
    }

    public ExtraShopItem setLore(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public static boolean isExtraShopItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == Constants.shop.EXTRA_SHOP_ITEM_CUSTOM_MODE_DATA;
    }

    public ItemStack toShopItem() {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setCustomModelData(Constants.shop.EXTRA_SHOP_ITEM_CUSTOM_MODE_DATA);

        List<String> finalLores = new ArrayList<>(lores);
        finalLores.add("§7[좌클릭] §6"+sellPrice+" 히유코인§f으로 판매");
        finalLores.add("§7[우클릭] §6"+buyPrice+" 히유코인§f으로 구매");
        finalLores.add("");
        finalLores.add("§f쉬프트 + 클릭으로 10개씩 구매 또는 판매");

        meta.setLore(finalLores);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }

}
