package me.saehyeon.hiusmp.parkour;

import me.saehyeon.hiusmp.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParkourEvent implements Listener {
    @EventHandler
    void onStartScreenClick(InventoryClickEvent e) {
        if(e.getCurrentItem() != null && e.getView().getTitle().equals("파쿠르")) {
            e.setCancelled(true);

            if(e.getCurrentItem().getType() == Material.SLIME_BLOCK) {
                Player player = (Player) e.getWhoClicked();
                Parkour.startInputMoney(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,0.7f,1f);
                player.closeInventory();
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

    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        if(Parkour.isInputingMoney(e.getPlayer())) {
            e.setCancelled(true);

            if(e.getMessage().toLowerCase().equals("exit") || e.getMessage().equals("종료")) {
                e.getPlayer().sendMessage("파쿠르 도박을 취소했습니다.");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1);
                Parkour.stopInputMoney(e.getPlayer());
            } else {
                try {
                    int money = Integer.parseInt(e.getMessage());
                    Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                        Parkour.start(e.getPlayer(),money);
                    },5);
                } catch (Exception err) {
                    err.printStackTrace();
                    e.getPlayer().sendMessage("§c배팅할 금액은 자연수여야 합니다.");
                }
            }
        }
    }
}
