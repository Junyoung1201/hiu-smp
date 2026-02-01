package me.saehyeon.hiusmp.fun;

import me.saehyeon.hiusmp.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.TradeSelectEvent;

import static me.saehyeon.hiusmp.Constants.costs.VILLAGE_TRADE_COST;

public class VillagerTradeEvent implements Listener {
    @EventHandler
    void onTrade(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.MERCHANT && e.getRawSlot() == 2 && e.getSlotType() == InventoryType.SlotType.RESULT) {
            Player player = (Player) e.getWhoClicked();

            if(Economy.getMoney(player) >= VILLAGE_TRADE_COST) {
                Economy.addMoney(player, -VILLAGE_TRADE_COST);
                player.sendMessage("주민과의 거래로 §6"+VILLAGE_TRADE_COST+" 히유코인§f을 지불했습니다.");
            } else {
                e.setCancelled(true);
                player.sendMessage("§c주민과 거래하기 위해서는 "+VILLAGE_TRADE_COST+" 히유코인이 필요합니다. (현재 소지금: "+Economy.getMoney(player)+" 히유코인)");
            }
        }
    }
}
