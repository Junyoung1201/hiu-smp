package me.saehyeon.hiusmp.parkour;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.Main;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParkourEvent implements Listener {
    @EventHandler
    void onDamagedInParkour(EntityDamageEvent e) {
        if(e.getEntityType() == EntityType.PLAYER && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) e.getEntity();

            if(Parkour.isInParkour(player)) {
                e.setCancelled(true);
                e.setDamage(0);
            }
        }
    }

    @EventHandler
    void onStartScreenClick(InventoryClickEvent e) {
        if(e.getCurrentItem() != null && e.getView().getTitle().equals("파쿠르")) {
            e.setCancelled(true);

            if(e.getCurrentItem().getType() == Material.SLIME_BLOCK) {
                Player player = (Player) e.getWhoClicked();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,0.7f,1f);
                player.closeInventory();
                Parkour.start(player);
            }
        }
    }

    @EventHandler
    void onStep(PlayerMoveEvent e) {
        if(Parkour.isInParkour(e.getPlayer()) && !e.getTo().toBlockLocation().equals(e.getFrom().toBlockLocation())) {
            Location loc = e.getTo().toBlockLocation().clone();
            loc.add(0,-1,0);

            if(loc.getBlock().getType() == Material.RED_CONCRETE) {
                // 파쿠르 실패
                Parkour.failed(e.getPlayer());
            }

            else if(loc.getBlock().getType() == Material.GOLD_BLOCK) {
                // 파쿠르 성공
                Parkour.complete(e.getPlayer());
            }
        }
    }
}
