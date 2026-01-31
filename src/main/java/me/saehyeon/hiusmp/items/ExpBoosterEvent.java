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
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.saehyeon.hiusmp.Constants.items.SI_EXP_BOOSTER_DISPLAY_NAME;

public class ExpBoosterEvent implements Listener {
    List<Player> cooldown = new ArrayList<>();

    @EventHandler
    void onExpGet(PlayerExpChangeEvent e) {
        if(ExpBooster.hasBoost(e.getPlayer())) {
            e.setAmount(e.getAmount()*2);
        } else {
            e.setAmount(e.getAmount() / 2);
        }
    }

    @EventHandler
    void onUse(PlayerInteractEvent e) {

        ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
        ItemStack item2 = e.getPlayer().getInventory().getItemInMainHand();

        if(ItemUtil.getDisplayName(item).equals(SI_EXP_BOOSTER_DISPLAY_NAME) || ItemUtil.getDisplayName(item2).equals(SI_EXP_BOOSTER_DISPLAY_NAME)) {
            e.setCancelled(true);

            if(cooldown.contains(e.getPlayer())) {
                return;
            }

            if(ExpBooster.hasBoost(e.getPlayer())) {
                e.getPlayer().sendMessage("§c이미 경험치 2배가 적용 중입니다.");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 0.7f,1);
                return;
            }

            cooldown.add(e.getPlayer());

            // 아이템 삭제
            InventoryUtil.removeItemsByName(e.getPlayer(), SI_EXP_BOOSTER_DISPLAY_NAME, 1);

            // 경험치 부스팅 2배 (10분) 시작
            ExpBooster.startExpBoost(e.getPlayer());

            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                cooldown.remove(e.getPlayer());
            },5);

        }
    }
}
