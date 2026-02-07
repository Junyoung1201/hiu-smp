package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.utils.ItemUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static me.saehyeon.hiusmp.Constants.costs.HOME_TP_COST;

public class HomeGUiEvent implements Listener {
    @EventHandler
    void onHomeGuiInteractive(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        // 집 설정 GUI
        if(e.getView().getTitle().equals("집 설정")) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) {
                return;
            }

            if(e.getCurrentItem().getType() == Material.LIGHT_GRAY_BANNER || e.getCurrentItem().getType() == Material.WHITE_BANNER) {
                String itemName = ItemUtil.getDisplayName(e.getCurrentItem());

                if(itemName.contains("§f슬롯 #")) {
                    try {

                        // 집 설정 시작 (500 히유코인 필요)
                        if(Economy.getMoney(player) < Constants.costs.HOME_SET_COST) {
                            // 돈 부족
                            player.closeInventory();
                            player.sendMessage("§c소지금이 부족합니다. 집 위치를 설정하기 위해선 "+Constants.costs.HOME_SET_COST+" 히유코인이 필요합니다. (현재 소지금: "+Economy.getMoney(player)+" 히유코인)");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER,1,1);
                            return;
                        }

                        // 돈 차감
                        Economy.addMoney(player, -Constants.costs.HOME_SET_COST);

                        int slotIndex = Integer.parseInt( itemName.split("슬롯 #")[1] );
                        Home.setHome(player, slotIndex, player.getLocation().toBlockLocation().toCenterLocation());
                        Home.updateHomeBanner(player);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }
        }

        // 집 이동 GUI
        else if(e.getView().getTitle().equals("집")) {
            e.setCancelled(true);

            if(e.getCurrentItem() != null && ItemUtil.getDisplayName(e.getCurrentItem()).contains("§f슬롯 #")) {

                // 집으로 이동 (활성화된 슬롯)
                if(e.getCurrentItem().getType() == Material.WHITE_BANNER) {

                    // 돈 확인 (100 히유코인 필요)
                    if(Economy.getMoney(player) < HOME_TP_COST) {
                        player.sendMessage("§c소지금이 부족합니다. 집으로 이동하기 위해서는 "+HOME_TP_COST+" 히유코인이 필요합니다. (현재 소지금: "+Economy.getMoney(player)+" 히유코인)");
                        return;
                    }

                    try {
                        String locDataStr = ChatColor.stripColor( e.getCurrentItem().getItemMeta().getLore().get(0) );
                        String[] coords = locDataStr.split(",");

                        String world = coords[0];
                        double x = Double.parseDouble(coords[1].split("x: ")[1]);
                        double y = Double.parseDouble(coords[2].split("y: ")[1]);
                        double z = Double.parseDouble(coords[3].split("z: ")[1]);

                        player.closeInventory();

                        Economy.addMoney(player, -HOME_TP_COST);
                        player.sendMessage("§6"+HOME_TP_COST+" 히유코인§f을 사용하여 §7"+ChatColor.stripColor( ItemUtil.getDisplayName(e.getCurrentItem()) )+"§f으로 이동했습니다.");
                        Teleport.teleportWait(player, new Location(Bukkit.getWorld(world), x,y,z));

                    } catch (Exception ex) {
                        player.sendMessage("§c오류가 발생했습니다.");
                    }
                }

                // 집 이동 안 함 (비활성화된  슬롯)
                else if(e.getCurrentItem().getType() == Material.LIGHT_GRAY_BANNER){
                    player.sendMessage("§c해당 슬롯에 등록된 위치가 없습니다.");
                }
            }
        }
    }
}
