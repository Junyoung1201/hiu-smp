package me.saehyeon.hiusmp.economy;

import me.saehyeon.hiusmp.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Economy {
    private static YamlConfiguration economyData = new YamlConfiguration();
    private static File economyDataFile = new File(Main.ins.getDataFolder(), "economy.yml");

    public static void setMoney(Player player, int money) {
        economyData.set("money."+player.getUniqueId(), Math.max(money, 0));
    }

    public static void addMoney(Player player, int value) {
        economyData.set("money."+player.getUniqueId(), value+getMoney(player));
    }

    public static int getMoney(Player player) {
        return Math.max( economyData.getInt("money."+player.getUniqueId(), 0), 0);
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
