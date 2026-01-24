package me.saehyeon.hiusmp.parkour;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Parkour {
    private static HashMap<UUID, Integer> inputMoney = new HashMap<>();
    private static HashMap<UUID, Double> betMultiplier = new HashMap<>();
    private static List<Player> inInputMoney = new ArrayList<>();

    public static void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "파쿠르");

        ItemStack item = new ItemStack(Material.SLIME_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b파쿠르 도박 시작");
        meta.setLore(Arrays.asList("§f클릭하여 파쿠르 도박을 시작합니다.","§f파쿠르 도박은 일정 금액을 배팅하고 클리어 시 §6§l최대 7배 §f해당하는 금액으로 돌려 받습니다.","§f사망 시 6배에 해당하는 금액을 잃습니다."));
        item.setItemMeta(meta);

        player.openInventory(inv);
        inv.setItem(13, item);
    }

    public static void complete(Player player) {
        int increaseMoney = (int) ( inputMoney.get(player.getUniqueId())*betMultiplier.get(player.getUniqueId()) );

        Economy.addMoney(player, increaseMoney+inputMoney.get(player.getUniqueId()));

        player.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0, 0,0));
        player.sendTitle("§a§l파쿠르 완료", "§6"+increaseMoney+" 히유코인§f을 얻었습니다!",0,70,20);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 0.7f,1f);

        inputMoney.remove(player.getUniqueId());
        betMultiplier.remove(player.getUniqueId());
        inInputMoney.remove(player);
    }

    public static boolean isInParkour(Player player) {
        return inputMoney.containsKey(player.getUniqueId()) && betMultiplier.containsKey(player.getUniqueId());
    }

    public static void failed(Player player) {

        int lossMoney = (int) (inputMoney.get(player.getUniqueId())*7);

        inputMoney.remove(player.getUniqueId());
        betMultiplier.remove(player.getUniqueId());
        inInputMoney.remove(player);

        Economy.addMoney(player, -lossMoney);

        player.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0, 0,0));
        player.sendTitle("§c§l파쿠르 실패","§7"+lossMoney+" 히유코인§f을 잃었습니다!", 0, 70, 20);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.MASTER,0.7f,1);
    }

    public static void generateNewBetMultiplier(Player player) {
        double min = 3D;
        double max = 7D;

        double randomValue = (Math.random() * (max - min)) + min;

        double result = Math.round(randomValue * 10) / 10.0;


        betMultiplier.put(player.getUniqueId(), result);
    }

    public static void stopInputMoney(Player player) {
        inInputMoney.remove(player);
    }

    public static void start(Player player, int betMoney) {
        inputMoney.put(player.getUniqueId(), betMoney);

        if(Economy.getMoney(player) < betMoney) {
            player.sendMessage("§c소지금이 부족합니다. (현재 소지금: "+Economy.getMoney(player)+" 히유코인)");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER,1,1);
            return;
        }

        if(betMoney < 1000) {
            player.sendMessage("§c1000 히유코인 이상만 배팅할 수 있습니다.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER,1,1);
            return;
        }

        // 돈 차감
        Economy.addMoney(player, -betMoney);

        generateNewBetMultiplier(player);

        player.teleport(new Location(Bukkit.getWorld("world"), 0, -57, 1, 0,0));

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {

            player.sendMessage("");
            player.sendMessage("§b§l파쿠르 시작");
            player.sendMessage("당신은 §6"+betMoney+" 히유코인§f을 배팅했으며");
            player.sendMessage("성공 시 §7"+getBetMultiplier(player)+"배§f인 §6"+ (int)(getBetMultiplier(player) * inputMoney.get(player.getUniqueId())) +" 히유코인§f을 지급 받습니다." );
            player.sendMessage("실패 시 §77배§f인 §6"+ (7 * inputMoney.get(player.getUniqueId())) +" 히유코인§f을 잃습니다." );
            player.sendMessage("");

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,1,1);
        },5);
    }

    public static Double getBetMultiplier(Player player) {
        return betMultiplier.getOrDefault(player.getUniqueId(), 1.2D);
    }

    public static void setInputMoney(Player player, int money) {
        inputMoney.put(player.getUniqueId(), money);
    }

    public static boolean isInputingMoney(Player player) {
        return inInputMoney.contains(player);
    }
    public static void startInputMoney(Player player) {
        if(!inInputMoney.contains(player)) {
            inInputMoney.add(player);
        }

        player.sendMessage("");
        player.sendMessage("§b§l파쿠르 도박");
        player.sendMessage("§f파쿠르 도박은 §6성공 시 3배, 실패 시 6배§f를 잃습니다.");
        player.sendMessage("§f계속하시려면 §6배팅할 금액§f을 입력하거나 §6\"exit\" 또는 \"종료\"를 입력하여 입장을 취소합니다.");
        player.sendMessage("");
    }
}
