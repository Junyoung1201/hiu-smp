package me.saehyeon.hiusmp.parkour;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.utils.LocationUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Parkour {
    private static int PARKOUR_COMPLETE_PRICE = 35000;

    public static void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "파쿠르");

        ItemStack item = new ItemStack(Material.SLIME_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b세현의 정신세계 파쿠르");
        meta.setLore(Arrays.asList("§f클리어 시 §6"+PARKOUR_COMPLETE_PRICE+" 히유코인§f을 얻습니다."));
        item.setItemMeta(meta);

        player.openInventory(inv);
        inv.setItem(13, item);
    }

    public static boolean isInParkour(Player player) {
        return LocationUtil.isWithin(player.getLocation(), Constants.locations.PARKOUR_POS1, Constants.locations.PARKOUR_POS2);
    }

    public static void complete(Player player) {
        Economy.addMoney(player, PARKOUR_COMPLETE_PRICE);
        player.teleport(Constants.locations.LOBBY);
        player.sendTitle("§a§l파쿠르 완료", "§6"+PARKOUR_COMPLETE_PRICE+" 히유코인§f을 얻었습니다!",0,70,20);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 0.7f,1f);
    }

    public static void start(Player player) {
        player.teleport(Constants.locations.PARKOUR_SPAWN);

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {

            player.sendMessage("");
            player.sendMessage("§b§l세현의 정신세계");
            player.sendMessage("완료 시 §6"+PARKOUR_COMPLETE_PRICE+" 히유코인§f을 얻습니다.");
            player.sendMessage("");

            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER,1,1);
        },5);
    }

    public static void failed(Player player) {

        player.teleport(Constants.locations.PARKOUR_SPAWN);
        player.sendMessage("§c파쿠르에 실패했습니다! \"/나가기\"를 입력하면 파쿠르에서 나갑니다.");
    }

    public static void exit(Player player) {
        // 로비로 이동시키기
        player.teleport(Constants.locations.LOBBY);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 0.7f, 1);
        player.sendMessage("파쿠르에서 나갑니다.");
    }
}
