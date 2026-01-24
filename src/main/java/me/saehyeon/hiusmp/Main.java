package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.features.Home;
import me.saehyeon.hiusmp.features.HomeGUiEvent;
import me.saehyeon.hiusmp.features.TeleportEvent;
import me.saehyeon.hiusmp.security.BlockEvent;
import me.saehyeon.hiusmp.security.NPCEvent;
import me.saehyeon.hiusmp.shop.ShopEvent;
import org.bukkit.Bukkit;
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
        Bukkit.getPluginCommand("집설정").setExecutor(new Command());

        Bukkit.getPluginCommand("상점").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("돈").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("송금").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new ShopEvent(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvent(), this);
        Bukkit.getPluginManager().registerEvents(new NPCEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HomeGUiEvent(), this);

        Economy.load();
        Home.load();
    }

    @Override
    public void onDisable() {
        Economy.save();
        Home.save();
    }
}
