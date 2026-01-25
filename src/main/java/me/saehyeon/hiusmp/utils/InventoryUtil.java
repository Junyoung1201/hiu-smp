package me.saehyeon.hiusmp.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class InventoryUtil {
    public static void removeItems(Player player, Predicate<ItemStack> predicate, int amount) {
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem != null && predicate.test(invItem)) {
                int preAmount = invItem.getAmount();
                int newAmount = Math.max(0, preAmount - amount);
                amount = Math.max(0, amount - preAmount);
                invItem.setAmount(newAmount);
                if (amount == 0) {
                    break;
                }
            }
        }
    }

    public static void removeItems(Player player, Material material, int amount) {

        for(ItemStack invItem : player.getInventory().getContents()) {
            if(invItem != null) {
                if(invItem.getType().equals(material)) {
                    int preAmount = invItem.getAmount();
                    int newAmount = Math.max(0, preAmount - amount);
                    amount = Math.max(0, amount - preAmount);
                    invItem.setAmount(newAmount);
                    if(amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    public static int howManyItems(Player player, Predicate<ItemStack> predicate) {
        return Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(predicate)
                .mapToInt(ItemStack::getAmount)
                .sum();
    }

    public static int howManyItems(Player player, Material material) {
        return Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(i -> i.getType() == material)
                .mapToInt(ItemStack::getAmount)
                .sum();
    }

    public static boolean hasItem(Player player, Predicate<ItemStack> predicate, int amount) {
        return howManyItems(player, predicate) >= amount;
    }

    public static boolean hasItem(Player player, Material material, int amount) {
        return howManyItems(player, material) >= amount;
    }
}
