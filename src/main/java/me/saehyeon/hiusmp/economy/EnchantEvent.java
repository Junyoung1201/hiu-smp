package me.saehyeon.hiusmp.economy;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantEvent implements Listener {
    @EventHandler
    void onEnchant(EnchantItemEvent e) {
        int enchantCost = e.getExpLevelCost()*2000;

        if(Economy.getMoney(e.getEnchanter()) < enchantCost) {
            e.setCancelled(true);
            e.getEnchanter().sendMessage("§c해당 인첸트를 하기 위해서는 "+enchantCost+" 히유코인이 필요합니다. (현재 소지금: "+Economy.getMoney(e.getEnchanter())+" 히유코인)");
            e.getEnchanter().playSound(e.getEnchanter().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1,1);
        } else {
            Economy.addMoney(e.getEnchanter(), -enchantCost);
            e.getEnchanter().sendMessage("인첸트 비용으로 §6"+enchantCost+" 히유코인§f을 공제했습니다.");
            e.getEnchanter().playSound(e.getEnchanter().getLocation(), Sound.BLOCK_CHAIN_PLACE, 1,1.5f);
        }
    }
}
