package me.saehyeon.hiusmp.lobby;

import me.saehyeon.hiusmp.parkour.Parkour;
import me.saehyeon.hiusmp.utils.PlayerUtil;
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
                    PlayerUtil.teleport(e.getPlayer(), "wild", 0, 76, 0, 0,0);
                    e.getPlayer().sendMessage("§c§l야생에 집을 짓지 마세요! §f야생은 1주 마다 초기화됩니다!");
                }

                else if(npcName.equals("파쿠르")) {
                    Parkour.openGUI(e.getPlayer());
                }

                else if(npcName.equals("집터로 이동")) {
                    PlayerUtil.teleport(e.getPlayer(), "town", 0.5D, -12D, 0.5D, 0,0);
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
