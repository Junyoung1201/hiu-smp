package me.saehyeon.hiusmp.lobby;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InteractiveEvent implements Listener {
    @EventHandler
    void onInteractiveEvent(PlayerInteractEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        ItemStack item2 = e.getPlayer().getInventory().getItemInOffHand();


        if(item != null && e.getPlayer().getWorld().getName().equals("world") && e.getPlayer().getGameMode() != GameMode.CREATIVE) {

            // 로비에서 엔더진주 사용 금지
            if(item.getType() == Material.ENDER_PEARL || item2.getType() == Material.ENDER_PEARL) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§c이 월드에서는 엔더진주를 사용할 수 없습니다.");
            }

            // 로비에서 폭죽 사용 금지
            if(item.getType() == Material.FIREWORK_ROCKET || item2.getType() == Material.FIREWORK_ROCKET) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§c이 월드에서는 폭죽을 사용할 수 없습니다.");
            }
        }
    }
}
