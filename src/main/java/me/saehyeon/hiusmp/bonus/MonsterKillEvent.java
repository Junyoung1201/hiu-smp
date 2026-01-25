package me.saehyeon.hiusmp.bonus;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MonsterKillEvent implements Listener {
    @EventHandler
    void onKillEnderDragon(EntityDeathEvent e) {
        if(e.getEntityType() == EntityType.ENDER_DRAGON) {
            int coin = MathUtil.randInt(5000,25000);

            Bukkit.getOnlinePlayers().forEach(p -> {
                Economy.addMoney(p, coin);
                p.sendMessage("§6+"+coin+" 히유코인 (엔더드래곤 처지됨)");
                p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 0.7f, 1f);
            });
        }
    }

    @EventHandler
    void onMonsterKill(EntityDeathEvent e) {
        if(e.getDamageSource().getCausingEntity() instanceof Player attacker && e.getEntity().getType() != EntityType.PLAYER) {

            // 엔더팜 사용할 수 있으니 -> 엔더맨, 엔드마이트는 죽여도 돈 주지 않기
            if(e.getEntityType() == EntityType.ENDERMAN || e.getEntityType() == EntityType.ENDERMITE) {
                return;
            }
            if(MathUtil.chance(30)) {
                int coin = MathUtil.randInt(5,10);

                Economy.addMoney(attacker, coin);
                attacker.sendMessage("§6+"+coin+" 히유코인 (몬스터 처치)");
                attacker.playSound(attacker.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 0.7f,1);
            }
        }
    }
}
