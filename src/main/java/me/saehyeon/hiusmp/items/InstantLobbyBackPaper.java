package me.saehyeon.hiusmp.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static me.saehyeon.hiusmp.Constants.items.SI_LOBBY_BACK_PAPER_DISPLAY_NAME;

public class InstantLobbyBackPaper {
    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(SI_LOBBY_BACK_PAPER_DISPLAY_NAME);
        meta.setLore(Arrays.asList("§7우클릭§f하여 로비로 즉시 이동합니다."));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }
}
