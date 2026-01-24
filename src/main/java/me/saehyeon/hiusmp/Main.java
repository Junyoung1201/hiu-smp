package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.features.*;
import me.saehyeon.hiusmp.items.InventorySavePaperEvent;
import me.saehyeon.hiusmp.lobby.BlockEvent;
import me.saehyeon.hiusmp.lobby.InteractiveEvent;
import me.saehyeon.hiusmp.lobby.NPCEvent;
import me.saehyeon.hiusmp.parkour.Parkour;
import me.saehyeon.hiusmp.parkour.ParkourEvent;
import me.saehyeon.hiusmp.shop.ShopEvent;
import me.saehyeon.hiusmp.town.TownEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main ins;

    @Override
    public void onEnable() {
        ins = this;

        Bukkit.getPluginCommand("상점").setExecutor(new Command());
        Bukkit.getPluginCommand("돈").setExecutor(new Command());
        Bukkit.getPluginCommand("송금").setExecutor(new Command());
        Bukkit.getPluginCommand("tpa").setExecutor(new Command());
        Bukkit.getPluginCommand("tpa-accept").setExecutor(new Command());
        Bukkit.getPluginCommand("tpa-deny").setExecutor(new Command());
        Bukkit.getPluginCommand("tpa-cancel").setExecutor(new Command());
        Bukkit.getPluginCommand("티피요청").setExecutor(new Command());
        Bukkit.getPluginCommand("로비").setExecutor(new Command());
        Bukkit.getPluginCommand("집").setExecutor(new Command());
        Bukkit.getPluginCommand("홈").setExecutor(new Command());
        Bukkit.getPluginCommand("나가기").setExecutor(new Command());
        Bukkit.getPluginCommand("집설정").setExecutor(new Command());
        Bukkit.getPluginCommand("이름").setExecutor(new Command());
        Bukkit.getPluginCommand("주사위").setExecutor(new Command());
        Bukkit.getPluginCommand("집터").setExecutor(new Command());
        Bukkit.getPluginCommand("hiu-item").setExecutor(new Command());

        Bukkit.getPluginCommand("상점").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("돈").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("송금").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new ShopEvent(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvent(), this);
        Bukkit.getPluginManager().registerEvents(new NPCEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HomeGUiEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CustomNameEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InteractiveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ParkourEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InventorySavePaperEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DiceEvent(), this);
        Bukkit.getPluginManager().registerEvents(new TownEvent(), this);

        Economy.load();
        Home.load();
        CustomName.load();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            CustomName.ensureScoreboard();
            CustomName.clearCustomNameTagTextDisplay();
        },150);

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                CustomName.setName(p, CustomName.getName(p));
            });

            Bukkit.getWorlds().forEach(world -> {
                world.setGameRule(GameRules.COMMAND_BLOCK_OUTPUT, false);
                world.setGameRule(GameRules.SEND_COMMAND_FEEDBACK, false);
            });

            Constants.init();
        },30);
    }

    @Override
    public void onDisable() {
        Economy.save();
        Home.save();
        CustomName.save();
    }
}
