package me.saehyeon.hiusmp.economy;

import me.saehyeon.hiusmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Set;

public class Economy {
    private static YamlConfiguration economyData = new YamlConfiguration();
    private static File economyDataFile = new File(Main.ins.getDataFolder(), "economy.yml");

    public static void setMoney(String uuid, int money) {
        economyData.set("money."+uuid, Math.max(money, 0));
    }

    public static void setMoney(Player player, int money) {
        economyData.set("money."+player.getUniqueId(), Math.max(money, 0));
    }

    public static void addMoney(Player player, int value) {
        economyData.set("money."+player.getUniqueId(), value+getMoney(player));
    }

    public static int getMoney(String uuid) {
        return Math.max( economyData.getInt("money."+uuid, 0), 0);
    }

    public static int getMoney(Player player) {
        return Math.max( economyData.getInt("money."+player.getUniqueId(), 0), 0);
    }

    public static Set<String> getPlayerUUIDs() {
        if(economyData.getConfigurationSection("money") == null) {
            return Set.of();
        }
        return economyData.getConfigurationSection("money").getKeys(false);
    }

    public static void load() {
        try {
            Main.ins.getDataFolder().mkdir();

            if(!economyDataFile.exists()) {
                economyDataFile.createNewFile();
            }

            economyData.load(economyDataFile);

            Main.ins.getLogger().info("경제 로드함.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Main.ins.getDataFolder().mkdir();

            economyData.save(economyDataFile);

            Main.ins.getLogger().info("경제 저장함.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
