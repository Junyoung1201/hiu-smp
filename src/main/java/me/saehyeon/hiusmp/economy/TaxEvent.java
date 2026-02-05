package me.saehyeon.hiusmp.economy;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static me.saehyeon.hiusmp.Constants.costs.*;

public class TaxEvent implements Listener {
    @EventHandler
    void onTeleport(PlayerTeleportEvent e) {
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
//                if(e.getPlayer().getWorld().getName().equals("town")) {
//                    if(Economy.getMoney(e.getPlayer()) > USE_TOWN_COST) {
//                        Economy.addMoney(e.getPlayer(), -USE_TOWN_COST);
//                        e.getPlayer().sendMessage("집터 출입 비용으로 §6"+USE_TOWN_COST+" 히유코인§f을 지불했습니다.");
//                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_CHAIN_PLACE, SoundCategory.MASTER,1,1.5f);
//                    }
//                }

                if(e.getPlayer().getWorld().getName().equals("world")) {
                    if(Economy.getMoney(e.getPlayer()) > USE_LOBBY_COST) {
                        Economy.addMoney(e.getPlayer(), -USE_LOBBY_COST);
                        e.getPlayer().sendMessage("로비 이용 비용으로 §6"+USE_LOBBY_COST+" 히유코인§f을 지불했습니다.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_CHAIN_PLACE, SoundCategory.MASTER,1,1.5f);
                    }
                }
            },3);
        }
    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            if(Economy.getMoney(e.getPlayer()) >= RESPAWN_COST) {
                Economy.addMoney(e.getPlayer(), -RESPAWN_COST);
                e.getPlayer().sendMessage("리스폰 비용으로 §6"+RESPAWN_COST+" 히유코인§f이 공제되었습니다.");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_CHAIN_PLACE, SoundCategory.MASTER,1,1.5f);
            }
        },5);
    }
}
