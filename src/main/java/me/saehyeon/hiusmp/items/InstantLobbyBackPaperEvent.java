package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.utils.InventoryUtil;
import me.saehyeon.hiusmp.utils.ItemUtil;
import me.saehyeon.hiusmp.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.saehyeon.hiusmp.Constants.items.SI_LOBBY_BACK_PAPER_DISPLAY_NAME;

public class InstantLobbyBackPaperEvent implements Listener {
    List<Player> cooldown = new ArrayList<>();

    @EventHandler
    void onUse(PlayerInteractEvent e) {
        if(cooldown.contains(e.getPlayer())) {
            return;
        }

        if(PlayerUtil.isMainHandItemName(e.getPlayer(), SI_LOBBY_BACK_PAPER_DISPLAY_NAME)) {
            e.setCancelled(true);
            cooldown.add(e.getPlayer());

            // 아이템 삭제
            InventoryUtil.removeItemsByName(e.getPlayer(), SI_LOBBY_BACK_PAPER_DISPLAY_NAME, 1);

            // 로비로 이동
            PlayerUtil.teleport(e.getPlayer(), "overworld", 0,0,0,0f,0f);
            e.getPlayer().sendMessage("로비로 이동했습니다.");
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_TELEPORT, SoundCategory.MASTER,0.7f, 1f);

            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                cooldown.remove(e.getPlayer());
            },5);
        }
    }
}
