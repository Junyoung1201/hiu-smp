package me.saehyeon.hiusmp.fun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DurabilityEvent implements Listener {
    @EventHandler
    void onDurabilityChange(PlayerItemDamageEvent e) {
        e.setDamage((int)(e.getDamage() * 1.5));
    }
}
