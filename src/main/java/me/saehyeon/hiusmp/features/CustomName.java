package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;

public class CustomName {
    private static YamlConfiguration nameData = new YamlConfiguration();
    private static File nameDataFile = new File(Main.ins.getDataFolder(), "names.yml");

    private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    public static String getName(Player player) {
        return nameData.getString(player.getUniqueId().toString(), player.getName());
    }

    public static void removeAllNameTag(Player player) {
        for(World world : Bukkit.getWorlds()) {
            for(Entity nameTag : world.getEntitiesByClass(TextDisplay.class)) {
                if(nameTag.getScoreboardTags().contains("player-name-tag_"+player.getUniqueId())) {
                    nameTag.remove();
                }
            }
        }
    }

    public static void ensureScoreboard() {
        if(scoreboard == null) {
            scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        }

        Team team = scoreboard.getTeam("hide-player-name");

        if(team == null) {
            team = scoreboard.registerNewTeam("hide-player-name");
            team.setNameTagVisibility(NameTagVisibility.NEVER);
            Bukkit.getOnlinePlayers().forEach(team::addPlayer);
        }
    }

    public static void load() {
        try {
            if(!nameDataFile.exists()) {
                nameDataFile.createNewFile();
            }

            Main.ins.getLogger().info("이름 데이터 로드함.");
            nameData.load(nameDataFile);

        } catch (Exception e) {
            Main.ins.getLogger().warning("이름 로드 실패함.");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Main.ins.getLogger().warning("이름 데이터 저장함.");
            nameData.save(nameDataFile);
        } catch (Exception e) {
            Main.ins.getLogger().warning("이름 데이터 저장 실패함.");
            e.printStackTrace();
        }
    }

    public static void teleportFix(Player player) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            for(World world : Bukkit.getWorlds()) {
                for(Entity nameTag : world.getEntitiesByClass(TextDisplay.class)) {
                    if(nameTag.getScoreboardTags().contains("player-name-tag_"+player.getUniqueId())) {
                        nameTag.remove();
                    }
                }
            }

            for(Entity nameTag : player.getWorld().getEntitiesByClass(TextDisplay.class)) {
                if(nameTag.getScoreboardTags().contains("player-name-tag_"+player.getUniqueId())) {
                    player.addPassenger(nameTag);
                    return;
                }
            }

            // 새로만들기
            setName(player, nameData.getString(player.getUniqueId()+"", player.getName()));
        },5);
    }

    public static void setName(Player player, String name) {

        nameData.set(player.getUniqueId().toString(),name);

        // 기존 플레이어에게 탑승해 있는 text display 가쟈오기 시도
        Entity nameTag = null;

        for(Entity entity : player.getPassengers()) {
            if(entity.getType() == EntityType.TEXT_DISPLAY) {
                nameTag = entity;
            }
        }

        player.setPlayerListName(name);
        player.setDisplayName(name);
        player.setCustomName(name);
        player.setCustomNameVisible(true);

        if(nameTag != null) {

            TextDisplay textDisplay = (TextDisplay) nameTag;
            textDisplay.setCustomNameVisible(true);
            textDisplay.setCustomName(name);

        } else {

            // 기존에 있는 name  tag text display가 없으면 -> 새로 만들어서 앉히기
            Entity newNameTag = player.getWorld().spawn(player.getLocation(), TextDisplay.class);
            TextDisplay textDisplay = (TextDisplay) newNameTag;
            textDisplay.setText("");
            textDisplay.setTextOpacity((byte)0);
            newNameTag.setCustomNameVisible(true);
            newNameTag.setCustomName(name);
            newNameTag.setSilent(true);
            newNameTag.addScoreboardTag("player-name-tag_"+player.getUniqueId());
            player.addPassenger(newNameTag);
        }
    }

    public static void clearCustomNameTagTextDisplay() {
        for(World world : Bukkit.getWorlds()) {
            for(Entity nameTag : world.getEntitiesByClass(TextDisplay.class)) {
                if(nameTag.getVehicle() == null || nameTag.getVehicle().getType() != EntityType.PLAYER) {
                    nameTag.remove();
                }
            }
        }
    }
}
