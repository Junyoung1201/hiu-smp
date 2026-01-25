package me.saehyeon.hiusmp.lobby;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectEvent implements Listener {
    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            e.getPlayer().sendMessage("§6/도움말§f로 명령어 설명을 확인할 수 있어요.");
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.7f, 1.5f);
        },10);
    }
}
