package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class EnderDragonSupport {
    public static void startTimer() {
        Bukkit.getScheduler().runTaskTimer(Main.ins, () -> {
            boolean isInEnd = false;

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.getWorld().getName().equals("wild_the_end")) {
                    isInEnd = true;
                    break;
                }
            }

            if(!isInEnd) {
                DragonBattle battle = Bukkit.getWorld("wild_the_end").getEnderDragonBattle();

                if(battle != null) {
                    EnderDragon dragon = battle.getEnderDragon();

                    if(dragon != null) {
                        if( dragon.getHealth() != dragon.getMaxHealth() ) {
                            dragon.setHealth(dragon.getMaxHealth());
                            Bukkit.broadcastMessage("§d§l엔더드래곤이 회복했습니다! §f엔더월드에서 모두가 떠나 엔더드래곤이 치유되었습니다.");
                        }
                    }
                }
            }
        },0, 20);
    }
}
