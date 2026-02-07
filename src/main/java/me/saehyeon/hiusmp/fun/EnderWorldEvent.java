package me.saehyeon.hiusmp.fun;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class EnderWorldEvent implements Listener {
    boolean isEndWorld(Player player) {
        return player.getWorld().getName().endsWith("_the_end");
    }

    @EventHandler
    void onPortalEnter(PlayerPortalEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            if(isEndWorld(e.getPlayer())) {
                e.getPlayer().sendMessage("§c§l조심하세요! §f엔더월드에서는 사망 시 아이템이 모두 증발합니다.");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, SoundCategory.MASTER, 0.7f,1);
            }
        },3);
    }
    
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        if(isEndWorld(e.getPlayer())) {
            e.getDrops().clear();
        }
    }
}
