package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
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

        Bukkit.getPluginCommand("상점").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("돈").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("송금").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new ShopEvent(), this);

        Economy.load();
    }

    @Override
    public void onDisable() {
        Economy.save();
    }
}
