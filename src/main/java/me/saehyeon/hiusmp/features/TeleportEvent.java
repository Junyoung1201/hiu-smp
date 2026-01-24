package me.saehyeon.hiusmp.features;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeleportEvent implements Listener {
    @EventHandler
    void onMoveWhenTpWaiting(PlayerMoveEvent e) {

        double f_x = e.getFrom().toBlockLocation().getX();
        double f_y = e.getFrom().toBlockLocation().getY();
        double f_z = e.getFrom().toBlockLocation().getZ();

        double t_x = e.getTo().toBlockLocation().getX();
        double t_y = e.getTo().toBlockLocation().getY();
        double t_z = e.getTo().toBlockLocation().getZ();

        if((f_x != t_x || f_y != t_y || f_z != t_z) && Teleport.isWaitingTeleport(e.getPlayer())) {

            // TP 대기 중에 움직임 -> TP 취소 & TPA 취소
            Teleport.teleportCancel(e.getPlayer(),true);
            e.getPlayer().sendMessage("§c텔레포트가 취소되었습니다. 움직이지 마세요!");
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER,0.7f,1.2f);
        }
    }

    @EventHandler
    void onQuit(PlayerQuitEvent e) {
        Teleport.quit(e.getPlayer());
    }
}
