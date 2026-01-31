package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static me.saehyeon.hiusmp.Constants.costs.TPA_REQUEST_COST;

public class Teleport {
    private static HashMap<Player, List<Player>> tpaMap = new HashMap<>();
    private static HashMap<Player, BukkitTask> tpaRequestExpiredTimer = new HashMap<>();
    private static HashMap<Player, List<BukkitTask>> teleportWaitTimer = new HashMap<>();

    private static List<Player> teleportWait = new ArrayList<>();

    private static long TPA_EXPIRED_SECONDs = 20L * 60L;
    private static int TELEPORT_WAIT_SECONDS = 4;

    public static boolean hasTpaRequest(Player player) {
        AtomicBoolean result = new AtomicBoolean(false);

        tpaMap.forEach((k,v) -> {
            if(v.contains(player)) {
                result.set(true);
                return;
            }
        });

        return result.get();
    }

    public static void tpa(Player from, Player to) {
        tpaMap.putIfAbsent(to, new ArrayList<>());

        if(hasTpaRequest(from)) {
            from.sendMessage("");
            from.sendMessage("이미 요청한 TP가 있습니다. 기존 TP 요청을 취소하시겠습니까?");
            ChatUtil.sendClickCommandMessage(from, "§c§l[ TP 요청취소 ]","tpa-cancel");
            from.sendMessage("");
            return;
        }

        if(Economy.getMoney(from) < TPA_REQUEST_COST) {
            from.sendMessage("§c티피 요청을 위해서는 최소 "+TPA_REQUEST_COST+" 히유코인이 필요합니다. (현재 소지금: "+Economy.getMoney(from)+" 히유코인)");
            return;
        } else {
            Economy.addMoney(from, -TPA_REQUEST_COST);
        }

        tpaMap.get(to).add(from);

        // TP 요청하기
        to.sendMessage("");
        to.sendMessage("§7"+from.getName()+"§f(이)가 TP를 요청하고 있습니다. 채팅 클릭하여 응답합니다.");
        ChatUtil.sendClickCommandMessage(to,"§a§l[ TP 허용하기 ]","tpa-accept "+from.getName());
        ChatUtil.sendClickCommandMessage(to,"§c§l[ TP 거부하기 ]","tpa-deny "+from.getName());
        to.sendMessage("");

        from.sendMessage("§7"+to.getName()+"§f에게 TP를 요청했습니다. 1분 내에 수락하지 않을 경우 만료됩니다. §6"+TPA_REQUEST_COST+" 히유코인을 소모했습니다.");

        tpaRequestExpiredTimer.put(from, Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            teleportCancel(from,true);
            from.sendMessage("§7"+to.getName()+"§f(에)게 한 텔레포트 요청이 만료되었습니다.");
            to.sendMessage("§7"+from.getName()+"§f(에)게서 온 텔레포트 요청이 만료되었습니다.");
        },TPA_EXPIRED_SECONDs));
    }

    public static boolean isWaitingTeleport(Player player) {
        return teleportWait.contains(player);
    }

    public static void teleportWait(Player player, Location targetLoc) {

        teleportWaitTimer.putIfAbsent(player, new ArrayList<>());

        player.sendMessage("텔레포트를 시작합니다. "+TELEPORT_WAIT_SECONDS+"초 동안 움직이지 마세요!");

        if(teleportWaitTimer.containsKey(player)) {
            teleportWaitTimer.get(player).forEach(BukkitTask::cancel);
        }

        if(!teleportWait.contains(player)) {
            teleportWait.add(player);
        }

        for(int i = 0; i <= TELEPORT_WAIT_SECONDS; i++) {
            int finalI = i;

            if(TELEPORT_WAIT_SECONDS-i > 0) {
                teleportWaitTimer.get(player).add(
                        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, SoundCategory.MASTER,1,1);
                            player.sendActionBar((TELEPORT_WAIT_SECONDS- finalI)+"초 후 텔레포트..");
                        }, 20L *i)
                );
            }
        }

        teleportWaitTimer.get(player).add(
             Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                 if(teleportWait.contains(player)) {

                     String worldName = targetLoc.getWorld().getName();

                     worldName = switch (worldName) {
                         case "world" -> "overworld";
                         case "world_nether" -> "the_nether";
                         case "world_end" -> "the_end";
                         default -> worldName;
                     };

                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:"+worldName+" run tp "+player.getName()+" "+targetLoc.getX()+" "+targetLoc.getY()+" "+targetLoc.getZ());
                     teleportComplete(player);
                 }
             },TELEPORT_WAIT_SECONDS* 20L)
        );
    }

    public static void teleportComplete(Player player) {
        teleportWait.remove(player);

        if(teleportWaitTimer.containsKey(player)) {
            teleportWaitTimer.get(player).forEach(BukkitTask::cancel);
            teleportWaitTimer.remove(player);
            tpaMap.forEach((p, v) -> {
                if(v.contains(player)) {
                    v.remove(player);
                }
            });
        }

        if( tpaRequestExpiredTimer.containsKey(player) ) {
            tpaRequestExpiredTimer.get(player).cancel();
            tpaRequestExpiredTimer.remove(player);
        }
    }

    public static void tpaAccept(Player accepter, Player receiver) {
        if(tpaMap.containsKey(accepter) && tpaMap.get(accepter).contains(receiver)) {
            receiver.sendMessage("§7"+accepter.getName()+"§f에게 보낸 텔레포트 요청이 수락되었습니다.");
            accepter.sendMessage("§7"+receiver.getName()+"§f의 텔레포트 요청을 수락했습니다.");

            teleportWait(receiver, accepter.getLocation());
        } else {
            accepter.sendMessage("§c"+receiver.getName()+"의 텔레포트 요청을 수락할 수 없습니다. 플레이어가 오프라인이거나 요청이 만료되었습니다.");
        }
    }

    public static void tpaDeny(Player denier, Player receiver) {
        if(tpaMap.containsKey(denier) && tpaMap.get(denier).contains(receiver)) {
            receiver.sendMessage("§7"+denier.getName()+"§f에게 보낸 텔레포트 요청이 거부되었습니다.");
            denier.sendMessage("§7"+receiver.getName()+"§f에게서 온 텔레포트 요청을 거부했습니다.");

            tpaMap.get(denier).remove(receiver);

            if(tpaRequestExpiredTimer.containsKey(receiver)) {
                tpaRequestExpiredTimer.get(receiver).cancel();
                tpaRequestExpiredTimer.remove(receiver);
            }

        } else {
            denier.sendMessage("§c"+receiver.getName()+"의 텔레포트 요청을 거부할 수 없습니다. 플레이어가 오프라인이거나 요청이 만료되었습니다.");
        }
    }

    public static void quit(Player quitPlayer) {

        // tpaRequestExpiredTimer 스케쥴 취소 및 삭제
        if(tpaRequestExpiredTimer.containsKey(quitPlayer)) {
            tpaRequestExpiredTimer.get(quitPlayer).cancel();
            tpaRequestExpiredTimer.remove(quitPlayer);
        }

        // TPA 요청 취소
        if(hasTpaRequest(quitPlayer)) {
            // TPA 요청 받은 플레이어 찾기
            tpaMap.forEach((k,v) -> {
                if(v.contains(quitPlayer)) {
                    k.sendMessage("§7"+quitPlayer.getName()+"§f(이)가 로그아웃하여 TP 요청이 취소되었습니다.");
                    v.remove(quitPlayer);
                }
            });
        }

        if(tpaMap.containsKey(quitPlayer) && !tpaMap.get(quitPlayer).isEmpty()) {
            tpaMap.get(quitPlayer).forEach(p -> {

                // tpa 보낸 사람한테 메세지 보내기
                p.sendMessage("§7"+quitPlayer.getName()+"§f(이)가 로그아웃하여 TP 요청이 취소되었습니다.");

                // tpaRequestExpiredTimer 삭제
                if(tpaRequestExpiredTimer.containsKey(p)) {
                    tpaRequestExpiredTimer.get(p).cancel();
                    tpaRequestExpiredTimer.remove(p);
                }
            });

            tpaMap.remove(quitPlayer);
        }
    }

    public static void teleportCancel(Player player) {
        teleportCancel(player, false);
    }

    public static void teleportCancel(Player player, boolean silent) {

        // teleportWaitTimer 모두 삭제
        if(teleportWaitTimer.containsKey(player)) {
            teleportWaitTimer.get(player).forEach(BukkitTask::cancel);
            teleportWaitTimer.remove(player);
        }

        if(tpaRequestExpiredTimer.containsKey(player)) {
            tpaRequestExpiredTimer.get(player).cancel();
            tpaRequestExpiredTimer.remove(player);
        }

        // tp waiting 에서 삭제
        teleportWait.remove(player);

        tpaMap.forEach((k,v) -> {
            if(v.contains(player)) {
                k.sendMessage("§7"+player.getName()+"§f(으)로부터 온 TP 요청이 취소되었습니다.");
                v.remove(player);
            }
        });

        if(!silent) {
            player.sendMessage("당신의 텔레포트가 취소되었습니다.");
        }
    }
}
