package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Dice {
    private static List<Player> bettingPlayers = new ArrayList<>();
    private static HashMap<UUID, BukkitTask> rollTimer = new HashMap<>();
    private static HashMap<UUID, Integer> betMoney = new HashMap<>();
    //private static HashMap<UUID, Integer> diceAmount = new HashMap<>();
    private static boolean DOBAK_SUSPEND = false;

    public static boolean isBetting(Player player) {
        return bettingPlayers.contains(player);
    }

    public static boolean isRolling(Player player) {
        return rollTimer.get(player.getUniqueId()) != null;
    }

    public static void betMoney(Player player, int money) {
        betMoney.put(player.getUniqueId(), money);
    }

    public static int getBetMoney(Player player) {
        return betMoney.getOrDefault(player.getUniqueId(), 0);
    }

    public static void startBetting(Player player) {
        if(DOBAK_SUSPEND) {
            player.sendMessage("§c현재는 도박이 금지되어 있습니다.");
            return;
        }

        if(isRolling(player)) {
            player.sendMessage("§c이미 주사위 도박이 진행 중입니다. 게임이 완전히 종료된 후 다시시도 해주세요.");
            return;
        }

        if(bettingPlayers.contains(player)) {
            player.sendMessage("§c배팅할 금액을 입력하세요.");
        } else {
            bettingPlayers.add(player);
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("§e§l♠ §f§l주사위 §e§l♠");
            player.sendMessage("§f배팅할 금액을 채팅에 입력해주세요.");
            player.sendMessage("§f취소하려면 §7\"exit\" §f또는 §7\"종료\"§f를 입력하세요.");
            player.sendMessage("");
            player.sendMessage("§e§l♠ §f§l주사위 숫자표 §e§l♠");
            player.sendMessage("");
            player.sendMessage("§e1 ~ 2:§f 2배 잃음");
            player.sendMessage("§e3 ~ 4:§f 1.5배 잃음");
            player.sendMessage("§e5:§f 1.5배 이득");
            player.sendMessage("§e6:§f 2배 이득");
            player.sendMessage("");
        }
    }

    public static void stopBetting(Player player) {
        bettingPlayers.remove(player);
    }

    public static void stopRoll(Player player) {
        if (rollTimer.containsKey(player.getUniqueId())) {
            rollTimer.get(player.getUniqueId()).cancel();
            rollTimer.remove(player.getUniqueId());
        }
    }

    public static void roll(Player player) {
        //diceAmount.putIfAbsent(player.getUniqueId(), 0);
        //diceAmount.put(player.getUniqueId(), diceAmount.get(player.getUniqueId())+1);

        stopRoll(player);

        bettingPlayers.remove(player);

        AtomicInteger currentRoll = new AtomicInteger(0);
        final int maxRolls = 20;

        Runnable[] runnable = new Runnable[1];
        runnable[0] = () -> {
            int currentNum = (int) (Math.random() * 6) + 1;

            player.sendTitle("§e" + currentNum, "", 0, 10, 0);
            player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1, 2);

            int count = currentRoll.incrementAndGet();

            if (count >= maxRolls) {

                // 결과 나옴
                onRollDone(player, currentNum);

                rollTimer.remove(player.getUniqueId());
                return;
            }

            // 딜레이 계산: 1틱부터 시작해서 maxRolls에 가까워질수록 딜레이 증가 (Lerp 느낌)
            long nextDelay = (long) (1 + ((double) count / maxRolls) * 8);

            BukkitTask task = Bukkit.getScheduler().runTaskLater(Main.ins, runnable[0], nextDelay);
            rollTimer.put(player.getUniqueId(), task);
        };

        runnable[0].run();
    }

    public static void onRollDone(Player player, int finalNumber) {
        int addMoney;

        switch(finalNumber) {
            case 1:
            case 2:
                addMoney = 2*getBetMoney(player);
                Economy.addMoney(player, -addMoney);
                player.sendTitle("§c§l"+finalNumber, "§7"+addMoney+" 히유코인§f을 잃었습니다!", 0, 70, 20);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.MASTER, 0.7f,1);
                Bukkit.broadcastMessage("§7"+CustomName.getName(player)+"§f(이)가 주사위 도박에서 §c2배인 §c§l"+addMoney+" 히유코인§c를 잃었습니다!");
                break;
            case 3:
            case 4:
                addMoney = (int) (1.5D * getBetMoney((player)));
                Economy.addMoney(player, -addMoney);
                player.sendTitle("§c§l"+finalNumber, "§7"+addMoney+" 히유코인§f을 잃었습니다!", 0, 70, 20);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 0.7f,1);
                break;
            case 5:
                addMoney = (int) (1.5D * getBetMoney((player)));
                Economy.addMoney(player, addMoney);
                player.sendTitle("§a§l"+finalNumber, "§6"+addMoney+" 히유코인§f을 얻었습니다!", 0, 70, 20);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 0.7f,1);
                break;
            case 6:
                addMoney = (int) (2 * getBetMoney((player)));
                Economy.addMoney(player, addMoney);
                player.sendTitle("§a§l"+finalNumber, "§6"+addMoney+" 히유코인§f을 얻었습니다!", 0, 70, 20);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 0.7f,1);
                Bukkit.broadcastMessage("§6"+CustomName.getName(player)+"§f(이)가 주사위 도박에서 §62배인 §6§l"+addMoney+" 히유코인§6를 벌었습니다!");
                break;
        }
    }
}
