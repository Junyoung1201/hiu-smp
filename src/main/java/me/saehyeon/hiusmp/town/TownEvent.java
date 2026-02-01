package me.saehyeon.hiusmp.town;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Estate;
import me.saehyeon.hiusmp.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class TownEvent implements Listener {
    ArrayList<Player> noPermissionAlertCooltime = new ArrayList<>();

    void sendNoPermissionAlert(Player player) {
        if(!noPermissionAlertCooltime.contains(player)) {
            Estate.sendNoPermissionMessage(player);
            noPermissionAlertCooltime.add(player);
            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                noPermissionAlertCooltime.remove(player);
            },5);
        }
    }

    @EventHandler
    void onEntityExplosion(ExplosionPrimeEvent e) {
        if(e.getEntity().getWorld().getName().equals("town")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onMobSpawn(CreatureSpawnEvent e) {
        if(e.getEntity().getWorld().getName().equals("town")) {
            if( e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BUCKET) {
                return;
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent e) {
        if(LocationUtil.isWithin(e.getBlock().getLocation(), Constants.locations.TOWN_SPAWN_POS1, Constants.locations.TOWN_SPAWN_POS2) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }

        // 권한 없는 땅 조작 금지
        if(e.getPlayer().getWorld().getName().equals("town")) {
            Chunk chunk = e.getBlock().getChunk();

            if(!Estate.hasPermission(e.getPlayer(), chunk.getX(), chunk.getZ())) {
                e.setCancelled(true);
                sendNoPermissionAlert(e.getPlayer());
            }
        }
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent e) {
        if(LocationUtil.isWithin(e.getBlock().getLocation(), Constants.locations.TOWN_SPAWN_POS1, Constants.locations.TOWN_SPAWN_POS2) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }

        // 권한 없는 땅 조작 금지
        if(e.getPlayer().getWorld().getName().equals("town")) {
            Chunk chunk = e.getBlockPlaced().getChunk();

            if(!Estate.hasPermission(e.getPlayer(), chunk.getX(), chunk.getZ())) {
                e.setCancelled(true);
                sendNoPermissionAlert(e.getPlayer());
            }
        }
    }

    @EventHandler
    void onInteraction(PlayerInteractEvent e) {

        // 권한 없는 땅 조작 금지
        if(e.getPlayer().getWorld().getName().equals("town")) {
            if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(e.getClickedBlock() != null) {
                    Chunk chunk = e.getClickedBlock().getChunk();

                    if(!Estate.hasPermission(e.getPlayer(), chunk.getX(), chunk.getZ())) {
                        e.setCancelled(true);
                        sendNoPermissionAlert(e.getPlayer());
                    }
                }
                }
        }
    }
}
