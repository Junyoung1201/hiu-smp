package me.saehyeon.hiusmp.security;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class NPCEvent implements Listener {

    @EventHandler
    void onLobbyNpcInteractive(PlayerInteractAtEntityEvent e) {
        if(e.getPlayer().getWorld().getName().equals("world") && e.getRightClicked() != null && e.getRightClicked().getType() == EntityType.MANNEQUIN) {
            e.setCancelled(true);

            if(e.getHand() == EquipmentSlot.HAND) {
                World wild = Bukkit.getWorld("wild");
                e.getPlayer().teleport(new Location(wild, 0,78, 0));
            }
        }
    }

    @EventHandler
    void onLobbyNpcDamaged(EntityDamageByEntityEvent e) {
        if(e.getDamager().getType() == EntityType.PLAYER && e.getDamager().getWorld().getName().equals("world") && e.getEntity().getType() == EntityType.MANNEQUIN) {
            e.setCancelled(true);
        }
    }
}
