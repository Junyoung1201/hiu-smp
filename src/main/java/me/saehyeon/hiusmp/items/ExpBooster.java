package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static me.saehyeon.hiusmp.Constants.items.SI_EXP_BOOSTER_DISPLAY_NAME;
import static me.saehyeon.hiusmp.Constants.items.SI_EXP_BOOSTER_TICK;

public class ExpBooster {
    private static ArrayList<UUID> expBoostPlayers = new ArrayList<>();

    public static boolean hasBoost(Player player) {
        return expBoostPlayers.contains(player.getUniqueId());
    }

    public static void startExpBoost(Player player) {
        if(expBoostPlayers.contains(player.getUniqueId())) {
            player.sendMessage("§c이미 경험치 2배가 적용 중입니다.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 0.7f,1);
            return;
        }

        expBoostPlayers.add(player.getUniqueId());

        player.sendMessage("§a"+(SI_EXP_BOOSTER_TICK / 20 / 60)+"분 간 경험치를 2배 얻습니다. §f서버를 나가도 유지됩니다.");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.7f,1f);

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            player.sendMessage("경험치 부스트가 종료되었습니다.");
            expBoostPlayers.remove(player.getUniqueId());
        },SI_EXP_BOOSTER_TICK);

    }

    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(SI_EXP_BOOSTER_DISPLAY_NAME);
        meta.setLore(Arrays.asList("§a"+(SI_EXP_BOOSTER_TICK / 20 / 60)+"분 간 경험치 2배 부스팅§f을 부여받습니다.",""));
        meta.addEnchant(Enchantment.EFFICIENCY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        return item;
    }
}
