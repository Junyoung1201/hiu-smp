package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.utils.InventoryUtil;
import me.saehyeon.hiusmp.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopEvent implements Listener {

    boolean isShopGui(InventoryClickEvent e) {
        return e.getView().getTitle().contains("상점");
    }

    @EventHandler
    void onShopGui(InventoryClickEvent e) {
        if(isShopGui(e)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onShopSelectScreen(InventoryClickEvent e) {
        if(e.getView().getTitle().equals("상점 선택") && e.getCurrentItem() != null) {
            e.setCancelled(true);

            switch( ItemUtil.getDisplayName(e.getCurrentItem()) ) {
                case "§f블럭 상점":
                    BlockShop.open((Player) e.getWhoClicked());
                    break;
                case "§f광물 상점":
                    MinerShop.open((Player) e.getWhoClicked());
                    break;
                case "§f희귀품 상점":
                    TreasureShop.open((Player) e.getWhoClicked());
                    break;
                case "§f농작물 상점":
                    FarmShop.open((Player) e.getWhoClicked());
                    break;
            }
        }
    }

    @EventHandler
    void onSellItem(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(isShopGui(e) && ShopItem.isShopItem(item)) {
            e.setCancelled(true);

            // 쉬프트 + 판매
            if(e.getClick().isRightClick()) {
                int amount = 1;

                if(e.getClick().isShiftClick()) {
                    amount = 10;
                }

                if(InventoryUtil.hasItem(player, item.getType(), amount)) {
                    InventoryUtil.removeItems(player,item.getType(), amount);

                    int cost = ShopItem.getSellPrice(item)*amount;

                    Economy.addMoney(player, cost);
                    player.sendMessage("아이템을 판매하여 §6"+cost+" 히유코인§f을 얻었습니다.");
                    player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_PLACE, 0.7f, 1.5f);
                } else {
                    player.sendMessage("§c판매에 필요한 아이템이 충분하지 않습니다.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.7f,1);
                }
            }
        }
    }

    @EventHandler
    void onShopClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().contains("상점") && !e.getView().getTitle().equals("상점 선택")) {
            Player p = (Player) e.getPlayer();

            // 상점 선택이 아니라 상점 GUI에 있으면 상점 선택 화면으로 이동
            Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                ShopManager.openSelectScreen(p);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, SoundCategory.MASTER, 1f, 1f);
            }, 1);
        }
    }

    @EventHandler
    void onBuyItem(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(isShopGui(e) && ShopItem.isShopItem(item)) {
            e.setCancelled(true);

            // 쉬프트 + 구매
            if(e.getClick().isLeftClick()) {
                int amount = 1;

                if(e.getClick().isShiftClick()) {
                    amount = 10;
                }

                int cost = amount*ShopItem.getBuyPrice(item);

                if( cost <= Economy.getMoney(player)) {
                    ItemStack giveItem = new ItemStack(e.getCurrentItem().getType(), amount);
                    player.give(giveItem);
                    Economy.addMoney(player, -cost);
                    player.sendMessage("§6"+cost+" 히유코인§f를 소비하여 §f§l"+ItemUtil.getDisplayName(item)+"§f(을)를 구매했습니다.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.7f, 1.5f);
                } else {
                    player.sendMessage("§c소지금이 부족합니다! 총 §c§l"+cost+" 히유코인§c가 필요하지만, 현재 소지금은 §c§l"+Economy.getMoney(player)+" 히유코인§c입니다. ("+(cost-Economy.getMoney(player))+" 히유 부족)");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.7f,1);
                }
            }
        }
    }
}
