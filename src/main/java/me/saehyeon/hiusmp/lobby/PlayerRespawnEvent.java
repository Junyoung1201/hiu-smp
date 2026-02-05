package me.saehyeon.hiusmp.lobby;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.saehyeon.hiusmp.Constants.locations.LOBBY;

public class PlayerRespawnEvent implements Listener {
    @EventHandler
    void onRespawn(org.bukkit.event.player.PlayerRespawnEvent e) {
        e.setRespawnLocation(LOBBY);
    }
}
