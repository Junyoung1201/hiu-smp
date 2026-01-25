package me.saehyeon.hiusmp.bonus;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvancementEvent implements Listener {

    private static List<Player> cooldown = new ArrayList<>();

    @EventHandler
    void onAdvancementClear(PlayerAdvancementDoneEvent e) {

        if(cooldown.contains(e.getPlayer())) {
            return;
        }

        int coin = MathUtil.randInt(30,100);

        Economy.addMoney(e.getPlayer(), coin);
        cooldown.add(e.getPlayer());

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            e.getPlayer().sendMessage("§6+"+coin+" 히유코인 (새로운 도전과제 완료)");
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,0.7f,1);
        },2);

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            cooldown.remove(e.getPlayer());
        },20);
    }
}
