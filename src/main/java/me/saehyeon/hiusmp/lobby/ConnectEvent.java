package me.saehyeon.hiusmp.lobby;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.saehyeon.hiusmp.Constants.locations.LOBBY;

public class ConnectEvent implements Listener {

    void sessionChangeAlert(Player player) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, SoundCategory.MASTER, 1,1);
            player.sendMessage("");
            player.sendMessage("§c§l곧 시즌 2가 시작됩니다! §f아래 항목이 초기화됩니다.");
            player.sendMessage("- 집터의 건축물 (모든 건축물은 백업되며 추후 \"박물관\" 맵을 통해 공개할 예정입니다.)");
            player.sendMessage("- 모든 플레이어들의 자산 및 아이템, 기타 플레이어 정보");
            player.sendMessage("- 야생, 네더월드, 엔더월드");
            player.sendMessage("");
            player.sendMessage("자세한 내용은 아래 링크를 참고해주세요:");
            player.sendMessage("https://blog.naver.com/ljy-dev/224171701295");
            player.sendMessage("");
        },15);
    }

    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            if( !e.getPlayer().hasPlayedBefore() ) {
                PlayerUtil.teleport(e.getPlayer(), LOBBY);
            }
        },2);

        Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
            e.getPlayer().sendMessage("§6/도움말§f로 명령어 설명을 확인할 수 있어요.");
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.7f, 1.5f);
        },10);

        // 시즌 초기화 알림
        //sessionChangeAlert(e.getPlayer());
    }
}
