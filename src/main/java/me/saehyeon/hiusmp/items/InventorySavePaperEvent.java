package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.utils.InventoryUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.saehyeon.hiusmp.Constants.items.SI_INVENTORY_SAVE_PAPER_DISPLAY_NAME;

public class InventorySavePaperEvent implements Listener {

    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        // 인벤토리 세이브 스크롤있으면 인벤토리 보호하기
//        if(InventorySavePaper.hasItem(e.getPlayer())) {
//
//            e.setKeepInventory(true);
//            e.setKeepLevel(true);
//            e.getDrops().clear();
//
//            InventoryUtil.removeItemsByName(e.getPlayer(), SI_INVENTORY_SAVE_PAPER_DISPLAY_NAME, 1);
//        }
    }
}
