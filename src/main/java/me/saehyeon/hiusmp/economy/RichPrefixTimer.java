package me.saehyeon.hiusmp.economy;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.features.CustomName;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RichPrefixTimer {
    public static void startTimer() {
        Bukkit.getScheduler().runTaskTimer(Main.ins, () -> {
            String[] topPlayers = new String[3];
            int[] topMoney = new int[3];
            String[] bottomPlayers = new String[3];
            int[] bottomMoney = new int[3];

            // bottomMoney 초기화 (최대값으로)
            for(int i = 0; i < 3; i++) {
                bottomMoney[i] = Integer.MAX_VALUE;
            }

            for(String uuid : Economy.getPlayerUUIDs()) {
                int money = Economy.getMoney(uuid);

                // 상위 3위까지 순위 갱신
                for(int i = 0; i < 3; i++) {
                    if(topPlayers[i] == null || money > topMoney[i]) {
                        // 기존 순위들을 뒤로 밀어내기
                        for(int j = 2; j > i; j--) {
                            topPlayers[j] = topPlayers[j-1];
                            topMoney[j] = topMoney[j-1];
                        }
                        // 새로운 순위 삽입
                        topPlayers[i] = uuid;
                        topMoney[i] = money;
                        break;
                    }
                }

                // 하위 3위까지 순위 갱신 (돈이 적은 순)
                for(int i = 0; i < 3; i++) {
                    if(bottomPlayers[i] == null || money < bottomMoney[i]) {
                        // 기존 순위들을 뒤로 밀어내기
                        for(int j = 2; j > i; j--) {
                            bottomPlayers[j] = bottomPlayers[j-1];
                            bottomMoney[j] = bottomMoney[j-1];
                        }
                        // 새로운 순위 삽입
                        bottomPlayers[i] = uuid;
                        bottomMoney[i] = money;
                        break;
                    }
                }
            }

            // 디버깅: 상위 3명 출력
            // for(int i = 0; i < 3; i++) {
            //     if(topPlayers[i] != null) {
            //         Player p = Bukkit.getPlayer(UUID.fromString(topPlayers[i]));
            //         String name = p != null ? p.getName() : "오프라인";
            //         Main.ins.getLogger().info((i+1) + "위: " + name + " (" + topMoney[i] + " 히유코인)");
            //     }
            // }

            // 모든 온라인 플레이어의 기존 순위 칭호 제거
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                CustomName.removePrefix(onlinePlayer, "§6§l[서버 1위]");
                CustomName.removePrefix(onlinePlayer, "§e[서버 2위]");
                CustomName.removePrefix(onlinePlayer, "§f[서버 3위]");
                CustomName.removePrefix(onlinePlayer, "§7[거지 3위]");
                CustomName.removePrefix(onlinePlayer, "§8[거지 2위]");
                CustomName.removePrefix(onlinePlayer, "§c[거지 1위]");
            }

            if(topPlayers[0] != null) {
                Player player1 = Bukkit.getPlayer(UUID.fromString(topPlayers[0]));
                if(player1 != null && player1.isOnline()) {
                    CustomName.addPrefix(player1, "§6§l[서버 1위]");
                }
            }

            if(topPlayers[1] != null) {
                Player player2 = Bukkit.getPlayer(UUID.fromString(topPlayers[1]));
                if(player2 != null && player2.isOnline()) {
                    CustomName.addPrefix(player2, "§e[서버 2위]");
                }
            }

            if(topPlayers[2] != null) {
                Player player3 = Bukkit.getPlayer(UUID.fromString(topPlayers[2]));
                if(player3 != null && player3.isOnline()) {
                    CustomName.addPrefix(player3, "§f[서버 3위]");
                }
            }

            // 하위 3명에게 칭호 부여 (온라인인 경우만)
            if(bottomPlayers[0] != null) {
                Player bottom1 = Bukkit.getPlayer(UUID.fromString(bottomPlayers[0]));
                if(bottom1 != null && bottom1.isOnline()) {
                    CustomName.addPrefix(bottom1, "§c[도태 1위]");
                }
            }

            if(bottomPlayers[1] != null) {
                Player bottom2 = Bukkit.getPlayer(UUID.fromString(bottomPlayers[1]));
                if(bottom2 != null && bottom2.isOnline()) {
                    CustomName.addPrefix(bottom2, "§8[도태 2위]");
                }
            }

            if(bottomPlayers[2] != null) {
                Player bottom3 = Bukkit.getPlayer(UUID.fromString(bottomPlayers[2]));
                if(bottom3 != null && bottom3.isOnline()) {
                    CustomName.addPrefix(bottom3, "§7[도태 3위]");
                }
            }
        },0,20);
    }
}
