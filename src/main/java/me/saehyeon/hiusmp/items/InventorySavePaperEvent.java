package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.utils.InventoryUtil;
import me.saehyeon.hiusmp.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class InventorySavePaperEvent implements Listener {

    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        // 인벤토리 세이브 스크롤있으면 인벤토리 보호하기
        if(InventorySavePaper.hasItem(e.getPlayer())) {

            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.getDrops().clear();

            InventoryUtil.removeItems(e.getPlayer(), (item) -> {
                if(item.getItemMeta() != null && item.getType() == Material.PAPER && ItemUtil.getDisplayName(item).equals("§e인벤세이브 스크롤")) {
                    return true;
                }

                return false;
            },1);
        }
    }
}
