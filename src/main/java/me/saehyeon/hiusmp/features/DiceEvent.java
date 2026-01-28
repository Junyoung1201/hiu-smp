package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DiceEvent implements Listener {
    @EventHandler
    void onDiceBetMoney(AsyncPlayerChatEvent e) {
        if(Dice.isBetting(e.getPlayer())) {
            e.setCancelled(true);

            if(e.getMessage().equals("종료") || e.getMessage().toLowerCase().equals("exit")) {
                Dice.stopBetting(e.getPlayer());
                e.getPlayer().sendMessage("주사위 배팅을 취소했습니다.");
                return;
            }

            try {

                int betMoney = Integer.parseInt( e.getMessage() );

                if(betMoney < 500) {
                    e.getPlayer().sendMessage("§c배팅 금액은 최소 500 히유코인 이상이어야 합니다.");
                    return;
                }

                if(Economy.getMoney(e.getPlayer()) < betMoney) {
                    e.getPlayer().sendMessage("§c소지금이 충분하지 않습니다.");
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 0.7f,1);
                } else {
                    e.getPlayer().sendMessage("당신은 §6"+betMoney+" 히유코인§f을 배팅하여 주사위를 굴립니다.");
                    Dice.betMoney(e.getPlayer(), betMoney);
                    Dice.roll(e.getPlayer());
                }

            } catch (Exception err) {
                e.getPlayer().sendMessage("§c금액이 올바르지 않습니다. 배팅 금액은 자연수가 되어야 합니다.");
            }
        }
    }
}
