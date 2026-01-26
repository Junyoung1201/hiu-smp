package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.Constants;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.saehyeon.hiusmp.Constants.items.SI_EXP_BOOSTER_DISPLAY_NAME;
import static me.saehyeon.hiusmp.Constants.items.SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME;

public class NightVisionBottleEvent implements Listener {
    List<Player> cooldown = new ArrayList<>();
    List<UUID> removeNightVisionWhenRejoin = new ArrayList<>();

    @EventHandler
    void onReJoin(PlayerJoinEvent e) {
        if(!removeNightVisionWhenRejoin.contains(e.getPlayer().getUniqueId())) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            if(e.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                e.getPlayer().sendMessage("§c야간투시가 해제되었습니다.");
                e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        },5);
    }

    @EventHandler
    void onUse(PlayerInteractEvent e) {

        ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
        ItemStack item2 = e.getPlayer().getInventory().getItemInMainHand();

        if(ItemUtil.getDisplayName(item).equals(SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME) || ItemUtil.getDisplayName(item2).equals(SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME)) {
            e.setCancelled(true);

            if(cooldown.contains(e.getPlayer())) {
                return;
            }

            cooldown.add(e.getPlayer());

            // 아이템 삭제
            InventoryUtil.removeItemsByName(e.getPlayer(), SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME, 1);

            // 로비로 이동
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Constants.items.SI_NIGHT_VISION_TICK,1,false,false,false));
            e.getPlayer().sendMessage("§b야간투시 "+((int)(Constants.items.SI_NIGHT_VISION_TICK / 20 / 60))+"분이 적용되었습니다. §f서버를 나가면 야간투시가 즉시 종료됩니다.");
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, SoundCategory.MASTER,0.7f, 1.3f);
            removeNightVisionWhenRejoin.add(e.getPlayer().getUniqueId());

            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                cooldown.remove(e.getPlayer());
            },5);

            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                removeNightVisionWhenRejoin.remove(e.getPlayer().getUniqueId());
            },(long)Constants.items.SI_NIGHT_VISION_TICK);

        }
    }
}
