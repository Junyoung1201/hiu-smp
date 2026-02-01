package me.saehyeon.hiusmp.fun;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HardMonsterEvent implements Listener {
    @EventHandler
    void onDamagedByMonster(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player && (e.getDamager() instanceof Monster || e.getDamager().getType() == EntityType.ENDER_DRAGON)) {
            e.setDamage(e.getDamage() * 1.5);

            if(e.getDamager().getType() == EntityType.ENDER_DRAGON) {
                e.setDamage(e.getDamage() * 3);
                ((Player)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 2)); // 3초간 공중 부양
            }
        }
    }

    @EventHandler
    void onDragonDamaged(EntityDamageByEntityEvent e) {
        if(e.getEntity().getType() == EntityType.ENDER_DRAGON) {
            // 드래곤이 받는 데미지 50% 감소
            e.setDamage(e.getDamage() * 0.5);

            // 15% 확률로 엔더맨 소환
            if(Math.random() < 0.15) {
                Enderman enderman = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), Enderman.class);
                enderman.setAggressive(true);
            }
        }
    }

    @EventHandler
    void onDamageByDragonBreath(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player victim && e.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
            e.setDamage(e.getDamage() * 1.5);
        }
    }
}
