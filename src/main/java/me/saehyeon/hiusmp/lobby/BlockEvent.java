package me.saehyeon.hiusmp.lobby;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockEvent implements Listener {
    @EventHandler
    void onLobbyWorldBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getWorld().getName().equals("world") && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onLobbyWorldBlockPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getWorld().getName().equals("world") && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onLobbyWorldInteractionBlock(PlayerInteractEvent e) {
        if(e.getClickedBlock() != null && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }
}
