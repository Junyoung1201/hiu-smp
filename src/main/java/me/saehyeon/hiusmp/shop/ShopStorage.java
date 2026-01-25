package me.saehyeon.hiusmp.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ShopStorage {
    public static HashMap<Material, Integer> globalSellPrice = new HashMap<>();
    public static HashMap<Material, Integer> globalBuyPrice = new HashMap<>();
    public static HashMap<String, Integer> globalExtraItemSellPrice = new HashMap<>();
    public static HashMap<String, Integer> globalExtraItemBuyPrice = new HashMap<>();
    public static HashMap<String, ItemStack> realExtraItems = new HashMap<>();
}
