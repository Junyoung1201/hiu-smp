package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.*;

public class CustomName {
    private static BukkitTask cleanerTimer = null;

    private static YamlConfiguration nameData = new YamlConfiguration();
    private static File nameDataFile = new File(Main.ins.getDataFolder(), "names.yml");

    private static YamlConfiguration prefixData = new YamlConfiguration();
    private static File prefixDataFile = new File(Main.ins.getDataFolder(), "prefixs.yml");

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

            // prefix
            if(!prefixDataFile.exists()) {
                prefixDataFile.createNewFile();
            }

            prefixData.load(prefixDataFile);
            Main.ins.getLogger().info("칭호 데이터 로드함.");

        } catch (Exception e) {
            Main.ins.getLogger().warning("이름 로드 실패함.");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            nameData.save(nameDataFile);
            Main.ins.getLogger().info("이름 데이터 저장함.");

            prefixData.save(prefixDataFile);
            Main.ins.getLogger().info("칭호 데이터 저장함.");

        } catch (Exception e) {
            Main.ins.getLogger().warning("이름 데이터 저장 실패함.");
            e.printStackTrace();
        }
    }

    public static List<String> getPrefixList(Player player) {
        return (List<String>) prefixData.get(player.getUniqueId().toString(), Arrays.asList());
    }

    public static String getPrefixString(Player player) {
        return String.join(" ",getPrefixList(player));
    }

    public static boolean hasPrefix(Player player) {
        return prefixData.contains(player.getUniqueId().toString());
    }

    public static void addPrefix(String uuid, String prefix) {
        prefix = ChatColor.translateAlternateColorCodes('&',prefix);

        List<String> arr = (List<String>) prefixData.get(uuid, new ArrayList<>());

        // YAML에서 가져온 리스트는 불변일 수 있으므로 새로운 ArrayList로 복사
        List<String> mutableArr = new ArrayList<>(arr);

        if(!mutableArr.contains(prefix)) {
            mutableArr.add(prefix);
            prefixData.set(uuid, mutableArr);
        }

        Player player = Bukkit.getPlayer(UUID.fromString(uuid));

        if(player != null) {
            updateNameTag(player);
        }
    }

    public static void addPrefix(Player player, String prefix) {
        prefix = ChatColor.translateAlternateColorCodes('&',prefix);

        String uuid = player.getUniqueId().toString();
        List<String> arr = (List<String>) prefixData.get(uuid, new ArrayList<>());

        // YAML에서 가져온 리스트는 불변일 수 있으므로 새로운 ArrayList로 복사
        List<String> mutableArr = new ArrayList<>(arr);

        if(!mutableArr.contains(prefix)) {
            mutableArr.add(prefix);
            prefixData.set(uuid, mutableArr);
        }

        updateNameTag(player);
    }

    public static void removePrefix(Player player, String prefix) {
        prefix = ChatColor.translateAlternateColorCodes('&',prefix);

        String uuid = player.getUniqueId().toString();
        List<String> arr = (List<String>) prefixData.get(uuid, new ArrayList<>());

        // YAML에서 가져온 리스트는 불변일 수 있으므로 새로운 ArrayList로 복사
        List<String> mutableArr = new ArrayList<>(arr);
        mutableArr.remove(prefix);
        prefixData.set(uuid, mutableArr);
        updateNameTag(player);
    }

    public static void startCleanerTimer() {
        if(cleanerTimer == null) {
            cleanerTimer = Bukkit.getScheduler().runTaskTimer(Main.ins, () -> {
                CustomName.clearCustomNameTagTextDisplay();
            },0L, Constants.timers.CUSTOM_NAME_TAG_CLEANER_TIMER_INTERVAL);
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

    private static String prefixPad(String str) {
        return str.equals(" ") || str.isEmpty() ? "§f" : " §f";
    }

    public static void updateNameTag(Player player) {

        // 기존 플레이어에게 탑승해 있는 text display 가쟈오기 시도
        Entity nameTag = null;

        for(Entity entity : player.getPassengers()) {
            if(entity.getType() == EntityType.TEXT_DISPLAY) {
                nameTag = entity;
            }
        }

        String name = getName(player);
        String prefix = getPrefixString(player);

        player.setPlayerListName(prefix+prefixPad(prefix)+name);
        player.setDisplayName(prefix+prefixPad(prefix)+name);
        player.setCustomName(prefix+prefixPad(prefix)+name);
        player.setCustomNameVisible(true);

        if(nameTag != null) {

            TextDisplay textDisplay = (TextDisplay) nameTag;
            textDisplay.setCustomNameVisible(true);
            textDisplay.setCustomName(prefix+prefixPad(prefix)+name);

        } else {

            // 기존에 있는 name  tag text display가 없으면 -> 새로 만들어서 앉히기
            Entity newNameTag = player.getWorld().spawn(player.getLocation(), TextDisplay.class);
            TextDisplay textDisplay = (TextDisplay) newNameTag;
            textDisplay.setText("");
            textDisplay.setTextOpacity((byte)0);
            newNameTag.setCustomNameVisible(true);
            newNameTag.setCustomName(prefix+prefixPad(prefix)+name);
            newNameTag.setSilent(true);
            newNameTag.addScoreboardTag("player-name-tag_"+player.getUniqueId());
            player.addPassenger(newNameTag);
        }
    }

    public static void setName(Player player, String name) {
        nameData.set(player.getUniqueId().toString(),name);
    }

    public static void clearCustomNameTagTextDisplay() {
        for(World world : Bukkit.getWorlds()) {
            for(Entity nameTag : world.getEntitiesByClass(TextDisplay.class)) {
                if(nameTag.getVehicle() == null || nameTag.getVehicle().getType() != EntityType.PLAYER) {
                    if(nameTag.getScoreboardTags().stream().anyMatch(tag -> tag.startsWith("player-name-tag_"))) {
                        nameTag.remove();
                    }
                }
            }
        }
    }
}
