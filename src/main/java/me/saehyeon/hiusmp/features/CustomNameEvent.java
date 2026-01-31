package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CustomNameEvent implements Listener {
    @EventHandler
    void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        CustomName.teleportFix(p);
    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            CustomName.clearCustomNameTagTextDisplay();
            Player p = e.getPlayer();
            CustomName.teleportFix(p);
        },3);
    }

    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("§e"+CustomName.getName(e.getPlayer())+"(이)가 히유 목장에 접속했습니다.");

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            CustomName.setName(e.getPlayer(), CustomName.getName(e.getPlayer()));

            if(CustomName.getName(e.getPlayer()).equals(e.getPlayer().getName())) {
                e.getPlayer().sendMessage("당신은 §6무료로 한글 닉네임§f을 설정할 수 있습니다! §6/이름 [한글 닉네임]§f을 입력하여 설정할 수 있습니다.");
            }
        },3);
    }

    @EventHandler
    void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§e"+CustomName.getName(e.getPlayer())+"(이)가 나갔습니다.");

        // 월드에서 해당 유저의 이름표 엔티티 지우기
        Bukkit.getWorlds().forEach(world -> {
            world.getEntitiesByClass(TextDisplay.class).forEach(nameTag -> {
                if(nameTag.getScoreboardTags().contains("player-name-tag_"+e.getPlayer().getUniqueId())) {
                    nameTag.remove();
                }
            });
        });
    }
}
