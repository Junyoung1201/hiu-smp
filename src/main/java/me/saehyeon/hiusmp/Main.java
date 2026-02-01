package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.bonus.AdvancementEvent;
import me.saehyeon.hiusmp.bonus.MonsterKillEvent;
import me.saehyeon.hiusmp.economy.*;
import me.saehyeon.hiusmp.features.*;
import me.saehyeon.hiusmp.fun.DurabilityEvent;
import me.saehyeon.hiusmp.fun.HardMonsterEvent;
import me.saehyeon.hiusmp.fun.VillagerTradeEvent;
import me.saehyeon.hiusmp.items.ExpBoosterEvent;
import me.saehyeon.hiusmp.items.InstantLobbyBackPaperEvent;
import me.saehyeon.hiusmp.items.InventorySavePaperEvent;
import me.saehyeon.hiusmp.items.NightVisionBottleEvent;
import me.saehyeon.hiusmp.lobby.BlockEvent;
import me.saehyeon.hiusmp.lobby.ConnectEvent;
import me.saehyeon.hiusmp.lobby.InteractiveEvent;
import me.saehyeon.hiusmp.lobby.NPCEvent;
import me.saehyeon.hiusmp.parkour.Parkour;
import me.saehyeon.hiusmp.parkour.ParkourEvent;
import me.saehyeon.hiusmp.shop.ShopEvent;
import me.saehyeon.hiusmp.town.TownEvent;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
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
        Bukkit.getPluginCommand("땅").setExecutor(new Command());
        Bukkit.getPluginCommand("도움말").setExecutor(new Command());
        Bukkit.getPluginCommand("hiu-item").setExecutor(new Command());
        Bukkit.getPluginCommand("prefix").setExecutor(new Command());
        Bukkit.getPluginCommand("칭호").setExecutor(new Command());

        Bukkit.getPluginCommand("상점").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("땅").setTabCompleter(new TabComplete());
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
        Bukkit.getPluginManager().registerEvents(new DiceEvent(), this);
        Bukkit.getPluginManager().registerEvents(new TownEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this);
        Bukkit.getPluginManager().registerEvents(new AdvancementEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MonsterKillEvent(), this);

        // 아이템 이벤트
        Bukkit.getPluginManager().registerEvents(new InventorySavePaperEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InstantLobbyBackPaperEvent(), this);
        Bukkit.getPluginManager().registerEvents(new NightVisionBottleEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ExpBoosterEvent(), this);

        // 게임 어렵게 만드는거
        Bukkit.getPluginManager().registerEvents(new VillagerTradeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HardMonsterEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DurabilityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new EnchantEvent(), this);     // 인첸트 비용 지불
        Bukkit.getPluginManager().registerEvents(new TaxEvent(), this);         // 세금 관련 이벤트

        Economy.load();
        Home.load();
        CustomName.load();
        Estate.load();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Constants.init();

            CustomName.ensureScoreboard();

            Bukkit.getOnlinePlayers().forEach(p -> {
                CustomName.setName(p, CustomName.getName(p));
            });

            // 타이머 시작
            CustomName.startCleanerTimer();
            RichPrefixTimer.startTimer();
            //Tax.startTimer();

            Bukkit.getWorlds().forEach(world -> {
                world.setGameRule(GameRules.COMMAND_BLOCK_OUTPUT, false);
                world.setGameRule(GameRules.SEND_COMMAND_FEEDBACK, false);
                world.setDifficulty(Difficulty.HARD);
            });
        },30);
    }

    @Override
    public void onDisable() {
        Economy.save();
        Home.save();
        CustomName.save();
        Estate.save();
    }
}
