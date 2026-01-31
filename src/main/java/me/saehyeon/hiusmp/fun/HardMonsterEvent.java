package me.saehyeon.hiusmp.fun;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HardMonsterEvent implements Listener {
    @EventHandler
    void onDamagedByMonster(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player victim && e.getEntity() instanceof Monster attacker) {
            e.setDamage(e.getDamage() * 1.5);

            if(attacker.getType() == EntityType.ENDER_DRAGON) {
                e.setDamage(e.getDamage() * 3);
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
