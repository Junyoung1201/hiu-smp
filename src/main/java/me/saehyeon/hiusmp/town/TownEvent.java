package me.saehyeon.hiusmp.town;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.utils.LocationUtil;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class TownEvent implements Listener {
    @EventHandler
    void onBlockBreak(BlockBreakEvent e) {
        if(LocationUtil.isWithin(e.getBlock().getLocation(), Constants.locations.TOWN_SPAWN_POS1, Constants.locations.TOWN_SPAWN_POS2) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent e) {
        if(LocationUtil.isWithin(e.getBlock().getLocation(), Constants.locations.TOWN_SPAWN_POS1, Constants.locations.TOWN_SPAWN_POS2) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }
}
