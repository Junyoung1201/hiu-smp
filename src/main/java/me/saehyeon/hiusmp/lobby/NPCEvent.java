package me.saehyeon.hiusmp.lobby;

import me.saehyeon.hiusmp.parkour.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class NPCEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    void onLobbyNpcInteractive(PlayerInteractAtEntityEvent e) {
        if(e.getPlayer().getWorld().getName().equals("world") && e.getRightClicked() != null && e.getRightClicked().getType() == EntityType.MANNEQUIN) {
            e.setCancelled(true);

            if(e.getHand() == EquipmentSlot.HAND) {
                String npcName = ChatColor.stripColor(e.getRightClicked().getCustomName());

                if(npcName.equals("야생으로 이동")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:wild run tp "+e.getPlayer().getName()+" 0 78 0");
                }

                else if(npcName.equals("파쿠르 도박")) {
                    Parkour.openGUI(e.getPlayer());
                }
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
